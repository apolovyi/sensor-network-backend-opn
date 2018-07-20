package th.sensornetwork.controller;

public class ConnectRequestWrapper {

	String brokerAddress;
	String brokerUsername;
	String brokerPassword;
	String topics;
	String ignoredMeasurements;
	String acceptedMeasurements;

	public String getBrokerAddress() {
		return brokerAddress;
	}

	public void setBrokerAddress(String brokerAddress) {
		this.brokerAddress = brokerAddress;
	}

	public String getBrokerUsername() {
		return brokerUsername;
	}

	public void setBrokerUsername(String brokerUsername) {
		this.brokerUsername = brokerUsername;
	}

	public String getBrokerPassword() {
		return brokerPassword;
	}

	public void setBrokerPassword(String brokerPassword) {
		this.brokerPassword = brokerPassword;
	}

	public String getTopics() {
		return topics;
	}

	public void setTopics(String topics) {
		this.topics = topics;
	}

	public String getIgnoredMeasurements() {
		return ignoredMeasurements;
	}

	public void setIgnoredMeasurements(String ignoredMeasurements) {
		this.ignoredMeasurements = ignoredMeasurements;
	}

	public String getAcceptedMeasurements() {
		return acceptedMeasurements;
	}

	public void setAcceptedMeasurements(String acceptedMeasurements) {
		this.acceptedMeasurements = acceptedMeasurements;
	}
}
