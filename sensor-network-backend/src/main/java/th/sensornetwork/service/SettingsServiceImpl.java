package th.sensornetwork.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import th.sensornetwork.model.SensorProduct;
import th.sensornetwork.model.Settings;
import th.sensornetwork.repository.SensorProductRepository;
import th.sensornetwork.repository.SettingsRepository;

import java.util.List;
import java.util.Set;

@Service
public class SettingsServiceImpl implements SettingsService {

	private SensorProductRepository sensorProductRepository;
	private SettingsRepository settingsRepository;

	private final String SETTINGS_DOCUMENT_ID = "Settings";

	@Autowired
	public SettingsServiceImpl(SensorProductRepository sensorProductRepository, SettingsRepository settingsRepository) {
		this.sensorProductRepository = sensorProductRepository;
		this.settingsRepository = settingsRepository;
	}

	@Override
	public Set<String> getRooms() {
		return settingsRepository.get(SETTINGS_DOCUMENT_ID).getRooms();
	}

	@Override
	public Set<String> addRoom(String room) {
		Settings settings = settingsRepository.get(SETTINGS_DOCUMENT_ID);
		settings.getRooms().add(room);
		settingsRepository.update(settings);
		return settingsRepository.get(SETTINGS_DOCUMENT_ID).getRooms();
	}

	@Override
	public Set<String> getTypes() {
		return settingsRepository.get(SETTINGS_DOCUMENT_ID).getTypes();
	}

	@Override
	public Set<String> addType(String type) {
		Settings settings = settingsRepository.get(SETTINGS_DOCUMENT_ID);
		settings.getTypes().add(type);
		settingsRepository.update(settings);
		return settingsRepository.get(SETTINGS_DOCUMENT_ID).getTypes();
	}


	@Override
	public Set<String> deleteRoom(String room) {
		Settings settings = settingsRepository.get(SETTINGS_DOCUMENT_ID);
		settings.getRooms().remove(room);
		settingsRepository.update(settings);
		return settingsRepository.get(SETTINGS_DOCUMENT_ID).getRooms();
	}

	@Override
	public Set<String> deleteType(String type) {
		Settings settings = settingsRepository.get(SETTINGS_DOCUMENT_ID);
		settings.getTypes().remove(type);
		settingsRepository.update(settings);
		return settingsRepository.get(SETTINGS_DOCUMENT_ID).getTypes();
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
		Settings settings = settingsRepository.get(SETTINGS_DOCUMENT_ID);
		settings.getIgnoredMeasurements().add(measurement);
		settingsRepository.update(settings);
		return settingsRepository.get(SETTINGS_DOCUMENT_ID).getIgnoredMeasurements().contains(measurement);
	}

}
