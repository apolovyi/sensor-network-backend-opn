package th.sensornetwork.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import th.sensornetwork.client.MqttClientTH;
import th.sensornetwork.model.SensorProduct;
import th.sensornetwork.model.Settings;
import th.sensornetwork.model.TemporaryData;
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

	@PostMapping("/ignoredMeasurements")
	public boolean addIgnoredMeasurements(@RequestBody String measurement) {
		return settingsService.addIgnoredMeasurement(measurement);
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
		return settingsService.addSensorProduct(new SensorProduct(sensorProduct.getProducer(), sensorProduct.getType(), sensorProduct.getSemantic()));
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
		return settingsService.getTemporaryData();
	}

	@PostMapping("/sensors")
	public boolean addSensor(@RequestBody NewSensorWrapper sw) {
		return mqttClientTH.addSensorFromTemporaryData(sw.name, sw.room, sw.spID, sw
				.temporarySensor);
	}

	@PostMapping
	public Settings updateSettings(@RequestBody Settings settings) {
		return mqttClientTH.updateMqttClient(settings);

	}

	@DeleteMapping
	public Settings deleteSettings() {
		return mqttClientTH.deleteSettings();

	}

	@GetMapping
	public Settings getSettings() {
		return mqttClientTH.getCurrentSettings();
	}

}
