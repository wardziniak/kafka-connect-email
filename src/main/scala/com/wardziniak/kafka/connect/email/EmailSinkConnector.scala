package com.wardziniak.kafka.connect.email

import java.util

import org.apache.kafka.common.config.ConfigDef
import org.apache.kafka.connect.connector.Task
import org.apache.kafka.connect.sink.SinkConnector
import org.slf4j.{Logger, LoggerFactory}
import collection.JavaConverters._

/**
  * Created by wardziniak on 29.11.2016.
  */
class EmailSinkConnector extends SinkConnector {

  val log: Logger = LoggerFactory.getLogger(classOf[EmailSinkConnector])

  private val configProps: util.Map[String, String] = new util.HashMap[String, String]()

  override def taskClass(): Class[_ <: Task] = classOf[EmailSinkTask]

  override def taskConfigs(maxTasks: Int): util.List[util.Map[String, String]] =
    (1 to maxTasks).map(_ => configProps).asJava

  override def stop(): Unit = log.debug("stop")

  override def config(): ConfigDef = new ConfigDef()

  override def start(props: util.Map[String, String]): Unit = log.debug("start")

  override def version(): String = "0.1.1"
}
