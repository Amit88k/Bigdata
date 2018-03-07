/**
 * 
 */
package com.nagarro.cargoanalytics.producer;

import java.util.Arrays;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import com.nagarro.cargoanalytics.modal.FlightModal;

/**
 * @author amitkhandelwal
 *
 */
public class FlightDataConsumer implements Runnable {

	private final KafkaConsumer<String, FlightModal> consumer;
	private final String topic;
	

	public FlightDataConsumer(String brokers, String groupId, String topic) {
		Properties prop = createConsumerConfig(brokers, groupId);
		this.consumer = new KafkaConsumer<>(prop);
		this.topic = topic;
		this.consumer.subscribe(Arrays.asList(this.topic));
	}
	
	/**
	 * @param brokers
	 * @param groupId
	 * @return
	 */
	 private static Properties createConsumerConfig(String brokers, String groupId) {
		    Properties props = new Properties();
		    props.put("bootstrap.servers", brokers);
		    props.put("group.id", groupId);
		    props.put("enable.auto.commit", "true");
		    props.put("auto.commit.interval.ms", "1000");
		    props.put("session.timeout.ms", "30000");
		    props.put("auto.offset.reset", "earliest");
		    props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		    props.put("value.deserializer", "com.nagarro.cargoanalytics.producer.FlightDataDeserializer");
		    return props;
		  }


	@Override
	public void run() {
		while(true){
			ConsumerRecords<String, FlightModal> records = consumer.poll(100);
			for (final ConsumerRecord<String, FlightModal> record : records) {
				System.out.println("Recieve" + record.value());
			}
		}
		
	}

}
