package th.sensornetwork.model.couchdb;

import lombok.Data;
import org.ektorp.support.CouchDbDocument;

import javax.validation.constraints.NotNull;
import java.util.*;
import java.util.stream.Collectors;

@Data
public class Settings extends CouchDbDocument {

	private Set<String> acceptedMeasurements;
	private Set<String> ignoredMeasurements;
	private Set<String> topics;
	private Set<String> types;
	private Set<String> rooms;
	private String      brokerAddress;
	private String      brokerPassword;
	private String      brokerUsername;

	public Settings() {
		super.setId("Settings");
		this.acceptedMeasurements = new HashSet<>();
		this.ignoredMeasurements = new HashSet<>();
		this.topics = new HashSet<>();
		this.types = new HashSet<>();
		this.rooms = new HashSet<>();
	}

	public void addAcceptedMeasurements(List<String> entities) {
		this.acceptedMeasurements.addAll(entities);
	}

	public void addIgnoredMeasurements(List<String> entities) {
		this.ignoredMeasurements.addAll(entities);
	}

}
