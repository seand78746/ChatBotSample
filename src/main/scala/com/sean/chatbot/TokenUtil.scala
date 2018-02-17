package com.sean.chatbot

import java.math.BigInteger
import java.security.SecureRandom

object TokenUtil {
  private val random = new SecureRandom()
  def generate(nrChars: Int = 24): String = {
    new BigInteger(nrChars * 5, random).toString(32)
  }
}
