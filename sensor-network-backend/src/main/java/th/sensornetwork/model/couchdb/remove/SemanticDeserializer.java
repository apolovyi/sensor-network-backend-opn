package th.sensornetwork.model.couchdb.remove;

import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.KeyDeserializer;

import java.io.IOException;

public class SemanticDeserializer extends KeyDeserializer {

	@Override
	public Object deserializeKey(String s, DeserializationContext deserializationContext) throws IOException {
		return null;
	}
}
