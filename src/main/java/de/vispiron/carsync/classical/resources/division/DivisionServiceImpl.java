package de.vispiron.carsync.classical.resources.division;

import de.vispiron.carsync.classical.service.CarsyncSession;
import de.vispiron.carsync.classical.service.RestServiceImpl;

public class DivisionServiceImpl extends RestServiceImpl<Division> implements DivisionService {

	public DivisionServiceImpl(CarsyncSession carsyncSession) {
		super(carsyncSession,
				"/api/division",
				Division.class,
				DivisionResponseDTO.class,
				DivisionCreateDTO.class,
				DivisionUpdateDTO.class);
	}
}
