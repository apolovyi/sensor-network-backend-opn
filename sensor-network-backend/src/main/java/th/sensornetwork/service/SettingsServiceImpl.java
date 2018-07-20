package th.sensornetwork.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import th.sensornetwork.model.couchdb.SensorProduct;
import th.sensornetwork.model.couchdb.TemporaryData;
import th.sensornetwork.repository.couchdb.PersistenceCouchDB;

import java.util.List;
import java.util.Set;

@Service
public class SettingsServiceImpl implements SettingsService {

	private PersistenceCouchDB persistenceCouchDB;

	@Autowired
	public SettingsServiceImpl(PersistenceCouchDB persistenceCouchDB) {
		this.persistenceCouchDB = persistenceCouchDB;
	}

	@Override
	public List<String> addAcceptedEntities(List<String> entities) {
		return persistenceCouchDB.addAcceptedEntities(entities);
	}

	@Override
	public List<String> removeAcceptedEntities(List<String> entities) {
		return persistenceCouchDB.removeAcceptedEntities(entities);
	}

	@Override
	public List<String> addIgnoredEntities(List<String> entities) {
		return persistenceCouchDB.addIgnoredEntities(entities);
	}

	@Override
	public List<String> removeIgnoredEntities(List<String> entities) {
		return persistenceCouchDB.removeIgnoredEntities(entities);
	}

	@Override
	public List<String> addTopics(List<String> topics) {
		return persistenceCouchDB.addTopic(topics);

	}

	@Override
	public List<String> removeTopic(List<String> topics) {
		return persistenceCouchDB.removeTopic(topics);
	}

	@Override
	public boolean addAdmin(String email, String password) {
		return persistenceCouchDB.addAdmin(email, password);
	}

	@Override
	public boolean removeAdmin(String email, String password) {
		return persistenceCouchDB.removeAdmin(email, password);
	}

	@Override
	public Set<String> getTopics() {
		return persistenceCouchDB.getTopics();
	}

	@Override
	public Set<String> addRoom(String room) {
		return persistenceCouchDB.addRoom(room);
	}

	@Override
	public Set<String> addType(String type) {
		return persistenceCouchDB.addType(type);
	}

	@Override
	public Set<String> addTopic(String topic) {
		return persistenceCouchDB.addTopic(topic);
	}

	@Override
	public Set<String> getRooms() {
		return persistenceCouchDB.getRooms();
	}

	@Override
	public Set<String> getTypes() {
		return persistenceCouchDB.getTypes();
	}

	@Override
	public Set<String> deleteRoom(String room) {
		return persistenceCouchDB.deleteRoom(room);
	}

	@Override
	public Set<String> deleteType(String type) {
		return persistenceCouchDB.deleteType(type);
	}

	@Override
	public Set<String> getAcceptedEntities() {
		return persistenceCouchDB.getAcceptedEntities();
	}

	@Override
	public Set<String> getIgnoredEntities() {
		return persistenceCouchDB.getIgnoredEntities();
	}

	@Override
	public List<SensorProduct> getAllSensorProducts() {
		return persistenceCouchDB.getAllSensorProducts();
	}

	@Override
	public List<SensorProduct> addSensorProduct(SensorProduct sensorProduct) {
		return persistenceCouchDB.addSensorProduct(sensorProduct);
	}

	@Override
	public List<SensorProduct> removeSensorProduct(String sensorProductID) {
		return persistenceCouchDB.removeSensorProduct(sensorProductID);
	}

	@Override
	public TemporaryData getTemporaryData() {
		return persistenceCouchDB.getTemporaryData();
	}

	@Override
	public void deleteTemporaryData() {
		persistenceCouchDB.deleteTemporaryData();
	}

	@Override
	public void addIgnoredMeasurement(String measurement) {
		persistenceCouchDB.addIgnoredMeasurement(measurement);
	}


}
