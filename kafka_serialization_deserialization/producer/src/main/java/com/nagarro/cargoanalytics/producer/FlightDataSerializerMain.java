/**
 * 
 */
package com.nagarro.cargoanalytics.producer;

/**
 * @author amitkhandelwal
 *
 */
public class FlightDataSerializerMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String brokers = "localhost:9092";
	    String groupId = "group01";
	    String topic = "FlightData_topic";
	    
	    if (args != null && args.length == 3) {
	        brokers = args[0];
	        groupId = args[1];
	        topic = args[2];
	      }
	    
	 FlightDataProducer producerThread = new FlightDataProducer(brokers, topic);
	 Thread t1 = new Thread(producerThread);
	 t1.start();
	 
	 FlightDataConsumer consumerThread = new FlightDataConsumer(brokers, groupId, topic);
	 Thread t2 = new Thread(consumerThread);
	 t2.start();
	 
	 try {
	      Thread.sleep(100000);
	    } catch (InterruptedException ie) {

	    }

	}

}
