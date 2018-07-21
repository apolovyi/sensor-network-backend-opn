package th.sensornetwork.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import th.sensornetwork.client.MqttClientTH;
import th.sensornetwork.model.couchdb.SensorProduct;
import th.sensornetwork.model.couchdb.Settings;
import th.sensornetwork.model.couchdb.TemporaryData;
import th.sensornetwork.service.SettingsService;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping(value = "/settings", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@CrossOrigin(origins = "*")
public class SettingsController {

	private final SettingsService settingsService;
	private final MqttClientTH mqttClientTH;

	@Autowired
	public SettingsController(SettingsService settingsService, MqttClientTH mqttClientTH) {
		this.settingsService = settingsService;
		this.mqttClientTH = mqttClientTH;
	}

	@PostMapping("/topics")
	public List<String> setTopics(@RequestBody List<String> topics) {
		return settingsService.addTopics(topics);
	}

	@DeleteMapping("/topics")
	public List<String> deleteTopics(@RequestBody List<String> topics) {
		return settingsService.removeTopic(topics);
	}

	@GetMapping("/topics")
	public Set<String> getTopics() {
		return settingsService.getTopics();
	}

	@GetMapping("/acceptedEntities")
	public Set<String> getAcceptedEntities() {
		return settingsService.getAcceptedEntities();
	}

	@PostMapping("/acceptedEntities")
	public List<String> addAcceptedEntities(@RequestBody List<String> acceptedEntities) {
		return settingsService.addAcceptedEntities(acceptedEntities);
	}

	@DeleteMapping("/acceptedEntities")
	public List<String> deleteAcceptedEntities(@RequestBody List<String> acceptedEntities) {
		return settingsService.removeAcceptedEntities(acceptedEntities);
	}

	@GetMapping("/ignoredEntities")
	public Set<String> getIgnoredEntities() {
		return settingsService.getIgnoredEntities();
	}

	@PostMapping("/ignoredEntities")
	public List<String> addIgnoredEntities(@RequestBody List<String> ignoredEntities) {
		return settingsService.addIgnoredEntities(ignoredEntities);
	}

	@PostMapping("/ignoredMeasurements")
	public void addIgnoredMeasurements(@RequestBody String measurement) {
		settingsService.addIgnoredMeasurement(measurement);
	}

	@DeleteMapping("/ignoredEntities")
	public List<String> deleteIgnoredEntities(@RequestBody List<String> ignoredEntities) {
		return settingsService.removeIgnoredEntities(ignoredEntities);
	}

	@GetMapping("/rooms")
	public Set<String> getAllRooms() {
		return settingsService.getRooms();
	}

	@PostMapping("/rooms")
	public Set<String> addRoom(@RequestBody String room) {
		return settingsService.addRoom(room);
	}

	@PatchMapping("/rooms")
	public Set<String> deleteRoom(@RequestBody String room) {
		return settingsService.deleteRoom(room);
	}

	@GetMapping("/types")
	public Set<String> getAllTypes() {
		return settingsService.getTypes();
	}

	@PostMapping("/types")
	public Set<String> addType(@RequestBody String type) {
		return settingsService.addType(type);
	}

	@PatchMapping("/types")
	public Set<String> deleteType(@RequestBody String type) {
		return settingsService.deleteType(type);
	}

	@PostMapping("/sensorProducts")
	public List<SensorProduct> addSensorProduct(@RequestBody SensorProduct sensorProduct) {
		sensorProduct.setId(sensorProduct.getProducer() + ":" + sensorProduct.getType());
		return settingsService.addSensorProduct(sensorProduct);
	}

	@PatchMapping("/sensorProducts")
	public List<SensorProduct> deleteSensorProduct(@RequestBody String sensorProduct) {
		return settingsService.removeSensorProduct(sensorProduct);
	}

	@GetMapping("/sensorProducts")
	public List<SensorProduct> getAllSensorProducts() {
		return settingsService.getAllSensorProducts();
	}

	@GetMapping("/sensors")
	public TemporaryData getTemporaryData() {
		/*settingsService.deleteTemporaryData();
		myMqttClient.refresh();
		try {
			Thread.sleep(2000);
		}
		catch (InterruptedException e) {
			e.printStackTrace();
		}*/
		return settingsService.getTemporaryData();
	}

	@PostMapping("/start")
	public Settings startMqttClient(@RequestBody Settings settings) {
		return mqttClientTH.updateMqttClient(settings);

	}

	@GetMapping("/all")
	public Settings getSettings() {
		return mqttClientTH.getCurrentSettings();
	}

	@PostMapping("/stop")
	public void stopMqttClient() {
		mqttClientTH.disconnectFromMqttBroker();
	}

	/*@PostMapping("/addAll")
	public TemporaryData addSensorFromTemporaryData(String name, String room, String spID,
	TemporaryData
			temporaryData) {
		return myMqttClient.addSensorFromTemporaryData(name, room, spID, temporaryData);
	}*/

	@PostMapping("/sensors")
	public boolean addSensor(@RequestBody NewSensorWrapper sw) {
		return mqttClientTH.addSensorFromTemporaryData(sw.name, sw.room, sw.spID, sw
				.temporarySensor);
	}


}
