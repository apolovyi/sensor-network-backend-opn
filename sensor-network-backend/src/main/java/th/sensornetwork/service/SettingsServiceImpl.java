package th.sensornetwork.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import th.sensornetwork.model.couchdb.SensorProduct;
import th.sensornetwork.model.couchdb.Settings;
import th.sensornetwork.model.couchdb.TemporaryData;
import th.sensornetwork.repository.couchdb.PersistenceCouchDB;
import th.sensornetwork.repository.couchdb.repository.SensorProductRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class SettingsServiceImpl implements SettingsService {

	private PersistenceCouchDB      persistenceCouchDB;
	private SensorProductRepository sensorProductRepository;

	private final String SETTINGS_DOCUMENT_ID = "Settings";

	@Autowired
	public SettingsServiceImpl(PersistenceCouchDB persistenceCouchDB, SensorProductRepository
			sensorProductRepository) {
		this.persistenceCouchDB = persistenceCouchDB;
		this.sensorProductRepository = sensorProductRepository;
	}

	@Override
	public Set<String> getRooms() {
		return persistenceCouchDB.getCouchDB()
				.get(Settings.class, SETTINGS_DOCUMENT_ID)
				.getRooms();
	}

	@Override
	public Set<String> addRoom(String room) {
		Settings settings = persistenceCouchDB.getCouchDB()
				.get(Settings.class, SETTINGS_DOCUMENT_ID);
		settings.getRooms().add(room);
		persistenceCouchDB.getCouchDB().update(settings);
		return persistenceCouchDB.getCouchDB()
				.get(Settings.class, SETTINGS_DOCUMENT_ID)
				.getRooms();
	}

	@Override
	public Set<String> getTypes() {
		return persistenceCouchDB.getCouchDB()
				.get(Settings.class, SETTINGS_DOCUMENT_ID)
				.getTypes();
	}

	@Override
	public Set<String> addType(String type) {
		Settings settings = persistenceCouchDB.getCouchDB()
				.get(Settings.class, SETTINGS_DOCUMENT_ID);
		settings.getTypes().add(type);
		persistenceCouchDB.getCouchDB().update(settings);
		return persistenceCouchDB.getCouchDB()
				.get(Settings.class, SETTINGS_DOCUMENT_ID)
				.getTypes();
	}


	@Override
	public Set<String> deleteRoom(String room) {
		Settings settings = persistenceCouchDB.getCouchDB()
				.get(Settings.class, SETTINGS_DOCUMENT_ID);
		settings.getRooms().remove(room);
		persistenceCouchDB.getCouchDB().update(settings);
		return persistenceCouchDB.getCouchDB()
				.get(Settings.class, SETTINGS_DOCUMENT_ID)
				.getRooms();
	}

	@Override
	public Set<String> deleteType(String type) {
		Settings settings = persistenceCouchDB.getCouchDB()
				.get(Settings.class, SETTINGS_DOCUMENT_ID);
		settings.getTypes().remove(type);
		persistenceCouchDB.getCouchDB().update(settings);
		return persistenceCouchDB.getCouchDB()
				.get(Settings.class, SETTINGS_DOCUMENT_ID)
				.getTypes();
	}

	@Override
	public List<SensorProduct> getAllSensorProducts() {
		return sensorProductRepository.getAll();
	}

	@Override
	public List<SensorProduct> addSensorProduct(SensorProduct sensorProduct) {
		sensorProductRepository.add(sensorProduct);
		return sensorProductRepository.getAll();
	}

	@Override
	public List<SensorProduct> removeSensorProduct(String sensorProductID) {
		SensorProduct sp = sensorProductRepository.get(sensorProductID);
		sensorProductRepository.remove(sp);
		return sensorProductRepository.getAll();
	}

	@Override
	public boolean addIgnoredMeasurement(String measurement) {
		Settings settings = persistenceCouchDB.getCouchDB()
				.get(Settings.class, SETTINGS_DOCUMENT_ID);
		settings.getIgnoredMeasurements().add(measurement);
		persistenceCouchDB.getCouchDB().update(settings);
		return persistenceCouchDB.getCouchDB()
				.get(Settings.class, SETTINGS_DOCUMENT_ID)
				.getIgnoredMeasurements()
				.contains(measurement);
	}

	@Override
	public TemporaryData getTemporaryData() {
		return persistenceCouchDB.getTemporaryData();
	}


}
