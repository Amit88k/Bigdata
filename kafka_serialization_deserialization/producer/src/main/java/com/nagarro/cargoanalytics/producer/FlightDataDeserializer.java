/**
 * 
 */
package com.nagarro.cargoanalytics.producer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nagarro.cargoanalytics.modal.FlightModal;

import java.util.Map;

import org.apache.kafka.common.serialization.Deserializer;

/**
 * @author amitkhandelwal
 *
 */
public class FlightDataDeserializer implements Deserializer<FlightModal>{

	@Override
	public void configure(Map<String, ?> configs, boolean isKey) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public FlightModal deserialize(String topic, byte[] data) {
		ObjectMapper objectMapper = new ObjectMapper();
		FlightModal flightModal = null;
		try {
			flightModal = objectMapper.readValue(data, FlightModal.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flightModal;
	}

	@Override
	public void close() {
		
	}
	
}
