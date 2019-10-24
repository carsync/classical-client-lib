package de.vispiron.carsync.classical.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.Iterator;
import java.util.Map;

public class JsonDiff {

	public static ObjectNode diff(ObjectNode newNode, ObjectNode baseNode) {
		ObjectNode resultNode = JsonNodeFactory.instance.objectNode();
		Iterator<Map.Entry<String, JsonNode>> iter = newNode.fields();

		while (iter.hasNext()) {
			Map.Entry<String, JsonNode> entry = iter.next();
			if (!entry.getValue().equals(baseNode.get(entry.getKey()))) {
				resultNode.set(entry.getKey(), entry.getValue());
			}
		}

		return resultNode;
	}

}