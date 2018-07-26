package th.sensornetwork.repository.couchdb.repository;

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

	@GenerateView
	public List<Sensor> findBySensorName(String sensorName) {
		return queryView("by_sensorName", sensorName);
	}

	@GenerateView
	public List<Sensor> findBySensorType(String sensorType) {
		return queryView("by_sensorType", sensorType);
	}

	@GenerateView
	public List<Sensor> findByRoom(String room) {
		return queryView("by_room", room);
	}
}
