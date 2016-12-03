package com.wardziniak.kafka.connect

/**
  * Created by wardziniak on 03.12.2016.
  */
package object email {

  val HOST_NAME_KEY: String = "wardziniak.email.connect.hostname"
  val SMTP_PORT_KEY: String = "wardziniak.email.connect.smtp.port"
  val FROM_ADDRESS_KEY: String = "wardziniak.email.connect.from.address"
  val USERNAME_KEY: String = "wardziniak.email.connect.username"
  val PASSWORD_KEY: String = "wardziniak.email.connect.passoword"

  val MANDATORY_KEYS = Set(HOST_NAME_KEY, SMTP_PORT_KEY, FROM_ADDRESS_KEY, USERNAME_KEY, PASSWORD_KEY)



}
