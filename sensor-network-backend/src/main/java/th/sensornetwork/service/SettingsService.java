package th.sensornetwork.service;

import th.sensornetwork.model.SensorProduct;
import th.sensornetwork.model.TemporaryData;

import java.util.List;
import java.util.Set;

public interface SettingsService {

	Set<String> addRoom(String room);

	Set<String> addType(String type);

	Set<String> getRooms();

	Set<String> getTypes();

	Set<String> deleteRoom(String room);

	Set<String> deleteType(String type);

	List<SensorProduct> getAllSensorProducts();

	List<SensorProduct> addSensorProduct(SensorProduct sensorProduct);

	List<SensorProduct> removeSensorProduct(String sensorProductID);

	boolean addIgnoredMeasurement(String measurement);
}
