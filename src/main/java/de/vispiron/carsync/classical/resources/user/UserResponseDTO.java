package de.vispiron.carsync.classical.resources.user;

import de.vispiron.carsync.classical.dto.CarsyncDTO;
import de.vispiron.carsync.classical.resources.company.CompanyResponseDTO;
import de.vispiron.carsync.classical.resources.division.DivisionResponseDTO;

public class UserResponseDTO extends CarsyncDTO {

	public Integer id;

	public String firstName;

	public String lastName;

	public Integer sex;

	public Boolean realEmail;

	public Boolean checkLicense;

	public Boolean employed;

	public Boolean access;

	public String personnelNumber;

	public String email;

	public String mobilePhone;

	public String extraInfo;

	public String defaultTripType;

	public CompanyResponseDTO company;

	public DivisionResponseDTO division;
}
