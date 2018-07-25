package th.sensornetwork.model.couchdb;

import lombok.Data;
import lombok.NonNull;
import org.ektorp.support.CouchDbDocument;
import org.ektorp.support.TypeDiscriminator;

import java.util.HashSet;
import java.util.Set;

@Data
public class Sensor extends CouchDbDocument {

	@TypeDiscriminator
	@NonNull
	private String      documentType;
	private Set<String> measurements;
	@NonNull
	private String      sensorName;
	@NonNull
	private String      sensorType;
	@NonNull
	private String      room;
	@NonNull
	private String      sensorProductID;

	/*public Sensor() {
		this.documentType = this.getClass().getSimpleName();
		this.measurements = new HashSet<>();
	}*/

	public Sensor(String id, String sensorName, String sensorType, String room, String
			sensorProductID, Set<String> measurements) {
		this.setId(id);
		this.sensorName = sensorName;
		this.sensorType = sensorType;
		this.room = room;
		this.sensorProductID = sensorProductID;
		this.documentType = this.getClass().getSimpleName();
		this.measurements = measurements;
	}

	/*public void addMeasurement(String measurements) {
		this.measurements.add(measurements);
	}

	public void addMeasurements(Set<String> measurements) {
		this.measurements.addAll(measurements);
	}*/

}
