package de.vispiron.carsync.classical.converter.modelmapper;

import de.vispiron.carsync.classical.converter.TemporalConverter;
import org.modelmapper.AbstractConverter;

import java.time.Instant;

public class CustomStringToInstantConverter extends AbstractConverter<String, Instant> {

	@Override
	protected Instant convert(String dateString) {

		if (dateString == null) {
			return null;
		}

		return TemporalConverter.parseInstantString(dateString);
	}
}
