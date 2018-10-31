package th.sensornetwork.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import th.sensornetwork.client.MqttClientTH;
import th.sensornetwork.model.SensorProduct;
import th.sensornetwork.model.Settings;
import th.sensornetwork.model.TempData;
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

	@GetMapping("/sensors")
	public TempData getTemporaryData() {
		return mqttClientTH.getTemporaryData();
	}

	/**
	 *
	 * @param sw
	 */
	@PostMapping("/sensors")
	public boolean addSensor(@RequestBody NewSensorWrapper sw) {
		return mqttClientTH.addSensorFromCandidate(sw.name, sw.room, sw.spID, sw
				.sensorCandidate);
	}

	/**
	 *
	 * @param measurement
	 */
	@PostMapping("/ignoredMeasurements")
	public boolean addIgnoredMeasurements(@RequestBody String measurement) {
		return settingsService.addIgnoredMeasurement(measurement);
	}

	@GetMapping("/rooms")
	public Set<String> getAllRooms() {
		return settingsService.getRooms();
	}

	/**
	 *
	 * @param room
	 */
	@PostMapping("/rooms")
	public Set<String> addRoom(@RequestBody String room) {
		return settingsService.addRoom(room);
	}

	/**
	 *
	 * @param room
	 */
	@DeleteMapping("/rooms")
	public Set<String> deleteRoom(@RequestBody String room) {
		return settingsService.deleteRoom(room);
	}

	@GetMapping("/types")
	public Set<String> getAllTypes() {
		return settingsService.getTypes();
	}

	/**
	 *
	 * @param type
	 */
	@PostMapping("/types")
	public Set<String> addType(@RequestBody String type) {
		return settingsService.addType(type);
	}

	/**
	 *
	 * @param type
	 */
	@DeleteMapping("/types")
	public Set<String> deleteType(@RequestBody String type) {
		return settingsService.deleteType(type);
	}

	/**
	 *
	 * @param sensorProduct
	 */
	@PostMapping("/sensorProducts")
	public List<SensorProduct> addSensorProduct(@RequestBody SensorProduct sensorProduct) {
		return settingsService.addSensorProduct(new SensorProduct(sensorProduct.getProducer(), sensorProduct.getType(), sensorProduct.getSemantic()));
	}

	/**
	 *
	 * @param sensorProduct
	 */
	@PatchMapping("/sensorProducts")
	public List<SensorProduct> deleteSensorProduct(@RequestBody String sensorProduct) {
		return settingsService.removeSensorProduct(sensorProduct);
	}

	@GetMapping("/sensorProducts")
	public List<SensorProduct> getAllSensorProducts() {
		return settingsService.getAllSensorProducts();
	}

}
