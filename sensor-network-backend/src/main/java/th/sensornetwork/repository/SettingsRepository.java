package th.sensornetwork.repository;

import org.ektorp.CouchDbConnector;
import org.ektorp.support.CouchDbRepositorySupport;
import org.springframework.stereotype.Repository;
import th.sensornetwork.model.Settings;

@Repository
public class SettingsRepository extends CouchDbRepositorySupport<Settings> {
	public SettingsRepository(CouchDbConnector db) {
		super(Settings.class, db);
		initStandardDesignDocument();
	}
}
