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

import javax.annotation.PostConstruct;
import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class MqttClientTH implements MqttCallback {

	private PersistenceCouchDB  persistenceCouchDB;
	private PersistenceInfluxDB persistenceInfluxDB;

	private       MqttClient client;
	private       Settings   settings;
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
			connectOptions.setAutomaticReconnect(true);
			connectOptions.setKeepAliveInterval(600000);
			try {
				client = new MqttClient(settings.getBrokerAddress(), "MQTT_TH_Koeln2");
				client.setCallback(this);
				client.connect(connectOptions);
				String[] topicsList = settings.getTopics()
						.toArray(new String[0]);
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
		updateMqttClientSettings(newSettings);
		connectToMqttBroker();
		return this.settings;
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

	public Settings deleteSettings() {
		Settings newSettings = new Settings();

		try {
			settings = persistenceCouchDB.getCouchDB().get(Settings.class, SETTINGS_DOC_ID);
			newSettings.setRevision(settings.getRevision());
			persistenceCouchDB.getCouchDB().delete(newSettings);
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		return newSettings;
	}

	@PostConstruct
	private void createDefaultSettings() {

		String ient = "ERROR,LOWBAT,LED_STATUS,UNREACH,STICKY_UNREACH,CONTROL_MODE" +
				"COMMUNICATION_REPORTING,PARTY_STOP_MONTH,PARTY_START_MONTH,PARTY_STOP_DAY,"
				+ "PARTY_STOP_TIME,PARTY_STOP_YEAR,WINDOW_OPEN_REPORTING,LOWBAT_REPORTING," +
				"PARTY_START_YEAR,PARTY_START_TIME,PARTY_START_DAY,CONFIG_PENDING," +
				"PARTY_TEMPERATURE,FAULT_REPORTING,BOOST_STATE,CONTROL_MODE,INHIBIT," +
				"DEVICE_IN_BOOTLOADER,VisuellesSignal,AkustischesSignal," +
				"COMMUNICATION_REPORTING,BOOST_STATE,PARTY_TEMPERATURE,VALVE_STATE,BATTERY_STATE,WORKING,ENERGY_COUNTER,BOOT,PRESS_SHORT,INSTALL_TEST,PRESS_LONG";

		Settings settings = new Settings();

		settings.addIgnoredMeasurements(Arrays.asList(ient.split(",")));
		settings.getTopics().add("th/hm/status/");
		settings.getRooms().add("Computer lab");
		settings.getRooms().add("Computer lab backroom");
		settings.getTypes().add("Unit");
		settings.getTypes().add("State");


		Map<String, String> semanticUnit = new HashMap<>();
		semanticUnit.put("ts", "ts");
		semanticUnit.put("value", "val");
		semanticUnit.put("unit", "hm_unit");

		Map<String, String> semanticState = new HashMap<>();
		semanticState.put("ts", "ts");
		semanticState.put("value", "val");

		SensorProduct sp1 = new SensorProduct("HomeMatic", "Unit", semanticUnit);
		SensorProduct sp2 = new SensorProduct("HomeMatic", "State", semanticState);

		//persistenceCouchDB.getCouchDB().create(new TemporaryData());

		try {
			persistenceCouchDB.getCouchDB().create(settings);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		this.settings = persistenceCouchDB.getCouchDB()
				.get(Settings.class, SETTINGS_DOC_ID);

		try {
			persistenceCouchDB.getCouchDB().create(sp1);
			persistenceCouchDB.getCouchDB().create(sp2);
		}
		catch (Exception e) {
			e.printStackTrace();
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

		if (temporaryData.getTemporarySensors()
				.stream()
				.noneMatch(x -> x.getSensorID().equals(sensorId)))
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

		try {
			System.out.println("\nExtracting property: " + sensorEntityName + "\n");
			sensor = persistenceCouchDB.getCouchDB().get(Sensor.class, sensorId);
		}
		catch (DocumentNotFoundException e) {
			e.printStackTrace();
		}
		assert sensor != null;
		Map<String, String> semantic = persistenceCouchDB.getCouchDB()
				.get(SensorProduct.class, sensor.getSensorProductID())
				.getSemantic();
		System.out.println("\nUpdating Sensor: " + sensor.getSensorName() + " " + "Entity: "
				+ sensorEntityName + "\n");
		persistenceCouchDB.updateSensor(sensor, sensorEntityName, semantic, receivedData);
		persistenceInfluxDB.writeData(sensorId, sensorEntityName, sensor.getRoom(), semantic, receivedData);

	}

	public boolean addSensorFromTemporaryData(@NotNull String name, @NotNull String room,
			@NotNull String spID, @NotNull TemporarySensor ts) {

		if (this.settings == null)
			this.settings = getCurrentSettings();

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

	}

	//1: accepted Entity
	//2: ignored Entity
	//0: unknown Entity
	private int getMessageStatus(String sensorId, String entity) {
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

		//this.settings = persistenceCouchDB.getCouchDB().get(Settings.class,
		// SETTINGS_DOC_ID);
		System.out.println("Message received:\n\t" + messageTopic + "\n\t" + new String
				(mqttMessage
				.getPayload()));

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
