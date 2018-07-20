package th.sensornetwork.service;

import th.sensornetwork.model.couchdb.Sensor;

import java.util.List;

public interface SensorService {

	List<Sensor> getAllSensors();

	List<Sensor> getSensorsByType(String type);

	List<Sensor> getSensorsByLocation(String location);

	Sensor getSensorById(String id);


}
