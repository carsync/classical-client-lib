package de.vispiron.carsync.classical.resources.division;

import de.vispiron.carsync.classical.dto.CarsyncDTO;
import de.vispiron.carsync.classical.resources.company.CompanyResponseDTO;

public class DivisionResponseDTO extends CarsyncDTO {

	public Integer id;

	public String name;

	public String tagNumber;

	public String costUnit;

	public CompanyResponseDTO company;

}
