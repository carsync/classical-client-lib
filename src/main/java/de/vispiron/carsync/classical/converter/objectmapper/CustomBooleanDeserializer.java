package de.vispiron.carsync.classical.converter.objectmapper;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;

public class CustomBooleanDeserializer extends JsonDeserializer<Boolean> {

	@Override
	public Boolean deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
		JsonToken t = p.getCurrentToken();
		if (t == JsonToken.VALUE_TRUE) {
			return Boolean.TRUE;
		}
		if (t == JsonToken.VALUE_FALSE) {
			return Boolean.FALSE;
		}
		return _parseBoolean(p, ctxt);
	}

	private final Boolean _parseBoolean(JsonParser p, DeserializationContext ctxt) throws IOException {
		JsonToken t = p.getCurrentToken();
		if (t == JsonToken.VALUE_NULL) {
			return null;
		}

		// should accept ints too, (0 == false, otherwise true)
		if (t == JsonToken.VALUE_NUMBER_INT) {
			return !"0".equals(p.getText());
		}
		// And finally, let's allow Strings to be converted too
		if (t == JsonToken.VALUE_STRING) {
			String text = p.getText().trim();
			// [databind#422]: Allow aliases
			if ("true".equals(text) || "True".equals(text) || "1".equals(text)) {
				return Boolean.TRUE;
			}
			if ("false".equals(text) || "False".equals(text) || "0".equals(text)) {
				return Boolean.FALSE;
			}
			return (Boolean) ctxt.handleWeirdStringValue(Boolean.class, text,
					"only \"true\", \"1\", \"false\", \"0\" recognized");
		}
		// usually caller should have handled but:
		if (t == JsonToken.VALUE_TRUE) {
			return Boolean.TRUE;
		}
		if (t == JsonToken.VALUE_FALSE) {
			return Boolean.FALSE;
		}
		// Otherwise, no can do:
		return (Boolean) ctxt.handleUnexpectedToken(Boolean.class, p);
	}
}