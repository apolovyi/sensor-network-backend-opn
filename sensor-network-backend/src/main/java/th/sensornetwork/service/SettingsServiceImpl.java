package th.sensornetwork.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import th.sensornetwork.model.SensorProduct;
import th.sensornetwork.model.Settings;
import th.sensornetwork.model.TemporaryData;
import th.sensornetwork.repository.SensorPersistence;
import th.sensornetwork.repository.SensorProductRepository;

import java.util.List;
import java.util.Set;

@Service
public class SettingsServiceImpl implements SettingsService {

	private SensorPersistence sensorPersistence;
	private SensorProductRepository sensorProductRepository;

	private final String SETTINGS_DOCUMENT_ID = "Settings";

	@Autowired
	public SettingsServiceImpl(SensorPersistence sensorPersistence, SensorProductRepository
			sensorProductRepository) {
		this.sensorPersistence = sensorPersistence;
		this.sensorProductRepository = sensorProductRepository;
	}

	@Override
	public Set<String> getRooms() {
		return sensorPersistence.getCouchDB()
				.get(Settings.class, SETTINGS_DOCUMENT_ID)
				.getRooms();
	}

	@Override
	public Set<String> addRoom(String room) {
		Settings settings = sensorPersistence.getCouchDB()
				.get(Settings.class, SETTINGS_DOCUMENT_ID);
		settings.getRooms().add(room);
		sensorPersistence.getCouchDB().update(settings);
		return sensorPersistence.getCouchDB()
				.get(Settings.class, SETTINGS_DOCUMENT_ID)
				.getRooms();
	}

	@Override
	public Set<String> getTypes() {
		return sensorPersistence.getCouchDB()
				.get(Settings.class, SETTINGS_DOCUMENT_ID)
				.getTypes();
	}

	@Override
	public Set<String> addType(String type) {
		Settings settings = sensorPersistence.getCouchDB()
				.get(Settings.class, SETTINGS_DOCUMENT_ID);
		settings.getTypes().add(type);
		sensorPersistence.getCouchDB().update(settings);
		return sensorPersistence.getCouchDB()
				.get(Settings.class, SETTINGS_DOCUMENT_ID)
				.getTypes();
	}


	@Override
	public Set<String> deleteRoom(String room) {
		Settings settings = sensorPersistence.getCouchDB()
				.get(Settings.class, SETTINGS_DOCUMENT_ID);
		settings.getRooms().remove(room);
		sensorPersistence.getCouchDB().update(settings);
		return sensorPersistence.getCouchDB()
				.get(Settings.class, SETTINGS_DOCUMENT_ID)
				.getRooms();
	}

	@Override
	public Set<String> deleteType(String type) {
		Settings settings = sensorPersistence.getCouchDB()
				.get(Settings.class, SETTINGS_DOCUMENT_ID);
		settings.getTypes().remove(type);
		sensorPersistence.getCouchDB().update(settings);
		return sensorPersistence.getCouchDB()
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
		Settings settings = sensorPersistence.getCouchDB()
				.get(Settings.class, SETTINGS_DOCUMENT_ID);
		settings.getIgnoredMeasurements().add(measurement);
		sensorPersistence.getCouchDB().update(settings);
		return sensorPersistence.getCouchDB()
				.get(Settings.class, SETTINGS_DOCUMENT_ID)
				.getIgnoredMeasurements()
				.contains(measurement);
	}

	@Override
	public TemporaryData getTemporaryData() {
		return sensorPersistence.getTemporaryData();
	}


}
