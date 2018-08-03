package th.sensornetwork.model;

import org.ektorp.support.CouchDbDocument;

import java.util.HashSet;
import java.util.Set;

public class TempData extends CouchDbDocument {

	private Set<SensorCandidate> sensorCandidates;

	public TempData() {
		super.setId("TempData");
		this.sensorCandidates = new HashSet<>();
	}

	public Set<SensorCandidate> getSensorCandidates() {
		return sensorCandidates;
	}

	public void setSensorCandidates(Set<SensorCandidate> sensorCandidates) {
		this.sensorCandidates = sensorCandidates;
	}
}






