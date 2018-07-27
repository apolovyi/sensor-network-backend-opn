package th.sensornetwork.model;

import lombok.Data;
import lombok.NonNull;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Data
public class SensorCandidate {
	@NonNull
	private String                    sensorID;
	private Set<MeasurementCandidate> measurements;

	public SensorCandidate(String sensorID) {
		this.sensorID = sensorID;
		this.measurements = new HashSet<>();
	}

	@Override
	public int hashCode() {
		return Objects.hash(sensorID);
	}

	@Override
	public boolean equals(Object obj) {

		if (obj == this)
			return true;
		if (!(obj instanceof SensorCandidate)) {
			return false;
		}
		SensorCandidate sensorCandidate = (SensorCandidate) obj;
		return Objects.equals(sensorID, sensorCandidate.sensorID);
	}
}