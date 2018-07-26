package th.sensornetwork.service;

import th.sensornetwork.model.Sensor;

import java.util.List;

public interface SensorService {

	List<Sensor> getAllSensors();

	Sensor getSensorById(String id);

	Sensor updateSensor(Sensor sensor);

	boolean deleteSensor(String sensorID);
}
