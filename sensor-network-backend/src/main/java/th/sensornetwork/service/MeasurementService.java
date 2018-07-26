package th.sensornetwork.service;

import th.sensornetwork.model.couchdb.Measurement;

import java.util.List;

public interface MeasurementService {

	Measurement getMeasurementByID(String measurementID);

	Measurement getMeasurementByIDForTimePeriod(String measurementID, String timePeriod);

}
