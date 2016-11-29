package com.wardziniak.kafka.connect.email

import java.util

import org.apache.kafka.common.config.ConfigDef
import org.apache.kafka.connect.connector.Task
import org.apache.kafka.connect.sink.SinkConnector
import org.slf4j.{Logger, LoggerFactory}

/**
  * Created by wardziniak on 29.11.2016.
  */
class EmailSinkConnector extends SinkConnector {

  val log: Logger = LoggerFactory.getLogger(classOf[EmailSinkConnector])

  override def taskClass(): Class[_ <: Task] = classOf[EmailSinkTask]

  override def taskConfigs(maxTasks: Int): util.List[util.Map[String, String]] = ???

  override def stop(): Unit = ???

  override def config(): ConfigDef = ???

  override def start(props: util.Map[String, String]): Unit = ???

  override def version(): String = "0.1.1"
}
