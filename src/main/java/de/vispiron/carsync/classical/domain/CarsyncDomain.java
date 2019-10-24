package de.vispiron.carsync.classical.domain;

import de.vispiron.carsync.classical.dto.CarsyncDTO;
import de.vispiron.carsync.classical.service.RestService;
import lombok.*;

import java.io.Serializable;
import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public abstract class CarsyncDomain {

	private CarsyncDTO databaseState;

	public abstract String getIdentifier();

}
