package th.sensornetwork.client;

import org.eclipse.paho.client.mqttv3.*;
import org.ektorp.DocumentNotFoundException;
import org.ektorp.UpdateConflictException;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import th.sensornetwork.model.couchdb.*;
import th.sensornetwork.repository.couchdb.PersistenceCouchDB;
import th.sensornetwork.repository.influxdb.PersistenceInfluxDB;

import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class MqttClientTH implements MqttCallback {

    private PersistenceCouchDB  persistenceCouchDB;
	private PersistenceInfluxDB persistenceInfluxDB;

	private       MqttClient client;
	private Settings settings;
	private final String     SETTINGS_DOC_ID = "Settings";

	@Autowired
	public MqttClientTH(PersistenceCouchDB persistenceCouchDB, PersistenceInfluxDB
			persistenceInfluxDB) {
		this.persistenceCouchDB = persistenceCouchDB;
		this.persistenceInfluxDB = persistenceInfluxDB;
	}

	private void connectToMqttBroker() {
		if (this.settings != null) {
			MqttConnectOptions connectOptions = new MqttConnectOptions();
			connectOptions.setUserName(settings.getBrokerUsername());
			connectOptions.setPassword(settings.getBrokerPassword().toCharArray());
			connectOptions.setConnectionTimeout(0);
			try {
				client = new MqttClient(settings.getBrokerAddress(), "MQTT_TH_Koeln0");
				client.setCallback(this);
				client.connect(connectOptions);
				String[] topicsList = settings.getTopics()
						.toArray(new String[settings.getTopics().size()]);
				for (String topic : topicsList) {
					client.subscribe(topic + "#");
				}
			}
			catch (Exception me) {
				me.printStackTrace();
			}
		}
		else {
			System.out.println("Couldn't get settings");
		}
	}

	private void updateMqttClientSettings(Settings settings) {
		Settings oldSettings = null;
		try {
			oldSettings = persistenceCouchDB.getCouchDB().get(Settings.class,
					SETTINGS_DOC_ID);
		}
		catch (DocumentNotFoundException e) {
			e.printStackTrace();
		}
		if (oldSettings == null) {
			System.out.println("Creating settings\n");
			try {
				persistenceCouchDB.getCouchDB().create(settings);
				System.out.println("Settings created\n");
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		else {
			System.out.println("Updating settings\n");
			try {
				settings.setRevision(oldSettings.getRevision());
				persistenceCouchDB.getCouchDB().update(settings);
				System.out.println("Settings updated\n");
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}

		this.settings = persistenceCouchDB.getCouchDB().get(Settings.class, SETTINGS_DOC_ID);
	}


	public Settings updateMqttClient(Settings newSettings) {

		persistenceCouchDB.deleteTemporaryData();

		disconnectFromMqttBroker();

		/*if (this.settings == null)
			this.settings*/

		updateMqttClientSettings(newSettings);

		//for test without semantic
		//createDefaultSemantic();

		connectToMqttBroker();
		return this.settings;
		//return newSettings;
	}

	public void refresh() {
		disconnectFromMqttBroker();

		//this.settings = persistenceCouchDB.getCouchDB().get(Settings.class,
		// SETTINGS_DOC_ID);

		connectToMqttBroker();
	}

	public Settings getCurrentSettings() {
		Settings settings = new Settings();

		try {
			settings = persistenceCouchDB.getCouchDB().get(Settings.class, SETTINGS_DOC_ID);
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		return settings;
	}

	private void createDefaultSemantic() {

		String timestampSemantic = "ts,ts";
		String valueSemantic     = "value,val";
		String unitSemantic      = "unit,hm_unit";

		String[] sensorTypes = ("Fenster/Tuer-kontakt,Wandthermostat,Heizungsthermostat," +
				"Bewegungsmelder,ToDo")
				.split(",");

		Map<String, String> semantic = new HashMap<>();
		semantic.put(timestampSemantic.split(",")[0], timestampSemantic.split("," + "")[1]);
		semantic.put(valueSemantic.split(",")[0], valueSemantic.split(",")[1]);
		semantic.put(unitSemantic.split(",")[0], unitSemantic.split(",")[1]);
		for (String sensorType : sensorTypes) {
			SensorProduct sp = new SensorProduct();
			sp.setId("Hersteller:" + sensorType);
			sp.setSemantic(semantic);
			try {
				persistenceCouchDB.getCouchDB().create(sp);
			}
			catch (UpdateConflictException e) {
				e.printStackTrace();
			}
		}

	}

	public void disconnectFromMqttBroker() {

		try {
			if (client != null)
				client.disconnect();
		}
		catch (MqttException e) {
			e.printStackTrace();
		}
	}

	private void processMessageByAdmin(@NotNull String sensorId, @NotNull String
			sensorEntityName, @NotNull JSONObject receivedData) {

		System.out.println("\nUpdating temporaryData: \n" + sensorId + "_" +
				sensorEntityName);
		System.out.println("\nData: \n" + receivedData);
		TemporaryData temporaryData = new TemporaryData();
		try {
			temporaryData = persistenceCouchDB.getCouchDB()
					.get(TemporaryData.class, "TemporaryData");
		}
		catch (DocumentNotFoundException e) {
			e.printStackTrace();
		}

		TemporarySensor temporarySensor = new TemporarySensor(sensorId);
		TemporaryMeasurement temporaryMeasurement = new TemporaryMeasurement
				(sensorEntityName, receivedData
				.toString());
		temporarySensor.getMeasurements().add(temporaryMeasurement);

		if (!temporaryData.getTemporarySensors()
				.stream()
				.anyMatch(x -> x.getSensorID().equals(sensorId)))
			temporaryData.getTemporarySensors().add(temporarySensor);
		else {
			temporarySensor = temporaryData.getTemporarySensors()
					.stream()
					.filter(x -> x.getSensorID().equals(sensorId))
					.findFirst()
					.get();
			temporarySensor.getMeasurements().add(temporaryMeasurement);
		}
		persistenceCouchDB.getCouchDB().update(temporaryData);
	}

	private void processMessageBySystem(@NotNull String sensorId, @NotNull String
			sensorEntityName, @NotNull JSONObject receivedData) {

		Sensor sensor = null;

		if (sensorId != null)
			try {
				System.out.println("\nExtracting property: " + sensorEntityName + "\n");
				sensor = persistenceCouchDB.getCouchDB().get(Sensor.class, sensorId);
			}
			catch (DocumentNotFoundException e) {
				e.printStackTrace();
			}
		Map<String, String> semantic = persistenceCouchDB.getCouchDB()
				.get(SensorProduct.class, sensor.getSensorProductID())
				.getSemantic();
		if (receivedData != null) {
			if (sensor == null) {

				//can't reach this state- sensors are only created by admin


				/*if (sensorId != null) {
					System.out.println("\nCreating Sensor: " + sensorId + " " + "Entity: " +
							"" + "" + "" + sensorEntityName + "\n");

					persistenceCouchDB.createSensorTestPhase(sensorId, sensorEntityName,
							semantic, receivedData);

					*//*persistenceCouchDB.createSensor(sensorId, sensorId, room ,
							type, spID, temporarySensor);*//*

					persistenceInfluxDB.writeData(sensorId, sensorEntityName, receivedData);
				}*/
			}
			else {
				System.out.println("\nUpdating Sensor: " + sensor.getSensorName() + " " +
						"Entity: " + sensorEntityName + "\n");
				persistenceCouchDB.updateSensor(sensor, sensorEntityName, semantic,
						receivedData);
				persistenceInfluxDB.writeData(sensorId, sensorEntityName, receivedData);
			}
		}
	}

	public boolean addSensorFromTemporaryData(String name, String room, String spID,
			TemporarySensor ts) {

		if (this.settings == null)
			this.settings = getCurrentSettings();

		/*td.getTemporarySensors()
				.forEach(x -> persistenceCouchDB.createSensor(x.getSensorID(), x.getSensorID
						(), "room", "Too", "Hersteller:Too", x));*/

		TemporaryData td = persistenceCouchDB.getCouchDB()
				.get(TemporaryData.class, "TemporaryData");
		boolean removed = td.getTemporarySensors().remove(ts);
		try {
			TemporaryData newTD = persistenceCouchDB.getCouchDB()
					.get(TemporaryData.class, "TemporaryData");
			td.setRevision(newTD.getRevision());
			persistenceCouchDB.getCouchDB().update(td);
		}
		catch (Exception e) {
			e.printStackTrace();
		}


		/*this.settings.addAcceptedMeasurements(ts.getMeasurements()
				.stream()
				.map(TemporaryMeasurement::getMeasurement)
				.collect(Collectors.toList()));*/

		try {
			this.settings = getCurrentSettings();
			this.settings.addAcceptedMeasurements(ts.getMeasurements()
					.stream()
					.map(TemporaryMeasurement::getMeasurement)
					.collect(Collectors.toList()));
			persistenceCouchDB.getSettingsRepository().update(this.settings);
		}
		catch (Exception e) {
			e.printStackTrace();
		}


		String type = persistenceCouchDB.getCouchDB().get(SensorProduct.class, spID)
				.getType();

		return persistenceCouchDB.createSensor(ts.getSensorID(), name, room, type, spID, ts);


		/*Settings settings = persistenceCouchDB.getCouchDB().get(Settings.class, "Settings");
		td.getTemporarySensors()
				.forEach(x -> this.settings.addAcceptedMeasurements(x.getMeasurements()
						.stream()
						.map(TemporaryMeasurement::getMeasurement)
						.collect(Collectors.toList())));
		persistenceCouchDB.getCouchDB().update(this.settings);

		TemporaryData newTD = new TemporaryData();
		newTD.setRevision(td.getRevision());

		persistenceCouchDB.getCouchDB().update(newTD);
		return persistenceCouchDB.getCouchDB().get(TemporaryData.class, "TemporaryData");*/


	}

	//1: accepted Entity
	//2: ignored Entity
	//0: unknown Entity
	private int getMessageStatus(String sensorId, String entity) {
		if (sensorId.equals("Heizungsthermostat-2-Sender"))
			System.out.println("yes");
		boolean containsSensor = persistenceCouchDB.getCouchDB().contains(sensorId);
		if (this.settings.getAcceptedMeasurements()
				.stream()
				.anyMatch(Objects.requireNonNull(entity)::contains) && containsSensor) {
			return 1;
		}
		else if (this.settings.getIgnoredMeasurements()
				.stream()
				.anyMatch(Objects.requireNonNull(entity)::contains)) {
			return 2;
		}
		return 0;
	}

	@Override
	public void messageArrived(String messageTopic, MqttMessage mqttMessage) {

		this.settings = persistenceCouchDB.getCouchDB().get(Settings.class, SETTINGS_DOC_ID);
		System.out.println("Message received:\n\t" + messageTopic + "\n\t" + new String
				(mqttMessage.getPayload()));

		if (this.settings != null) {
			String topic = this.settings.getTopics()
					.stream()
					.filter(messageTopic::contains)
					.findAny()
					.orElse("empty");

			if (!topic.equals("empty")) {
				messageTopic = messageTopic.substring(topic.length(), messageTopic.length());

				String sensorId         = null;
				String sensorEntityName = null;

				JSONObject receivedData = null;

				try {
					sensorId = messageTopic.split("/")[0];
					sensorEntityName = messageTopic.split("/")[1];
				}
				catch (Exception e) {
					e.printStackTrace();
				}

				try {
					receivedData = new JSONObject(new String(mqttMessage.getPayload()));
				}
				catch (JSONException e) {
					e.printStackTrace();
				}

				/*if(sensorEntityName.equals("VALVE_STATE"))
					System.out.println("Yes");*/

				int messageStatus = getMessageStatus(sensorId, sensorEntityName);

				switch (messageStatus) {
					case 0:
						processMessageByAdmin(sensorId, sensorEntityName, receivedData);
						break;
					case 1:
						processMessageBySystem(sensorId, sensorEntityName, receivedData);
						break;
					default:
						break;
				}
			}
		}
		else {
			System.out.println("Couldn't get settings");
		}
	}

	@Override
	public void connectionLost(Throwable throwable) {
		System.out.println("Connection to MQTT broker lost! Trying to reconnect");

		connectToMqttBroker();
	}

	@Override
	public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
	}


}
