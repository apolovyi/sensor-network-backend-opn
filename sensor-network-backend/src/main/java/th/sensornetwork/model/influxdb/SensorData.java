package th.sensornetwork.model.influxdb;

import org.influxdb.annotation.Column;
import org.influxdb.annotation.Measurement;

import java.time.Instant;

@Measurement(name = "all")
public class SensorData {

	@Column(name = "time")
	private Instant time;
	@Column(name = "sensor", tag = true)
	private String  sensor;
	@Column(name = "messwert", tag = true)
	private String  messwert;
	@Column(name = "location", tag = true)
	private String  location;
	@Column(name = "unit", tag = true)
	private String  unit;
	@Column(name = "value")
	private Double  value;

	public Instant getTime() {
		return time;
	}

	public void setTime(Instant time) {
		this.time = time;
	}

	public String getSensor() {
		return sensor;
	}

	public void setSensor(String sensor) {
		this.sensor = sensor;
	}

	public String getMesswert() {
		return messwert;
	}

	public void setMesswert(String messwert) {
		this.messwert = messwert;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}
}
