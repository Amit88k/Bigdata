/**
 * 
 */
package com.nagarro.cargoanalytics.producer;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import com.nagarro.cargoanalytics.modal.FlightModal;

/**
 * @author amitkhandelwal
 *
 */
public class FlightDataProducer implements Runnable{
	
	private final KafkaProducer<String, FlightModal> producer;
	private final String topic;
	
	public FlightDataProducer(String brokers, String topic){
		Properties prop = createProducerConfig(brokers);
		this.producer = new KafkaProducer<String, FlightModal>(prop);
		this.topic = topic;
	}
	/**
	 * @param brokers
	 * @return
	 */
	private static Properties createProducerConfig(String brokers) {
		Properties props = new Properties();
		props.put("bootstrap.servers", brokers);
		props.put("acks", "all");
		props.put("retries", 0);
		props.put("batch.size", 16384);
		props.put("linger.ms", 1);
		props.put("buffer.memory", 33554432);
		props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		props.put("value.serializer", "com.nagarro.cargoanalytics.producer.FlightDataSerializer");
		
		return props;
	}
	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	public void run() {
		List<FlightModal> flightsList = new ArrayList<>();
		flightsList.add(new FlightModal("1001", "NYC", "DEL"));
		flightsList.add(new FlightModal("2002", "LON", "MUB"));
		for(final FlightModal flight:flightsList) {
			producer.send(new ProducerRecord<String, FlightModal>(topic, flight.getFlightCode(), flight), 
					new Callback() {
				 public void onCompletion(RecordMetadata metadata, Exception e) {
		              if (e != null) {
		                e.printStackTrace();
		              }
		              System.out.println("Sent:" + flight.toString());
		            }
			});
			 try {
			        Thread.sleep(100);
			      } catch (InterruptedException e) {
			        e.printStackTrace();
			      }

			    }

			    // closes producer
			    producer.close();		
	}	
}
