package th.sensornetwork.controller;

import th.sensornetwork.model.SensorCandidate;

public class NewSensorWrapper {
	String          name;
	String          room;
	String          spID;
	SensorCandidate sensorCandidate;

	public void setName(String name) {
		this.name = name;
	}

	public void setRoom(String room) {
		this.room = room;
	}

	public void setSpID(String spID) {
		this.spID = spID;
	}

	public void setSensorCandidate(SensorCandidate sensorCandidate) {
		this.sensorCandidate = sensorCandidate;
	}
}
