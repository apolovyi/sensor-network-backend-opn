package th.sensornetwork.controller;

import th.sensornetwork.model.couchdb.TemporarySensor;

public class NewSensorWrapper {
	String          name;
	String          room;
	String          spID;
	TemporarySensor temporarySensor;

	public void setName(String name) {
		this.name = name;
	}

	public void setRoom(String room) {
		this.room = room;
	}

	public void setSpID(String spID) {
		this.spID = spID;
	}

	public void setTemporarySensor(TemporarySensor temporarySensor) {
		this.temporarySensor = temporarySensor;
	}
}
