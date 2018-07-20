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

	@GetMapping("")
	public List<Sensor> getAllSensors(@RequestParam(value = "location", required = false) String location, @RequestParam(value = "sensorType", required = false) String type ) {
		if(location!=null)
			return sensorService.getSensorsByType(location);
		else if(type!= null)
			sensorService.getSensorsByType(type);
		return sensorService.getAllSensors();
	}

	/*@GetMapping("/1")
	public String viewAll(Model m) {
		m.addAttribute(sensorService.getAllSensors());
		return "/sensors1/index";
	}*/

	@GetMapping("/{id}")
	public Sensor getSensorById(@PathVariable String id) {
		return sensorService.getSensorById(id);
	}

}
