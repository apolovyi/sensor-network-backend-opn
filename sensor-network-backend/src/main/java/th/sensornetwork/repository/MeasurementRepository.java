package th.sensornetwork.repository;

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

}
