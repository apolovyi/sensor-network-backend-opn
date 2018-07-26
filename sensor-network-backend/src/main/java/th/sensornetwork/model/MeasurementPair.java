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

}