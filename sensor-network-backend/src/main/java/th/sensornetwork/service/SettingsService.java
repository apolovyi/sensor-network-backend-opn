package th.sensornetwork.service;

import th.sensornetwork.model.couchdb.SensorProduct;
import th.sensornetwork.model.couchdb.TemporaryData;

import java.util.List;
import java.util.Set;

public interface SettingsService {

	List<String> addAcceptedEntities(List<String> entities);

	List<String> removeAcceptedEntities(List<String> entities);

	List<String> addIgnoredEntities(List<String> entities);

	List<String> removeIgnoredEntities(List<String> entities);

	List<String> addTopics(List<String> topics);

	List<String> removeTopic(List<String> topics);

	boolean addAdmin(String email, String password);

	boolean removeAdmin(String email, String password);

	Set<String> getTopics();

	Set<String> addRoom(String room);

	Set<String> addType(String type);

	Set<String> addTopic(String topic);

	Set<String> getRooms();

	Set<String> getTypes();

	Set<String> deleteRoom(String room);

	Set<String> deleteType(String type);

	Set<String> getAcceptedEntities();

	Set<String> getIgnoredEntities();

	List<SensorProduct> getAllSensorProducts();

	List<SensorProduct> addSensorProduct(SensorProduct sensorProduct);

	List<SensorProduct> removeSensorProduct(String sensorProductID);

	TemporaryData getTemporaryData();

	void deleteTemporaryData();

	void addIgnoredMeasurement(String measurement);
}
