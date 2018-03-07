package com.nagarro.cargoanalytics.producer;

import java.util.Map;

import org.apache.kafka.common.serialization.Serializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nagarro.cargoanalytics.modal.FlightModal;
/**
 * @author amitkhandelwal
 *
 */
public class FlightDataSerializer implements Serializer<FlightModal> {

	public void close() {
	
	}

	public void configure(Map arg0, boolean arg1) {
		
	}

	public byte[] serialize(String arg0, FlightModal arg1) {
		byte[] retVal = null;
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			retVal = objectMapper.writeValueAsString(arg1).getBytes();
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return retVal;
	}
	




}
