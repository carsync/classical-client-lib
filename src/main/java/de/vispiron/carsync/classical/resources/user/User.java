package de.vispiron.carsync.classical.resources.user;

import de.vispiron.carsync.classical.domain.CarsyncIdDomain;
import de.vispiron.carsync.classical.resources.company.Company;
import de.vispiron.carsync.classical.resources.division.Division;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class User extends CarsyncIdDomain {

	private String firstName;

	private String lastName;

	private Integer sex;

	private Boolean realEmail;

	private Boolean checkLicense;

	private Boolean employed;

	private Boolean access;

	private String personnelNumber;

	private String email;

	private String mobilePhone;

	// private Object driverRole;
	// private Object fleetManagerRole;

	private String extraInfo;

	private String defaultTripType;

	private Company company;

	private Division division;

	public User(Integer id) {
		super(id);
	}

	public void setCompanyId(Integer id) {
		if (company == null || !company.getId().equals(id)) {
			company = new Company(id);
		}
	}

	public void setDivisionId(Integer id) {
		if (division == null || !division.getId().equals(id)) {
			division = new Division(id);
		}
	}
}
