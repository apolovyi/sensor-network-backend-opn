package th.sensornetwork.repository.couchdb.repository;

import org.ektorp.CouchDbConnector;
import org.ektorp.support.CouchDbRepositorySupport;
import org.ektorp.support.GenerateView;
import org.ektorp.support.View;
import org.springframework.stereotype.Repository;
import th.sensornetwork.model.couchdb.SensorProduct;

import java.util.List;

@Repository
@View(name = "all", map = "function(doc) { if (doc.documentType === 'SensorProduct') emit( null, doc._id )}")
public class SensorProductRepository extends CouchDbRepositorySupport<SensorProduct> {

	public SensorProductRepository(CouchDbConnector db) {
		super(SensorProduct.class, db);
		initStandardDesignDocument();
	}

	@GenerateView
	public List<SensorProduct> findByProducer(String producer) {
		return queryView("by_producer", producer);
	}

}
