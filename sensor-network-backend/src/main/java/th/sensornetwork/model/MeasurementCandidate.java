package th.sensornetwork.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MeasurementCandidate {

	private String measurement;
	private String values;

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