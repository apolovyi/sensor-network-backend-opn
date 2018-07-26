package th.sensornetwork.repository.couchdb;

import org.ektorp.CouchDbConnector;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ImportResource;
import org.springframework.stereotype.Component;
import th.sensornetwork.model.couchdb.*;
import th.sensornetwork.repository.couchdb.repository.MeasurementRepository;
import th.sensornetwork.repository.couchdb.repository.SensorProductRepository;
import th.sensornetwork.repository.couchdb.repository.SensorRepository;
import th.sensornetwork.repository.couchdb.repository.SettingsRepository;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@ImportResource("classpath:couchdb-config.xml")
public class PersistenceCouchDB {

	private CouchDbConnector        couchDB;
	private SensorRepository        sensorRepository;
	private MeasurementRepository   measurementRepository;
	private SensorProductRepository sensorProductRepository;
	private SettingsRepository      settingsRepository;

	private final String SETTINGS_DOCUMENT_ID  = "Settings";
	private final String TEMP_DATA_DOCUMENT_ID = "TemporaryData";

	@Autowired
	public PersistenceCouchDB(CouchDbConnector couchDB, SensorRepository sensorRepository,
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

	public boolean createSensor(String sensorID, String sensorName, String room, String type,
			String sensorProductID, TemporarySensor temporarySensor) {

		Set<TemporaryMeasurement> temporaryMeasurements = temporarySensor.getMeasurements();
		Set<String> measurementNames = temporaryMeasurements.stream()
				.map(x -> x.getMeasurement())
				.collect(Collectors.toSet());
		Sensor sensor = new Sensor(sensorID, sensorName, type, room, sensorProductID,
				measurementNames);
		//sensor.addMeasurements(measurementNames);
		try {
			sensorRepository.add(sensor);
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		Map<String, String> semantic = sensorProductRepository.get(sensorProductID)
				.getSemantic();
		for (TemporaryMeasurement tms : temporaryMeasurements) {
			createMeasurement(sensorID, tms.getMeasurement(), semantic, new JSONObject(tms
					.getValues()));
		}

		return sensorRepository.contains(sensor.getId());
	}

	private boolean createMeasurement(String sensorId, String sensorEntityName, Map<String,
			String> semantic, JSONObject receivedData) {

		Measurement measurement = new Measurement(sensorEntityName, sensorId);

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


	public boolean updateSensor(Sensor sensor, String measurementName, Map<String, String>
			semantic, JSONObject receivedData) {

		Sensor data         = couchDB.get(Sensor.class, sensor.getId());
		Set    measurements = data.getMeasurements();
		//Add new measurement to repository
		if (!measurements.contains(measurementName)) {
			measurements.add(measurementName);
			sensor.setMeasurements(measurements);
			sensorRepository.update(sensor);
			System.out.println("Creating measurement in CouchDB, Sensor: " + sensor.getId() + " " +
					"Measurement: " + measurementName + " Value: " + receivedData);
			return createMeasurement(sensor.getId(), measurementName, semantic, receivedData);
		}
		// Update existing measurement
		else {
			System.out.println("Updating measurement in CouchDB, Sensor: " + sensor.getId() + " " +
					"Measurement: " + measurementName + " Value: " + receivedData);
			return updateMeasurement(sensor.getId(), measurementName, receivedData);
		}
	}

	private boolean updateMeasurement(String sensorID, String measurementName, JSONObject
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
		//measurement.addMeasurement(measurementPair);
		measurement.getMeasurementPairs().add(measurementPair);

		try {
			measurementRepository.update(measurement);
			return true;
		}
		catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	private boolean createSensorProduct(String producer, String type, Map<String, String>
			semantic) {
		SensorProduct sensorProduct = new SensorProduct(producer, type, semantic);
		sensorProductRepository.add(sensorProduct);
		System.out.println("Creating sensor product : " + sensorProduct.getId());
		return sensorProductRepository.contains(sensorProduct.getId());
	}

	public List<String> addAcceptedEntities(List<String> entities) {
		Settings settings = couchDB.get(Settings.class, SETTINGS_DOCUMENT_ID);
		settings.addAcceptedMeasurements(entities);
		couchDB.update(settings);
		return compareValues(entities, couchDB.get(Settings.class, SETTINGS_DOCUMENT_ID)
				.getAcceptedMeasurements());
	}

	public List<String> removeAcceptedEntities(List<String> entities) {
		Settings settings = couchDB.get(Settings.class, SETTINGS_DOCUMENT_ID);
		settings.removeAcceptedMeasurements(entities);
		couchDB.update(settings);
		return compareValues(entities, couchDB.get(Settings.class, SETTINGS_DOCUMENT_ID)
				.getAcceptedMeasurements());
	}

	public List<String> addIgnoredEntities(List<String> entities) {
		Settings settings = couchDB.get(Settings.class, SETTINGS_DOCUMENT_ID);
		settings.addIgnoredMeasurements(entities);
		couchDB.update(settings);
		return compareValues(entities, couchDB.get(Settings.class, SETTINGS_DOCUMENT_ID)
				.getIgnoredMeasurements());
	}

	public List<String> removeIgnoredEntities(List<String> entities) {
		Settings settings = couchDB.get(Settings.class, SETTINGS_DOCUMENT_ID);
		settings.removeIgnoredMeasurements(entities);
		couchDB.update(settings);
		return compareValues(entities, couchDB.get(Settings.class, SETTINGS_DOCUMENT_ID)
				.getIgnoredMeasurements());
	}

	public List<String> addTopic(List<String> topics) {
		List<String> addedTopics = new ArrayList<>();
		Settings     settings    = couchDB.get(Settings.class, SETTINGS_DOCUMENT_ID);
		settings.addTopics(topics);
		couchDB.update(settings);
		return compareValues(topics, couchDB.get(Settings.class, SETTINGS_DOCUMENT_ID)
				.getTopics());

	}

	public List<String> removeTopic(List<String> topics) {
		Settings settings = couchDB.get(Settings.class, SETTINGS_DOCUMENT_ID);
		settings.removeTopics(topics);
		couchDB.update(settings);
		return compareValues(topics, couchDB.get(Settings.class, SETTINGS_DOCUMENT_ID)
				.getTopics());
	}

	public Set<String> getTopics() {
		return couchDB.get(Settings.class, SETTINGS_DOCUMENT_ID).getTopics();
	}

	public Set<String> addTopic(String topic) {
		Settings settings = couchDB.get(Settings.class, SETTINGS_DOCUMENT_ID);
		settings.getTopics().add(topic);
		couchDB.update(settings);
		return couchDB.get(Settings.class, SETTINGS_DOCUMENT_ID).getTopics();
	}

	public Set<String> getRooms() {
		return couchDB.get(Settings.class, SETTINGS_DOCUMENT_ID).getRooms();
	}

	public Set<String> addRoom(String room) {
		Settings settings = couchDB.get(Settings.class, SETTINGS_DOCUMENT_ID);
		settings.getRooms().add(room);
		couchDB.update(settings);
		return couchDB.get(Settings.class, SETTINGS_DOCUMENT_ID).getRooms();
	}

	public Set<String> getTypes() {
		return couchDB.get(Settings.class, SETTINGS_DOCUMENT_ID).getTypes();
	}

	public Set<String> addType(String type) {
		Settings settings = couchDB.get(Settings.class, SETTINGS_DOCUMENT_ID);
		settings.getTypes().add(type);
		couchDB.update(settings);
		return couchDB.get(Settings.class, SETTINGS_DOCUMENT_ID).getTypes();
	}


	public Set<String> deleteRoom(String room) {
		Settings settings = couchDB.get(Settings.class, SETTINGS_DOCUMENT_ID);
		settings.getRooms().remove(room);
		couchDB.update(settings);
		return couchDB.get(Settings.class, SETTINGS_DOCUMENT_ID).getRooms();
	}

	public Set<String> deleteType(String type) {
		Settings settings = couchDB.get(Settings.class, SETTINGS_DOCUMENT_ID);
		settings.getTypes().remove(type);
		couchDB.update(settings);
		return couchDB.get(Settings.class, SETTINGS_DOCUMENT_ID).getTypes();
	}

	public Set<String> getAcceptedEntities() {
		return couchDB.get(Settings.class, SETTINGS_DOCUMENT_ID).getAcceptedMeasurements();
	}

	public Set<String> getIgnoredEntities() {
		return couchDB.get(Settings.class, SETTINGS_DOCUMENT_ID).getIgnoredMeasurements();
	}

	public List<SensorProduct> getAllSensorProducts() {
		return sensorProductRepository.getAll();
	}

	public List<SensorProduct> addSensorProduct(SensorProduct sensorProduct) {
		sensorProductRepository.add(sensorProduct);
		return sensorProductRepository.getAll();
	}

	public List<SensorProduct> removeSensorProduct(String sensorProductID) {
		SensorProduct sp = sensorProductRepository.get(sensorProductID);
		sensorProductRepository.remove(sp);
		return sensorProductRepository.getAll();
	}

	public TemporaryData getTemporaryData() {
		TemporaryData td = new TemporaryData();
		try {
			td = couchDB.get(TemporaryData.class, TEMP_DATA_DOCUMENT_ID);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return td;
	}

	private List<String> compareValues(List<String> values, Set<String> updatedValues) {
		List<String> addedValues = new ArrayList<>();
		for (String value : values) {
			if (updatedValues.contains(value)) {
				addedValues.add(value);
			}
		}
		return addedValues;
	}

	public void addIgnoredMeasurement(String measurement) {

		Settings settings = couchDB.get(Settings.class, SETTINGS_DOCUMENT_ID);
		settings.getIgnoredMeasurements().add(measurement);
		couchDB.update(settings);

		return;
	}

	public void deleteTemporaryData() {
		try {
			TemporaryData td = couchDB.get(TemporaryData.class, TEMP_DATA_DOCUMENT_ID);
			couchDB.delete(td);
			TemporaryData tdNew = new TemporaryData();
			couchDB.create(tdNew);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
