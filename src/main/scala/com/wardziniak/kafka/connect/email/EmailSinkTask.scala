package com.wardziniak.kafka.connect.email

import java.util

import com.wardziniak.kafka.connect.email.model.EmailMessage
import org.apache.commons.mail.SimpleEmail
import org.apache.kafka.clients.consumer.OffsetAndMetadata
import org.apache.kafka.common.TopicPartition
import org.apache.kafka.connect.errors.ConnectException
import org.apache.kafka.connect.sink.{SinkRecord, SinkTask}
import org.slf4j.{Logger, LoggerFactory}

import scala.collection.JavaConverters._

/**
  * Created by wardziniak on 29.11.2016.
  */
class EmailSinkTask extends SinkTask {

  val log: Logger = LoggerFactory.getLogger(classOf[EmailSinkTask])

  var hostName: String = ""
  var smtpPort: Int = 465
  var userName: String = ""
  var password: String = ""
  var fromAddress: String = ""


  override def stop(): Unit = log.error("stop")

  override def put(records: util.Collection[SinkRecord]): Unit = {
    records.asScala.foreach(record => {
      log.error("PUT:" + record.value().toString)
      sendEmail(record.value().asInstanceOf[EmailMessage])
    })
  }

  override def flush(offsets: util.Map[TopicPartition, OffsetAndMetadata]): Unit = log.error("flush")

  override def start(props: util.Map[String, String]): Unit = {
    if (!MANDATORY_KEYS.forall(mandatory => props.keySet().contains(mandatory)))
      throw new ConnectException("Not all mandatory properties are set")
    hostName = props.get(HOST_NAME_KEY)
    smtpPort = props.get(SMTP_PORT_KEY).toInt
    userName = props.get(USERNAME_KEY)
    password = props.get(PASSWORD_KEY)
    fromAddress = props.get(FROM_ADDRESS_KEY)
    log.debug("Sink task started")
  }

  override def version(): String = "0.0.1"


  private def sendEmail(emailMessage: EmailMessage): Unit = {
    val email = new SimpleEmail()
    email.setHostName(hostName)
    email.setSmtpPort(smtpPort)
    email.setAuthentication(userName, password)
    email.setFrom(fromAddress)
    email.setStartTLSEnabled(true)
    email.setSSLOnConnect(true)
    email.setSubject(emailMessage.title)
    email.setMsg(emailMessage.body)
    if (emailMessage.toRecipients.isDefined) {
      emailMessage.toRecipients.get.map(_.emailAddress).foreach(address => email.addTo(address))
    }
    email.send()
    log.info("Email sent")
  }
}
