package th.sensornetwork.service;

import th.sensornetwork.model.couchdb.Measurement;

import java.util.List;

public interface MeasurementService {

	List<Measurement> getMeasurementsBySensor(String id);

	List<Measurement> getAllMeasurements();

	Measurement getMeasurementByID(String measurementID);

	Measurement getMeasurementByIDForTimePeriod(String measurementID, String timePeriod);

	//String getEntityUnit(String id, String entity);

}
