package th.sensornetwork.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import th.sensornetwork.model.couchdb.Measurement;
import th.sensornetwork.model.couchdb.MeasurementPair;
import th.sensornetwork.repository.couchdb.repository.MeasurementRepository;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class MeasurementServiceImpl implements MeasurementService {

	private MeasurementRepository measurementRepository;

	@Autowired
	public MeasurementServiceImpl(MeasurementRepository measurementRepository) {
		this.measurementRepository = measurementRepository;
	}

	@Override
	public Measurement getMeasurementByID(String measurementID) {
		return measurementRepository.get(measurementID);
	}

	@Override
	public Measurement getMeasurementByIDForTimePeriod(String measurementID, String
			timePeriod) {
		Measurement ms = measurementRepository.get(measurementID);

		LocalDateTime ldt    = LocalDateTime.now();
		ZoneId        zoneId = ZoneId.systemDefault();
		switch (timePeriod) {
			case "day":
				ldt = ldt.minusDays(1);
				break;
			case "week":
				ldt = ldt.minusDays(7);
				break;
			case "month":
				ldt = ldt.minusDays(30);
				break;
			default:
				break;
		}
		long                 epochMS          = ldt.atZone(zoneId).toEpochSecond() * 1000;
		Set<MeasurementPair> measurementPairs = ms.getMeasurementPairs()
				.stream()
				.filter(m -> m.getTs() > epochMS)
				.collect(Collectors.toSet());
		ms.setMeasurementPairs(measurementPairs);

		return measurementRepository.get(measurementID);
	}
}
