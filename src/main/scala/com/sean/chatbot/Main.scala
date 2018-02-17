package com.sean.chatbot

import akka.actor.{ActorSystem, Props}
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import akka.http.scaladsl.server.Directives._
import com.sean.chatbot.Models.{UserLogin, UserMessage}
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import akka.pattern.ask
import akka.util.Timeout
import scala.concurrent.Future
import scala.concurrent.duration._

object Main {
  case class ChatBotUser(name: String, token: String)

  def main(args: Array[String]) {
    implicit val actorSystem = ActorSystem("chat-bot-system")
    implicit val actorMaterializer = ActorMaterializer()
    implicit val executionContext = actorSystem.dispatcher
    implicit val timeout = Timeout(5.seconds)

    val chatActor = actorSystem.actorOf(Props[ChatBotActor])
    var userList = Vector.empty[ChatBotUser]

    def findToken(token: String): Future[Option[ChatBotUser]] = Future {
      userList.find(_.token == token)
    }

    import com.sean.chatbot.Models.ServiceJsonProtoocol._

    val route = pathPrefix("chatbot") {
      path("login")  {
        post {
          entity(as[UserLogin]) {
            user =>
              complete {
                userList.find(_.name == user.name) match {
                  case None =>
                    val token = TokenUtil.generate()
                    val newUser = new ChatBotUser(user.name, token)
                    userList = userList :+ newUser
                    println(s"generated token for ${user.name} - $token")
                    token
                  case Some(chatBotUser) =>
                    s"User already has login, use token: ${chatBotUser.token}"
                }
              }
          }
        }
      } ~
        path("message") {
          post {
            entity(as[UserMessage]) {
              message =>
                val tokenValid: Future[Option[ChatBotUser]] = findToken(message.token)
                onSuccess(tokenValid) {
                  case Some(user) =>
                    onSuccess((chatActor ? UserMessage).mapTo[String]) {
                      result =>
                        complete(result)
                    }
                  case None =>
                    complete("No user found with that token.")
                }
            }
          }
        }
    }

    Http().bindAndHandle(route, "localhost", 8080)
    println("server started at 8080")
  }
}
