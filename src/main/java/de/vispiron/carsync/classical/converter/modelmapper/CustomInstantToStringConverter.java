package de.vispiron.carsync.classical.converter.modelmapper;

import de.vispiron.carsync.classical.converter.TemporalConverter;
import org.modelmapper.AbstractConverter;

import java.time.Instant;
import java.time.format.DateTimeFormatter;

public class CustomInstantToStringConverter extends AbstractConverter<Instant, String> {

	@Override
	protected String convert(Instant date) {
		return date != null ? getDateTimeFormatter(date).format(date) : null;
	}

	private DateTimeFormatter getDateTimeFormatter(Instant date) {
		return date.getNano() == 0 ?
				TemporalConverter.INSTANT_FORMAT_WITHOUT_MILLISECONDS : TemporalConverter.INSTANT_FORMAT;
	}
}
