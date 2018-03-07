package com.nagarro.cargoanalytics.modal;

/**
 * @author amitkhandelwal
 *
 */
public class FlightModal {

	protected String flightCode;
	protected String departurePort;
	protected String arrivalPort;
	
	FlightModal(){
		
	}
	
	public FlightModal(String flightCode, String departurePort, String arrivalPort) {
		super();
		this.flightCode = flightCode;
		this.departurePort = departurePort;
		this.arrivalPort = arrivalPort;
	}
	
	  public String toString() {
		    return "Flight code=" + flightCode + ", departurePort=" + departurePort + ", arrivalPort=" + arrivalPort ;
		  }

	/**
	 * @return the flightCode
	 */
	public String getFlightCode() {
		return flightCode;
	}

	/**
	 * @param flightCode the flightCode to set
	 */
	public void setFlightCode(String flightCode) {
		this.flightCode = flightCode;
	}

	/**
	 * @return the departurePort
	 */
	public String getDeparturePort() {
		return departurePort;
	}

	/**
	 * @param departurePort the departurePort to set
	 */
	public void setDeparturePort(String departurePort) {
		this.departurePort = departurePort;
	}

	/**
	 * @return the arrivalPort
	 */
	public String getArrivalPort() {
		return arrivalPort;
	}

	/**
	 * @param arrivalPort the arrivalPort to set
	 */
	public void setArrivalPort(String arrivalPort) {
		this.arrivalPort = arrivalPort;
	}

}

	