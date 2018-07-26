package th.sensornetwork.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import th.sensornetwork.model.couchdb.Measurement;
import th.sensornetwork.model.couchdb.Sensor;
import th.sensornetwork.repository.couchdb.repository.MeasurementRepository;
import th.sensornetwork.repository.couchdb.repository.SensorRepository;

import java.util.List;

@Service
public class SensorServiceImpl implements SensorService {

	private SensorRepository      sensorRepository;
	private MeasurementRepository measurementRepository;

	@Autowired
	public SensorServiceImpl(SensorRepository sensorRepository, MeasurementRepository measurementRepository) {
		this.sensorRepository = sensorRepository;
		this.measurementRepository =measurementRepository;
	}

	@Override
	public List<Sensor> getAllSensors() {
		return sensorRepository.getAll();
	}

	@Override
	public Sensor getSensorById(String id) {
		return sensorRepository.get(id);
	}

	@Override
	public Sensor updateSensor(Sensor sensor) {
		sensorRepository.update(sensor);
		return sensorRepository.get(sensor.getId());
	}

	@Override
	public boolean deleteSensor(String sensorID) {
		Sensor sensor = sensorRepository.get(sensorID);
		for (String ms: sensor.getMeasurements()){
			measurementRepository.remove(measurementRepository.get(sensorID+"_"+ms));
		}
		sensorRepository.remove(sensor);
		return !sensorRepository.contains(sensorID);
	}
}
