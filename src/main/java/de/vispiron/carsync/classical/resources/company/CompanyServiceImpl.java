package de.vispiron.carsync.classical.resources.company;

import de.vispiron.carsync.classical.service.CarsyncSession;
import de.vispiron.carsync.classical.service.RestServiceImpl;

public class CompanyServiceImpl extends RestServiceImpl<Company> implements CompanyService {

	public CompanyServiceImpl(CarsyncSession carsyncSession) {
		super(carsyncSession,
				"/api/v4/division",
				Company.class,
				CompanyResponseDTO.class,
				null,
				null);
	}
}
