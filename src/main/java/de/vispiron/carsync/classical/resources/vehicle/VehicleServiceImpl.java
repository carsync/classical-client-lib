package de.vispiron.carsync.classical.resources.vehicle;

import de.vispiron.carsync.classical.service.CarsyncSession;
import de.vispiron.carsync.classical.service.RestServiceImpl;

public class VehicleServiceImpl extends RestServiceImpl<Vehicle> implements VehicleService {

	public VehicleServiceImpl(CarsyncSession carsyncSession) {
		super(carsyncSession,
				"/api/v4/vehicle",
				Vehicle.class,
				VehicleResponseDTO.class,
				VehicleCreateDTO.class,
				VehicleUpdateDTO.class);
	}
}
