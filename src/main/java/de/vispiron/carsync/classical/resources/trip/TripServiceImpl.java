package de.vispiron.carsync.classical.resources.trip;

import de.vispiron.carsync.classical.service.CarsyncSession;
import de.vispiron.carsync.classical.service.RestServiceImpl;

public class TripServiceImpl extends RestServiceImpl<Trip> implements TripService {

	public TripServiceImpl(CarsyncSession carsyncSession) {
		this(carsyncSession, LogBookContext.LOG_BOOK);
	}

	public TripServiceImpl(CarsyncSession carsyncSession, LogBookContext context) {
		super(carsyncSession,
				"/api/v4/" + (context == LogBookContext.LOG_BOOK ? "logbook" : "fleetlogbook"),
				Trip.class,
				TripResponseDTO.class,
				null,
				TripUpdateDTO.class);
	}

	public enum LogBookContext {LOG_BOOK, FLEET_LOG_BOOK}
}
