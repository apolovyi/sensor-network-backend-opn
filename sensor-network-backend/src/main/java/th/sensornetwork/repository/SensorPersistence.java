package th.sensornetwork.repository;

import org.ektorp.CouchDbConnector;
import org.ektorp.DocumentNotFoundException;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ImportResource;
import org.springframework.stereotype.Component;
import th.sensornetwork.model.*;

import java.util.*;
import java.util.stream.Collectors;

@Component
@ImportResource("classpath:couchdb-config.xml")
public class SensorPersistence {

	private CouchDbConnector        couchDB;
	private SensorRepository        sensorRepository;
	private MeasurementRepository   measurementRepository;
	private SensorProductRepository sensorProductRepository;
	private SettingsRepository      settingsRepository;

	@Autowired
	public SensorPersistence(CouchDbConnector couchDB, SensorRepository sensorRepository,
			MeasurementRepository measurementRepository, SensorProductRepository
			sensorProductRepository, SettingsRepository settingsRepository) {
		this.couchDB = couchDB;
		this.sensorRepository = sensorRepository;
		this.measurementRepository = measurementRepository;
		this.sensorProductRepository = sensorProductRepository;
		this.settingsRepository = settingsRepository;
	}

	public CouchDbConnector getCouchDB() {
		return couchDB;
	}

	public SettingsRepository getSettingsRepository() {
		return settingsRepository;
	}

	public boolean createSensor(String sensorID, String sensorName, String room, String
			sensorProductID, SensorCandidate sensorCandidate) {

		Set<MeasurementCandidate> measurementCandidates = sensorCandidate.getMeasurements();
		Set<String> measurementNames = measurementCandidates.stream()
				.map(MeasurementCandidate::getMeasurement)
				.collect(Collectors.toSet());
		Sensor sensor = new Sensor(sensorID, sensorName, room, sensorProductID,
				measurementNames);
		try {
			sensorRepository.add(sensor);
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		Map<String, String> semantic = sensorProductRepository.get(sensorProductID)
				.getSemantic();
		for (MeasurementCandidate tms : measurementCandidates) {
			createMeasurement(sensorID, tms.getMeasurement(), semantic, new JSONObject(tms
					.getValues()));
		}

		return sensorRepository.contains(sensor.getId());
	}

	private boolean createMeasurement(String sensorId, String measurementName, Map<String,
			String> semantic, JSONObject receivedData) {

		Measurement measurement = new Measurement(measurementName, sensorId);

		try {
			if (receivedData.has(semantic.get("unit")))
				measurement.setUnit(receivedData.getString(semantic.get("unit")));
		}
		catch (JSONException e) {
			e.printStackTrace();
		}

		MeasurementPair measurementPair = new MeasurementPair();
		Double          value           = receivedData.getDouble(semantic.get("value"));
		measurementPair.setValue(value);
		measurementPair.setTs(receivedData.getLong(semantic.get("ts")));

		measurement.getMeasurementPairs().add(measurementPair);

		if (!measurementRepository.contains(measurement.getId())) {
			measurementRepository.add(measurement);
		}
		return measurementRepository.contains(measurement.getId());
	}


	public void updateSensor(Sensor sensor, String measurementName, Map<String, String>
			semantic, JSONObject receivedData) {

		Sensor      data         = couchDB.get(Sensor.class, sensor.getId());
		Set<String> measurements = data.getMeasurements();
		//Add new measurement to repository
		if (!measurements.contains(measurementName)) {
			measurements.add(measurementName);
			sensor.setMeasurements(measurements);
			sensorRepository.update(sensor);
			createMeasurement(sensor.getId(), measurementName, semantic, receivedData);
		}
		// Update existing measurement
		else {
			updateMeasurement(sensor.getId(), measurementName, receivedData);
		}
	}

	private void updateMeasurement(String sensorID, String measurementName, JSONObject
			receivedData) {

		MeasurementPair measurementPair = new MeasurementPair();
		String          spID            = sensorRepository.get(sensorID).getSensorProductID();
		SensorProduct   sensorProduct   = sensorProductRepository.get(spID);

		Measurement measurement = null;

		try {
			measurement = measurementRepository.get(sensorID + "_" + measurementName);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		if (measurement == null) {
			if (createMeasurement(sensorID, measurementName, sensorProduct.getSemantic(),
					receivedData)) {
				measurement = measurementRepository.get(sensorID + "_" + measurementName);
			}
		}

		measurementPair.setValue(receivedData.getDouble(sensorProduct.getSemantic()
				.get("value")));
		measurementPair.setTs(receivedData.getLong(sensorProduct.getSemantic().get("ts")));

		Objects.requireNonNull(measurement).getMeasurementPairs().add(measurementPair);

		try {
			measurementRepository.update(measurement);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void deleteTemporaryData() {
		try {
			couchDB.delete(couchDB.get(TempData.class, "TempData"));
		}
		catch (DocumentNotFoundException e) {
			e.printStackTrace();
		}
		couchDB.create(new TempData());
	}

	public TempData getTemporaryData() {
		TempData td = new TempData();
		try {
			td = couchDB.get(TempData.class, "TempData");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return td;
	}


}
