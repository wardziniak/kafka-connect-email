package com.wardziniak.kafka.connect.email

import java.util

import org.apache.kafka.clients.consumer.OffsetAndMetadata
import org.apache.kafka.common.TopicPartition
import org.apache.kafka.connect.sink.{SinkRecord, SinkTask}
import org.slf4j.{Logger, LoggerFactory}
import collection.JavaConverters._

/**
  * Created by wardziniak on 29.11.2016.
  */
class EmailSinkTask extends SinkTask {

  val log: Logger = LoggerFactory.getLogger(classOf[EmailSinkTask])

  override def stop(): Unit = log.error("stop");

  override def put(records: util.Collection[SinkRecord]): Unit = {
    records.asScala.foreach(record => log.error(record.value().toString))
  }

  override def flush(offsets: util.Map[TopicPartition, OffsetAndMetadata]): Unit = log.debug("flush")

  override def start(props: util.Map[String, String]): Unit = log.debug("start")

  override def version(): String = "0.0.1"
}
