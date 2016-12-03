# kafka-connect-email

##Summary

kafka-connect-email is simple Apache Kafka Sink Connector. It reads messages from Kafka topics and send emails with those messages. Messages have to be in proper schema.


##Configuration

To use connector you need first setup Apache Kafka Cluster. For Kafka following docker image can be used: wardziniak/kafka

###Quick guide for Kafka

Start zookeeper
```
docker run -d --name zookeeper -p 2181:2181 jplock/zookeeper:3.4.9
```

Start kafka brokers
```
docker run -d --name kafka1 -p 9092:9092 --env ZOOKEEPER_IP=192.168.1.13 --env KAFKA_BROKER_ID=0 --env KAFKA_PORT=9092 --env KAFKA_ADVERTISED_PORT=9092 --env KAFKA_ADVERTISED_HOST_NAME=192.168.1.13 wardziniak/kafka
```

```
docker run -d --name kafka2 -p 9093:9093 --env ZOOKEEPER_IP=192.168.1.13 --env KAFKA_BROKER_ID=1 --env KAFKA_PORT=9093 --env KAFKA_ADVERTISED_PORT=9093 --env KAFKA_ADVERTISED_HOST_NAME=192.168.1.13 wardziniak/kafka
```

You have to change ZOOKEEPER_IP and KAFKA_ADVERTISED_HOST_NAME to ip of you machine.
More detail can be found:
https://hub.docker.com/r/wardziniak/kafka

When Apache Kafka is ready topic for reading emails message havet to be created. To do that you need download Apache Kafka from https://kafka.apache.org/downloads and run command:
```
./bin/kafka-topics.sh --create --zookeeper localhost:2181 --partitions 5 --replication-factor 2 --topic connect-email
```


###Guide for kafka-email-connector

Create property files in $KAFKA_HOME/config

* config/connect-main.properties
```properties
bootstrap.servers=localhost:9092
key.converter=org.apache.kafka.connect.storage.StringConverter
value.converter=com.wardziniak.kafka.connect.email.EmailConverter
key.converter.schemas.enable=false
value.converter.schemas.enable=false
internal.key.converter=org.apache.kafka.connect.json.JsonConverter
internal.value.converter=org.apache.kafka.connect.json.JsonConverter
internal.key.converter.schemas.enable=false
internal.value.converter.schemas.enable=false
offset.storage.file.filename=/tmp/connect.offsets
offset.flush.interval.ms=10000
```

* config/connect-email-sink.properties
```properties
name=local-email-sink
connector.class=com.wardziniak.kafka.connect.email.EmailSinkConnector
tasks.max=1
topics=connect-email
wardziniak.email.connect.hostname=smtp.googlemail.com
wardziniak.email.connect.smtp.port=465
wardziniak.email.connect.from.address=fromaddress@gmail.com
wardziniak.email.connect.username=username@gmail.com
wardziniak.email.connect.passoword=emailpassword
```
* Notice
  * bootstrap.servers - address with port of Kafka broker
  * topics - Kafka topic created. Messages will be sent to it
  * wardziniak.email.connect.* - Email account settings

###Start connector

* First you need to build it
  ```
  sbt assembly
  ```
* Add to CLASSPATH assemblied jar ($PROJECT/target/scala-2.11/kafka-connect-email-assembly-1.0.jar)
* Start connector
```
  $KAFKA_HOME/bin/connect-standalone.sh config/connect-main.properties config/connect-email-sink.properties
```

###Send Email

To send email you need to send message to propriate Kafka topic (ex. connect-email). You can use Kafka tools
```
$KAFKA_HOME/bin/kafka-console-producer.sh --broker-list localhost:9092 --topic connect-email
```
Paste sample message
```
{"title":"Sample title","body":"Email content","toRecipients":[{"name":"Ludwig","emailAddress":"Ludwig@von.mises"}]}
```

Json Schema for messages
```json
{
  "$schema": "http://json-schema.org/draft-04/schema#",
  "type": "object",
  "properties": {
    "title": {
      "type": "string"
    },
    "body": {
      "type": "string"
    },
    "toRecipients": {
      "type": "array",
      "items": {
        "type": "object",
        "properties": {
          "name": {
            "type": "string"
          },
          "emailAddress": {
            "type": "string"
          }
        },
        "required": [
          "name",
          "emailAddress"
        ]
      }
    }
  },
  "required": [
    "title",
    "body",
    "toRecipients"
  ]
}
```
Sample message:
```json
{
  "title": "Sample title",
  "body": "Mail body",
  "toRecipients": [
    {
      "name": "Ludwig",
      "emailAddress": "Ludwig@von.mises"
    }
  ]
}
```

