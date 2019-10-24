package de.vispiron.carsync.classical.domain;

import lombok.Getter;

public class CarsyncServerException extends RuntimeException {

	@Getter
	private int responseCode;

	@Getter
	private String requestId;

	public CarsyncServerException(String message, int responseCode, String requestId) {
		super(message);
		this.responseCode = responseCode;
		this.requestId = requestId;
	}

	@Override public String toString() {
		return "Response code " + String.valueOf(this.responseCode) + " on request " + this.requestId + ": "
				+ super.toString();
	}
}
