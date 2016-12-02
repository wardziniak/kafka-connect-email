package com.wardziniak.kafka.connect.email

import java.util

import com.wardziniak.kafka.connect.email.model.Message
import org.apache.kafka.connect.data.{Schema, SchemaAndValue, SchemaBuilder}
import org.apache.kafka.connect.storage.Converter
import org.slf4j.{Logger, LoggerFactory}
import spray.json.{DefaultJsonProtocol, JsonParser}

/**
  * Created by wardziniak on 12/2/16.
  */
class EmailConverter extends Converter {

  val log: Logger = LoggerFactory.getLogger(classOf[EmailConverter])

  object FriendsProtocol extends DefaultJsonProtocol {
    implicit val messageFormat = jsonFormat1(Message)
  }

  override def configure(configs: util.Map[String, _], isKey: Boolean): Unit = {}

  override def toConnectData(topic: String, value: Array[Byte]): SchemaAndValue = {
    import FriendsProtocol.messageFormat
    log.error(value.toString)
    val msg = JsonParser(new String(value, "UTF-8")).convertTo[Message]
    new SchemaAndValue(SchemaBuilder.struct().optional.build(), msg)
  }

  override def fromConnectData(topic: String, schema: Schema, value: scala.Any): Array[Byte] = value.toString.getBytes
}
