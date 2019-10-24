package de.vispiron.carsync.classical.service;

import lombok.Getter;
import lombok.Setter;

public class CarsyncSession {

	@Getter
	private final String username;

	@Getter
	private final String password;

	@Getter
	@Setter
	private String sessionToken;

	@Getter
	@Setter
	private String baseUrl = "https://portal.carsync-log.de";

	public CarsyncSession(String username, String password) {
		this.username = username;
		this.password = password;
	}

}
