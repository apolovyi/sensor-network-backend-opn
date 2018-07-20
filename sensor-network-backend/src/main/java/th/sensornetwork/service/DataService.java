package th.sensornetwork.service;

import java.util.Map;

public interface DataService {

	Map<Long, Double> getData(String id, String entity);

}
