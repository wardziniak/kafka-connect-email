package com.wardziniak.kafka.connect.email.model

/**
  * Created by wardziniak on 12/2/16.
  */
case class Message(name: String) {
  override def toString: String = name
}
