package de.vispiron.carsync.classical.resources.division;

import de.vispiron.carsync.classical.domain.CarsyncIdDomain;
import de.vispiron.carsync.classical.resources.company.Company;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Division extends CarsyncIdDomain {

	private String name;

	private String tagNumber;

	private String costUnit;

	private Company company;

	public Division(Integer id) {
		super(id);
	}
}
