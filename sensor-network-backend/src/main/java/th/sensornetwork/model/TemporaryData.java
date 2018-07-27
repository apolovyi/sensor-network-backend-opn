package th.sensornetwork.model;

import lombok.Data;
import org.ektorp.support.CouchDbDocument;

import java.util.HashSet;
import java.util.Set;

@Data
public class TemporaryData extends CouchDbDocument {

	private Set<SensorCandidate> sensorCandidates;

	public TemporaryData() {
		super.setId("TemporaryData");
		this.sensorCandidates = new HashSet<>();
	}


}






