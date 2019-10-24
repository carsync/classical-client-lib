package de.vispiron.carsync.classical.service;

import de.vispiron.carsync.classical.domain.CarsyncDomain;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class Page<T extends CarsyncDomain> {

	List<T> content;

	Integer rangeStart;

	Integer totalSize;

}
