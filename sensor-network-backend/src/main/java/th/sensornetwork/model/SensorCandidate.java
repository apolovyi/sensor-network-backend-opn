package th.sensornetwork.model;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class SensorCandidate {
	private String                    sensorID;
	private Set<MeasurementCandidate> measurements;

	public SensorCandidate(String sensorID) {
		this.sensorID = sensorID;
		this.measurements = new HashSet<>();
	}

	public String getSensorID() {
		return sensorID;
	}

	public void setSensorID(String sensorID) {
		this.sensorID = sensorID;
	}

	public Set<MeasurementCandidate> getMeasurements() {
		return measurements;
	}

	public void setMeasurements(Set<MeasurementCandidate> measurements) {
		this.measurements = measurements;
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