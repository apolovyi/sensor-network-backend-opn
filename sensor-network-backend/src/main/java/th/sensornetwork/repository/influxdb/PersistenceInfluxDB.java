package th.sensornetwork.repository.influxdb;

import org.influxdb.dto.Point;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.influxdb.InfluxDBTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.TimeUnit;

@Component
public class PersistenceInfluxDB {

	@Autowired
	private InfluxDBTemplate<Point> influxDBTemplate;

	public void writeData(String sensorName, String measurement, String room, Map<String,String> semantic, JSONObject
			receivedData) {

		Double val  = receivedData.getDouble(semantic.get("value"));
		Long   ts   = receivedData.getLong(semantic.get("ts"));
		String unit = "";
		if (receivedData.has(semantic.get("unit")))
			unit = receivedData.getString(semantic.get("unit"));
		try {
			influxDBTemplate.write(Point.measurement(sensorName)
					.time(ts, TimeUnit.MILLISECONDS)
					.tag("entity", measurement)
					.tag("unit", unit)
					.tag("room", room)
					.addField("value", val)
					.build());
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
