package th.sensornetwork.model.couchdb.remove;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import th.sensornetwork.model.couchdb.SensorProduct;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class SensorProductDeserializer extends JsonDeserializer<SensorProduct> {
	@Override
	public SensorProduct deserialize(JsonParser jp, DeserializationContext
			deserializationContext) throws IOException, JsonProcessingException {
		ObjectCodec oc   = jp.getCodec();
		JsonNode    node = oc.readTree(jp);

		final String        id        = node.get("_id").asText();
		final String        name      = node.get("hersteller").asText();
		Map<String, String> semantic = new HashMap<>();
		Iterator<String> iterator = node.get("semantic").fieldNames();

		SensorProduct sensorProduct = new SensorProduct();
		return sensorProduct;
	}
}
