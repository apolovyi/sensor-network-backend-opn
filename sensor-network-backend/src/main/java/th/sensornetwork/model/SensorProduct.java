package th.sensornetwork.model;

import lombok.Data;
import lombok.NonNull;
import org.ektorp.support.CouchDbDocument;

import java.util.HashMap;
import java.util.Map;

@Data
public class SensorProduct extends CouchDbDocument {

	@NonNull
	private String documentType;
	@NonNull
	private String producer;
	@NonNull
	private String type;
	@NonNull
	private Map<String,String> semantic;

	public SensorProduct(String producer, String type, Map<String, String> semantic) {
		super.setId(producer+":"+type);
		this.semantic = semantic;
		this.producer = producer;
		this.type = type;
		this.documentType = this.getClass().getSimpleName();
	}
}
