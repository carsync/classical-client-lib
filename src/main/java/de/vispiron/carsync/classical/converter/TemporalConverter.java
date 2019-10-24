package de.vispiron.carsync.classical.converter;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoField;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class TemporalConverter {

	private final static String BASE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSSSS";

	private final static String BASE_FORMAT_WITHOUT_MILLISECONDS = "yyyy-MM-dd'T'HH:mm:ss";

	public final static DateTimeFormatter LOCALE_DATE_TIME_FORMAT = DateTimeFormatter.ofPattern(BASE_FORMAT);

	public final static DateTimeFormatter OFFSET_DATE_TIME_FORMAT = DateTimeFormatter.ofPattern(BASE_FORMAT + "XXX");

	public final static DateTimeFormatter INSTANT_FORMAT = DateTimeFormatter.ofPattern(BASE_FORMAT + "XXX")
			.withZone(ZoneId.of("UTC"));

	public final static DateTimeFormatter INSTANT_FORMAT_WITHOUT_MILLISECONDS = DateTimeFormatter.ofPattern(
		BASE_FORMAT_WITHOUT_MILLISECONDS + "XXX")
			.withZone(ZoneId.of("UTC"));

	public final static DateTimeFormatter ZONED_DATE_TIME_FORMAT = DateTimeFormatter.ofPattern(BASE_FORMAT + " VV");

	private final static Set<DateTimeFormatter> LOCALE_DATE_TIME_FORMATS = new HashSet<>();

	private final static Set<DateTimeFormatter> OFFSET_DATE_TIME_FORMATS = new HashSet<>();

	private final static Set<DateTimeFormatter> ZONED_DATE_TIME_FORMATS = new HashSet<>();

	static {
		LOCALE_DATE_TIME_FORMATS.add(getFormatter(""));
		OFFSET_DATE_TIME_FORMATS.add(getFormatter("Z"));
		OFFSET_DATE_TIME_FORMATS.add(getFormatter("X"));
		OFFSET_DATE_TIME_FORMATS.add(getFormatter("XXX"));
		ZONED_DATE_TIME_FORMATS.add(getFormatter(" VV"));
	}

	private static DateTimeFormatter getFormatter(final String extend) {
		return new DateTimeFormatterBuilder()
				.appendPattern("yyyy[-MM[-dd['T'[HH:mm[:ss]]]]]")
				.optionalStart()
				.appendFraction(ChronoField.MICRO_OF_SECOND, 0, 6, true)
				.optionalEnd()
				.appendPattern(extend)
				.parseDefaulting(ChronoField.DAY_OF_MONTH, 1)
				.parseDefaulting(ChronoField.MONTH_OF_YEAR, 1)
				.parseDefaulting(ChronoField.HOUR_OF_DAY, 0)
				.parseDefaulting(ChronoField.MINUTE_OF_HOUR, 0)
				.parseDefaulting(ChronoField.SECOND_OF_MINUTE, 0)
				.toFormatter();
	}

	public static Date parseDateString(final String valueString) {
		return new Date(parseOffsetDateTimeString(valueString).toInstant().toEpochMilli());
	}

	public static Instant parseInstantString(final String valueString) {
		return parseOffsetDateTimeString(valueString).toInstant();
	}

	public static LocalDateTime parseLocalDateTimeString(final String valueString) {
		for (DateTimeFormatter format : LOCALE_DATE_TIME_FORMATS) {
			try {
				return LocalDateTime.parse(valueString, format);
			} catch (DateTimeParseException ignored) {
			}
		}

		throw new RuntimeException(
				valueString + " is not of expected date time format (ISO 8601) WITHOUT time offset or time zone.");
	}

	public static OffsetDateTime parseOffsetDateTimeString(final String valueString) {
		for (DateTimeFormatter format : OFFSET_DATE_TIME_FORMATS) {
			try {
				return OffsetDateTime.parse(valueString, format);
			} catch (DateTimeParseException ignored) {
			}
		}

		throw new RuntimeException(
				valueString + " is not of expected date time format (ISO 8601) WITH time offset");
	}

	public static ZonedDateTime parseZonedDateTimeString(final String valueString) {
		for (DateTimeFormatter format : ZONED_DATE_TIME_FORMATS) {
			try {
				return ZonedDateTime.parse(valueString, format);
			} catch (DateTimeParseException ignored) {
			}
		}

		throw new RuntimeException(
				valueString + " is not of expected date time format (ISO 8601) WITH time zone (and not offset)");
	}
}
