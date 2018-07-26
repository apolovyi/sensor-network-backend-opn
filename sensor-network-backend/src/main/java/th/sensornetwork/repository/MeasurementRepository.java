package th.sensornetwork.repository.couchdb.repository;

import org.ektorp.CouchDbConnector;
import org.ektorp.support.CouchDbRepositorySupport;
import org.ektorp.support.GenerateView;
import org.ektorp.support.View;
import org.springframework.stereotype.Repository;
import th.sensornetwork.model.Measurement;

import java.util.List;

@Repository
@View(name = "all", map = "function(doc) { if (doc.documentType == 'Measurement') emit( null, doc._id )}")
public class MeasurementRepository extends CouchDbRepositorySupport<Measurement> {

	public MeasurementRepository(CouchDbConnector db) {
		super(Measurement.class, db);
		initStandardDesignDocument();
	}

	@GenerateView
	public List<Measurement> findByMeasurementName(String measurementName) {
		return queryView("by_measurementName", measurementName);
	}

	@GenerateView
	public List<Measurement> findBySensorID(String sensorID) {
		return queryView("by_sensorID", sensorID);
	}
}
