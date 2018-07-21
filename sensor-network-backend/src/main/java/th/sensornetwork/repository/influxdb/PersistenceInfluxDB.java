package th.sensornetwork.repository.influxdb;

import org.influxdb.dto.Point;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.influxdb.InfluxDBTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class PersistenceInfluxDB {

	@Autowired
	private InfluxDBTemplate<Point> influxDBTemplate;

	public void writeData(String sensorName, String measurement, JSONObject
			receivedData) {

		Double val  = receivedData.getDouble("val");
		Long   ts   = receivedData.getLong("ts");
		String unit = "";
		if (receivedData.has("hm_unit"))
			unit = receivedData.getString("hm_unit");
		try {
			influxDBTemplate.write(Point.measurement(sensorName)
					.time(ts, TimeUnit.MILLISECONDS)
					.tag("entity", measurement)
					.tag("unit", unit)
					.addField("value", val)
					.build());
			System.out.println("Writing to influxdb, Sensor: " + sensorName + " " +
					"Entity: " + measurement + " Value: " + val);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
