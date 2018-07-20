package th.sensornetwork.model.couchdb;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
public class MeasurementPair {

	@NonNull
	private Long   ts;
	@NonNull
	private Double value;

	/*@Override
	public int hashCode() {
		return Objects.hash(ts,value);
	}

	@Override
	public boolean equals(Object obj) {

		if (obj == this) return true;
		if (!(obj instanceof MeasurementPair)) {
			return false;
		}
		MeasurementPair measurementPair = (MeasurementPair) obj;
		return Objects.equals(ts, measurementPair.ts)&& Objects.equals(value, measurementPair
		.value);
	}*/
}