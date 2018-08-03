package th.sensornetwork.model;

import org.ektorp.support.CouchDbDocument;

import java.util.Map;

public class SensorProduct extends CouchDbDocument {

	private String documentType;
	private String producer;
	private String type;
	private Map<String,String> semantic;

	public String getDocumentType() {
		return documentType;
	}

	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}

	public String getProducer() {
		return producer;
	}

	public void setProducer(String producer) {
		this.producer = producer;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Map<String, String> getSemantic() {
		return semantic;
	}

	public void setSemantic(Map<String, String> semantic) {
		this.semantic = semantic;
	}

	public SensorProduct(String producer, String type, Map<String, String> semantic) {
		super.setId(producer+":"+type);
		this.semantic = semantic;
		this.producer = producer;
		this.type = type;
		this.documentType = this.getClass().getSimpleName();
	}
}
