package com.wardziniak.kafka.connect.email

import java.util

import com.wardziniak.kafka.connect.email.model.Message
import org.apache.kafka.clients.consumer.OffsetAndMetadata
import org.apache.kafka.common.TopicPartition
import org.apache.kafka.connect.sink.{SinkRecord, SinkTask}
import org.slf4j.{Logger, LoggerFactory}
import spray.json.JsonParser
import spray.json._

import collection.JavaConverters._

/**
  * Created by wardziniak on 29.11.2016.
  */
class EmailSinkTask extends SinkTask {

  val log: Logger = LoggerFactory.getLogger(classOf[EmailSinkTask])

  override def stop(): Unit = log.error("stop")

  override def put(records: util.Collection[SinkRecord]): Unit = {
    records.asScala.foreach(record => {
      log.error("put:value:" + record.toString)
      log.error("put:class" + record.value.getClass.toString)
    })
  }

  override def flush(offsets: util.Map[TopicPartition, OffsetAndMetadata]): Unit = log.error("flush")

  override def start(props: util.Map[String, String]): Unit = log.error("start")

  override def version(): String = "0.0.1"
}
