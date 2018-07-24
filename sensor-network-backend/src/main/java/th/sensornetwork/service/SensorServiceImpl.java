package th.sensornetwork.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import th.sensornetwork.model.couchdb.Sensor;
import th.sensornetwork.repository.couchdb.repository.SensorRepository;

import java.util.List;

@Service
public class SensorServiceImpl implements SensorService {

	private SensorRepository sensorRepository;

	@Autowired
	public SensorServiceImpl(SensorRepository sensorRepository) {
		this.sensorRepository = sensorRepository;
	}

	@Override
	public List<Sensor> getAllSensors() {
		return sensorRepository.getAll();
	}

	@Override
	public List<Sensor> getSensorsByType(String type) {
		return sensorRepository.findBySensorType(type);
	}

	@Override
	public List<Sensor> getSensorsByLocation(String location) {
		return sensorRepository.findByRoom(location);
	}

	@Override
	public Sensor getSensorById(String id) {
		return sensorRepository.get(id);
	}

	@Override
	public Sensor updateSensor(Sensor sensor) {
		//Sensor oldSensor = sensorRepository.get(sensor.getId());
		sensorRepository.update(sensor);
		Sensor newSensor = sensorRepository.get(sensor.getId());
		return newSensor;
	}
}
