package th.sensornetwork.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import th.sensornetwork.service.DataService;

import java.util.Map;

@RestController
public class DataController {


	private DataService dataService;

    @Autowired
    public DataController(DataService dataService) {
        this.dataService = dataService;
    }

    public Map<Long, Double> getData(String id, String entity) {
		return null;
	}
}
