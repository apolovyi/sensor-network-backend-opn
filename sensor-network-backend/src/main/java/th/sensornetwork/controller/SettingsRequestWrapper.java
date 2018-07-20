package th.sensornetwork.controller;

import java.util.Set;

public class SettingsRequestWrapper {

	String brokerAddress;
	String brokerUsername;
	String brokerPassword;
	/*String ignoredMeasurements;
	String acceptedMeasurements;*/

	Set<String> topics;

	Set<String> ignoredMeasurements;
	Set<String> acceptedMeasurements;

	/*
	Set<String> topics;*/

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

	/*public String getIgnoredMeasurements() {
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

	public String getTopics() {
		return topics;
	}

	public void setTopics(String topics) {
		this.topics = topics;
	}*/

	public Set<String> getIgnoredMeasurements() {
		return ignoredMeasurements;
	}

	public void setIgnoredMeasurements(Set<String> ignoredMeasurements) {
		this.ignoredMeasurements = ignoredMeasurements;
	}

	public Set<String> getAcceptedMeasurements() {
		return acceptedMeasurements;
	}

	public void setAcceptedMeasurements(Set<String> acceptedMeasurements) {
		this.acceptedMeasurements = acceptedMeasurements;
	}


	public Set<String> getTopics() {
		return topics;
	}

	public void setTopics(Set<String> topics) {
		this.topics = topics;
	}
}
