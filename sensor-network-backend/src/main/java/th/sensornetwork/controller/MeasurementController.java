package th.sensornetwork.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import th.sensornetwork.model.Measurement;
import th.sensornetwork.service.MeasurementService;

@RestController
@RequestMapping(value = "/measurements", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@CrossOrigin(origins = "*")
public class MeasurementController {

	private MeasurementService measurementService;

	@Autowired
	public MeasurementController(MeasurementService measurementService) {
		this.measurementService = measurementService;
	}

	/**
	 *
	 * @param id
	 * @param timePeriod
	 */
	@GetMapping("/{id}")
	public Measurement getMeasurementByID(@PathVariable String id, @RequestParam(value = "timePeriod", required = false) String timePeriod) {
		if (!timePeriod.equals("all"))
			return measurementService.getMeasurementByIDForTimePeriod(id, timePeriod);
		return measurementService.getMeasurementByID(id);
	}
}
