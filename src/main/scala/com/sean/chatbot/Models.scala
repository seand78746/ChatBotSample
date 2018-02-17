package com.sean.chatbot

import spray.json.DefaultJsonProtocol

object Models {
  case class UserLogin(name: String)
  case class UserMessage(token: String, message: String)
  case class CreateTicketRequest(description: String, environment: String, createdAt: String, userEmail: String)
  case class CreateTicketRespone(status: String, ticketId: String)
  object ServiceJsonProtoocol extends DefaultJsonProtocol {
    implicit val chatBotUserLoginProtocol = jsonFormat1(UserLogin)
    implicit val chatBotUserMessageProtocol = jsonFormat2(UserMessage)
    implicit val createTicketReqProtocol = jsonFormat4(CreateTicketRequest)
    implicit val createTicketResProtocol = jsonFormat2(CreateTicketRespone)

  }
}