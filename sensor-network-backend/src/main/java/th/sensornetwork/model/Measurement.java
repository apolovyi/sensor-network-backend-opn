package th.sensornetwork.model;

import org.ektorp.support.CouchDbDocument;
import org.ektorp.support.TypeDiscriminator;

import java.util.HashSet;
import java.util.Set;

public class Measurement extends CouchDbDocument {

	@TypeDiscriminator
	private String               documentType;
	private String               measurementName;
	private String               unit;
	private String               sensorID;

	private Set<MeasurementPair> measurementPairs;

	public Measurement(String measurementName, String sensorID) {
		super.setId(sensorID + "_" + measurementName);
		this.measurementName = measurementName;
		this.sensorID = sensorID;
		this.documentType = this.getClass().getSimpleName();
		this.measurementPairs = new HashSet<>();
	}

	public String getDocumentType() {
		return documentType;
	}

	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}

	public String getMeasurementName() {
		return measurementName;
	}

	public void setMeasurementName(String measurementName) {
		this.measurementName = measurementName;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getSensorID() {
		return sensorID;
	}

	public void setSensorID(String sensorID) {
		this.sensorID = sensorID;
	}

	public Set<MeasurementPair> getMeasurementPairs() {
		return measurementPairs;
	}

	public void setMeasurementPairs(Set<MeasurementPair> measurementPairs) {
		this.measurementPairs = measurementPairs;
	}
}