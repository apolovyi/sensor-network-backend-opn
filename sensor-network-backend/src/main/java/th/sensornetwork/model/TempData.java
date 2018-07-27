package th.sensornetwork.model;

import lombok.Data;
import org.ektorp.support.CouchDbDocument;

import java.util.HashSet;
import java.util.Set;

@Data
public class TempData extends CouchDbDocument {

	private Set<SensorCandidate> sensorCandidates;

	public TempData() {
		super.setId("TempData");
		this.sensorCandidates = new HashSet<>();
	}


}






