# cloud-stream-kafka-issue
repo to show spring cloud stream kafka issue



# Steps to reproduce

* start the kafka cluster - > docker-compose up

* insert data into kafka topic

echo "1:peter" | kafkacat -b localhost:9092 -t hello.topic -Z -K:
echo "2:" | kafkacat -b localhost:9092 -t hello.topic -Z -K:
echo "3:marie" | kafkacat -b localhost:9092 -t hello.topic -Z -K:
echo "4:rosie" | kafkacat -b localhost:9092 -t hello.topic -Z -K:

* start the spring boot application 

* call the metrics endpoint and assert that after reading all messages on the topic the unconsumed messages is 2.

curl http://localhost:8080/actuator/metrics/spring.cloud.stream.binder.kafka.offset | json_pp

```
{
   "baseUnit" : null,
   "name" : "spring.cloud.stream.binder.kafka.offset",
   "description" : "Unconsumed messages for a particular group and topic",
   "measurements" : [
      {
         "value" : 2,
         "statistic" : "VALUE"
      }
   ],
   "availableTags" : [
      {
         "values" : [
            "hello.topic"
         ],
         "tag" : "topic"
      },
      {
         "values" : [
            "cloud.stream.service-hello"
         ],
         "tag" : "group"
      }
   ]
}
```
