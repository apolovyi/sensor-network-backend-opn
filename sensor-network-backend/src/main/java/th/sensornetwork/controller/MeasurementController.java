package th.sensornetwork.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import th.sensornetwork.model.couchdb.Measurement;
import th.sensornetwork.service.MeasurementService;

import java.util.List;

@RestController
@RequestMapping(value = "/measurements", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@CrossOrigin(origins = "*")
public class MeasurementController {

	private MeasurementService measurementService;

	@Autowired
	public MeasurementController(MeasurementService measurementService) {
		this.measurementService = measurementService;
	}

	@GetMapping("/{id}")
	public Measurement getMeasurementByID(@PathVariable String id, @RequestParam(value = "timePeriod", required = false) String timePeriod) {
		if (!timePeriod.equals("all"))
			return measurementService.getMeasurementByIDForTimePeriod(id, timePeriod);
		return measurementService.getMeasurementByID(id);
	}
}
