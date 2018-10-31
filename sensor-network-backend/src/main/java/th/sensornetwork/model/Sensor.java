package th.sensornetwork.model;

import org.ektorp.support.CouchDbDocument;
import org.ektorp.support.TypeDiscriminator;

import java.util.HashSet;
import java.util.Set;

public class Sensor extends CouchDbDocument {

	@TypeDiscriminator
	private String      documentType;
	private Set<String> measurements;
	private String      sensorName;
	private String      room;
	private String      sensorProductID;

	public Sensor(String id, String sensorName, String room, String
			sensorProductID, Set<String> measurements) {
		this.setId(id);
		this.sensorName = sensorName;
		this.room = room;
		this.sensorProductID = sensorProductID;
		this.documentType = this.getClass().getSimpleName();
		this.measurements = measurements;
	}

	public Sensor() {
	}

	public String getDocumentType() {
		return documentType;
	}

	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}

	public Set<String> getMeasurements() {
		return measurements;
	}

	public void setMeasurements(Set<String> measurements) {
		this.measurements = measurements;
	}

	public String getSensorName() {
		return sensorName;
	}

	public void setSensorName(String sensorName) {
		this.sensorName = sensorName;
	}

	public String getRoom() {
		return room;
	}

	public void setRoom(String room) {
		this.room = room;
	}

	public String getSensorProductID() {
		return sensorProductID;
	}

	public void setSensorProductID(String sensorProductID) {
		this.sensorProductID = sensorProductID;
	}
}
