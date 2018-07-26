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

	public Measurement(String measurementName, String sensorID) {
		super.setId(sensorID + "_" + measurementName);
		this.measurementName = measurementName;
		this.sensorID = sensorID;
		this.documentType = this.getClass().getSimpleName();
		this.measurementPairs = new HashSet<>();
	}

}