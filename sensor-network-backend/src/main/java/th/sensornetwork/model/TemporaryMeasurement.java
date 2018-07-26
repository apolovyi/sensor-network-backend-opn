package th.sensornetwork.model.couchdb;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TemporaryMeasurement {

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
		if (!(obj instanceof TemporaryMeasurement)) {
			return false;
		}

		TemporaryMeasurement temporaryMeasurement = (TemporaryMeasurement) obj;
		return Objects.equals(measurement, temporaryMeasurement.measurement);
	}

}