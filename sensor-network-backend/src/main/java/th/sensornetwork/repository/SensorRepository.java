package th.sensornetwork.repository;

import org.ektorp.CouchDbConnector;
import org.ektorp.support.CouchDbRepositorySupport;
import org.ektorp.support.GenerateView;
import org.ektorp.support.View;
import org.springframework.stereotype.Repository;
import th.sensornetwork.model.Sensor;

import java.util.List;

@Repository
@View(name = "all", map = "function(doc) { if (doc.documentType == 'Sensor') emit( null, doc._id )}")
public class SensorRepository extends CouchDbRepositorySupport<Sensor> {

	public SensorRepository(CouchDbConnector db) {
		super(Sensor.class, db);
		initStandardDesignDocument();
	}
}
