package th.sensornetwork.service;

import th.sensornetwork.model.Measurement;

public interface MeasurementService {

	Measurement getMeasurementByID(String measurementID);

	Measurement getMeasurementByIDForTimePeriod(String measurementID, String timePeriod);

}
