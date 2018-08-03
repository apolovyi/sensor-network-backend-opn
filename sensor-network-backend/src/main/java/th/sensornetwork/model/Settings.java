package th.sensornetwork.model;

import org.ektorp.support.CouchDbDocument;
import java.util.*;

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

	public Set<String> getAcceptedMeasurements() {
		return acceptedMeasurements;
	}

	public void setAcceptedMeasurements(Set<String> acceptedMeasurements) {
		this.acceptedMeasurements = acceptedMeasurements;
	}

	public Set<String> getIgnoredMeasurements() {
		return ignoredMeasurements;
	}

	public void setIgnoredMeasurements(Set<String> ignoredMeasurements) {
		this.ignoredMeasurements = ignoredMeasurements;
	}

	public Set<String> getTopics() {
		return topics;
	}

	public void setTopics(Set<String> topics) {
		this.topics = topics;
	}

	public Set<String> getTypes() {
		return types;
	}

	public void setTypes(Set<String> types) {
		this.types = types;
	}

	public Set<String> getRooms() {
		return rooms;
	}

	public void setRooms(Set<String> rooms) {
		this.rooms = rooms;
	}

	public String getBrokerAddress() {
		return brokerAddress;
	}

	public void setBrokerAddress(String brokerAddress) {
		this.brokerAddress = brokerAddress;
	}

	public String getBrokerPassword() {
		return brokerPassword;
	}

	public void setBrokerPassword(String brokerPassword) {
		this.brokerPassword = brokerPassword;
	}

	public String getBrokerUsername() {
		return brokerUsername;
	}

	public void setBrokerUsername(String brokerUsername) {
		this.brokerUsername = brokerUsername;
	}
}
