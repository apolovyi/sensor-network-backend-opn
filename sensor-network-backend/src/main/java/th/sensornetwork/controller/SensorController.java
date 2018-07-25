package th.sensornetwork.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import th.sensornetwork.model.couchdb.Sensor;
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
	public List<Sensor> getAllSensors(@RequestParam(value = "location", required = false) String location, @RequestParam(value = "sensorType", required = false) String type ) {
		if(location!=null)
			return sensorService.getSensorsByType(location);
		else if(type!= null)
			sensorService.getSensorsByType(type);
		return sensorService.getAllSensors();
	}

	@PatchMapping
	public Sensor updateSensor(@RequestBody Sensor sensor){
		return sensorService.updateSensor(sensor);
	}

	@GetMapping("/{id}")
	public Sensor getSensorById(@PathVariable String id) {
		return sensorService.getSensorById(id);
	}

	@DeleteMapping("/{id}")
	public boolean deleteSensor(@PathVariable String id){
		return sensorService.deleteSensor(id);
	}
}
