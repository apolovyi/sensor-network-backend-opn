package th.sensornetwork.model.couchdb;

import lombok.Data;
import lombok.NonNull;
import org.ektorp.support.CouchDbDocument;
import org.ektorp.support.TypeDiscriminator;

import java.util.HashSet;
import java.util.Set;

@Data
public class Measurement extends CouchDbDocument {

	@TypeDiscriminator
	@NonNull
	private String               documentType;
	@NonNull
	private String               measurementName;
	private String               unit;
	@NonNull
	private String               sensorID;

	private Set<MeasurementPair> measurementPairs;

	public Measurement() {
		this.documentType = this.getClass().getSimpleName();
		this.measurementPairs = new HashSet<>();
	}

	public Measurement(String measurementName, String sensorID, String unit) {
		super.setId(sensorID + "_" + measurementName);
		//super.setId(sensorID + "_" + measurementName + "_" + date);
		this.measurementName = measurementName;
		this.unit = unit;
		this.sensorID = sensorID;
		this.documentType = this.getClass().getSimpleName();
		this.measurementPairs = new HashSet<>();
	}

	public Measurement(String measurementName, String sensorID) {
		super.setId(sensorID + "_" + measurementName);
		//super.setId(sensorID + "_" + measurementName + "_" + date);
		this.measurementName = measurementName;
		this.sensorID = sensorID;
		this.documentType = this.getClass().getSimpleName();
		this.measurementPairs = new HashSet<>();
	}

}