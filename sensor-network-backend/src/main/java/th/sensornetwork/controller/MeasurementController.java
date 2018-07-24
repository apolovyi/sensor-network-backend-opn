package th.sensornetwork.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import th.sensornetwork.model.couchdb.Measurement;
import th.sensornetwork.service.MeasurementService;

import java.util.List;

@RestController
@RequestMapping("/measurements")
@CrossOrigin(origins = "*")
public class MeasurementController {

	private MeasurementService measurementService;

	@Autowired
	public MeasurementController(MeasurementService measurementService) {
		this.measurementService = measurementService;
	}

	@GetMapping("")
	public List<Measurement> getAllMeasurements(@RequestParam(value = "sensorID", required = false) String sensorID) {
		if (sensorID!=null)
			return measurementService.getMeasurementsBySensor(sensorID);
		return measurementService.getAllMeasurements();
	}

	@GetMapping("/{id}")
	public Measurement getMeasurementByID(@PathVariable String id, @RequestParam(value = "timePeriod", required = false) String timePeriod) {
		if (!timePeriod.equals("all"))
			return measurementService.getMeasurementByIDForTimePeriod(id, timePeriod);
		return measurementService.getMeasurementByID(id);
	}
}
