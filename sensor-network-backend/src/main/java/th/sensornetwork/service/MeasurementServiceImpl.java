package th.sensornetwork.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import th.sensornetwork.model.couchdb.Measurement;
import th.sensornetwork.repository.couchdb.repository.MeasurementRepository;

import java.util.List;

@Service
public class MeasurementServiceImpl implements MeasurementService {

	private MeasurementRepository measurementRepository;

	@Autowired
	public MeasurementServiceImpl(MeasurementRepository measurementRepository) {
		this.measurementRepository = measurementRepository;
	}

	@Override
	public List<Measurement> getMeasurementsBySensor(String id) {
		return measurementRepository.findBySensorID(id);
	}

	@Override
	public List<Measurement> getAllEntities() {
		return measurementRepository.getAll();
	}

	@Override
	public Measurement getMeasurementByID(String measurementID) {
		return measurementRepository.get(measurementID);
	}
}
