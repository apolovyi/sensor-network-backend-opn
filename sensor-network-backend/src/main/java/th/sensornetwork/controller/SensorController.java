package th.sensornetwork.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import th.sensornetwork.model.Sensor;
import th.sensornetwork.service.SensorService;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "/sensors",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class SensorController {

	private SensorService sensorService;

	@Autowired
	public SensorController(SensorService sensorService) {
		this.sensorService = sensorService;
	}

	@GetMapping
	public List<Sensor> getAllSensors() {
		return sensorService.getAllSensors();
	}

	@PatchMapping
	public Sensor updateSensor(@RequestBody Sensor sensor){
		return sensorService.updateSensor(sensor);
	}

	/**
	 *
	 * @param id
	 */
	@GetMapping("/{id}")
	public Sensor getSensorById(@PathVariable String id) {
		return sensorService.getSensorById(id);
	}

	/**
	 *
	 * @param id
	 */
	@DeleteMapping("/{id}")
	public boolean deleteSensor(@PathVariable String id){
		return sensorService.deleteSensor(id);
	}
}
