package th.sensornetwork.repository.couchdb.repository;

import org.ektorp.CouchDbConnector;
import org.ektorp.support.CouchDbRepositorySupport;
import org.springframework.stereotype.Repository;
import th.sensornetwork.model.Settings;

@Repository
//@View(name = "all", map = "function(doc) { if (doc.documentType == 'Settings' ) emit( null, doc._id )}")
public class SettingsRepository extends CouchDbRepositorySupport<Settings> {
	public SettingsRepository(CouchDbConnector db) {
		super(Settings.class, db);
		initStandardDesignDocument();
	}
}
