package th.sensornetwork.model.couchdb;

import lombok.Data;
import org.ektorp.support.CouchDbDocument;

import java.util.HashSet;
import java.util.Set;

@Data
public class TemporaryData extends CouchDbDocument {

	private Set<TemporarySensor> temporarySensors;

	public TemporaryData() {
		super.setId("TemporaryData");
		this.temporarySensors = new HashSet<>();
	}


}






