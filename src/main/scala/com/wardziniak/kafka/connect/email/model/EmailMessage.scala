package com.wardziniak.kafka.connect.email.model

/**
  * Created by wardziniak on 01.12.2016.
  */
case class EmailMessage(title: String, body: String, toRecipients: Option[List[Recipient]])


case class Recipient(name: String, emailAddress: String)
