package th.sensornetwork.model;

import java.util.Objects;

public class MeasurementCandidate {

	private String measurement;
	private String values;

	public MeasurementCandidate(String measurement, String values) {
		this.measurement = measurement;
		this.values = values;
	}

	public MeasurementCandidate() {
	}

	public String getMeasurement() {
		return measurement;
	}

	public void setMeasurement(String measurement) {
		this.measurement = measurement;
	}

	public String getValues() {
		return values;
	}

	public void setValues(String values) {
		this.values = values;
	}

	@Override
	public int hashCode() {
		return Objects.hash(measurement);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this)
			return true;
		if (!(obj instanceof MeasurementCandidate)) {
			return false;
		}

		MeasurementCandidate measurementCandidate = (MeasurementCandidate) obj;
		return Objects.equals(measurement, measurementCandidate.measurement);
	}

}