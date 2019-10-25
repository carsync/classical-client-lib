package de.vispiron.carsync.classical.converter.modelmapper;

import de.vispiron.carsync.classical.converter.TemporalConverter;
import org.modelmapper.AbstractConverter;

import java.time.OffsetDateTime;

public class CustomStringToOffsetDateTimeConverter extends AbstractConverter<String, OffsetDateTime> {

	@Override
	protected OffsetDateTime convert(String dateString) {

		if (dateString == null) {
			return null;
		}

		return TemporalConverter.parseOffsetDateTimeString(dateString);
	}
}
