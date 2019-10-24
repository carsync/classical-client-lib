package de.vispiron.carsync.classical.domain;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public abstract class CarsyncIdDomain extends CarsyncDomain {

	private Integer id;

	public String getIdentifier() {
		return String.valueOf(this.id);
	}

}
