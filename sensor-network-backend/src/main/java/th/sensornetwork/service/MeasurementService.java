package th.sensornetwork.service;

import th.sensornetwork.model.couchdb.Measurement;

import java.util.List;

public interface MeasurementService {

	List<Measurement> getMeasurementsBySensor(String id);

	List<Measurement> getAllEntities();

	Measurement getMeasurementByID(String measurementID);

	//String getEntityUnit(String id, String entity);

}
