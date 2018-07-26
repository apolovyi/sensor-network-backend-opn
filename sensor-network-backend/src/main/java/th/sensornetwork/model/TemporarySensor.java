package th.sensornetwork.model.couchdb;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Data
public class TemporarySensor {
	@NonNull
	private String                    sensorID;
	private Set<TemporaryMeasurement> measurements;

	public TemporarySensor(String sensorID) {
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
		if (!(obj instanceof TemporarySensor)) {
			return false;
		}
		TemporarySensor temporarySensor = (TemporarySensor) obj;
		return Objects.equals(sensorID, temporarySensor.sensorID);
	}
}