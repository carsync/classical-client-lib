package de.vispiron.carsync.classical.resources.company;

import de.vispiron.carsync.classical.domain.CarsyncIdDomain;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Company extends CarsyncIdDomain {

	private String name;

	public Company(Integer id) {
		super(id);
	}
}
