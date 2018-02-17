package com.sean.chatbot

import akka.actor.Actor
import com.sean.chatbot.Models.UserMessage

class ChatBotActor extends Actor {

  def receive = greetings

  def greetings: Receive = {
    case UserMessage(token, message) =>
      context.become(email)
      sender ! "hi please enter email:"
  }

  def email: Receive = {
    case UserMessage(token, message) =>
      context.become(url)
      sender ! ""
  }

  def url: Receive = {
    case UserMessage(toke, message) =>
      sender ! ""
  }
}
