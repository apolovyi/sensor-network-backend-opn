package th.sensornetwork.model;


public class MeasurementPair {

	private Long   ts;
	private Double value;

	public MeasurementPair() {
	}

	public Long getTs() {
		return ts;
	}

	public void setTs(Long ts) {
		this.ts = ts;
	}

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}
}