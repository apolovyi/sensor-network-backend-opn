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
		createDefaultSettings();
	}

	public void createDefaultSettings() {
		String topic = "th/hm/status/";
		/*String aent = "STATE,MOTION,BRIGHTNESS,ACTUAL_TEMPERATURE,ACTUAL_HUMIDITY," +
                "SET_TEMPERATURE,PARTY_TEMPERATURE,BATTERY_STATE," +
                "ENERGY_COUNTER,POWER,CURRENT,VOLTAGE,FREQUENCY,TEMPERATURE,HUMIDITY";*/
		String ient = "ERROR,LOWBAT,LED_STATUS,UNREACH,STICKY_UNREACH,CONTROL_MODE" +
                "COMMUNICATION_REPORTING,PARTY_STOP_MONTH,PARTY_START_MONTH,PARTY_STOP_DAY,"
                + "PARTY_STOP_TIME,PARTY_STOP_YEAR,WINDOW_OPEN_REPORTING,LOWBAT_REPORTING,"
                + "PARTY_START_YEAR,PARTY_START_TIME,PARTY_START_DAY,CONFIG_PENDING," +
                "PARTY_TEMPERATURE,FAULT_REPORTING,BOOST_STATE,CONTROL_MODE,INHIBIT," +
                "DEVICE_IN_BOOTLOADER,VisuellesSignal,AkustischesSignal," +
                "COMMUNICATION_REPORTING,VALVE_STATE,BOOST_STATE";

		addTopics(Arrays.asList(topic.split(",")));
		//addAcceptedMeasurements(Arrays.asList(aent.split(",")));
		addIgnoredMeasurements(Arrays.asList(ient.split(",")));
		this.rooms.add("Computer lab");
		this.rooms.add("Computer lab backroom");

		this.types.add("Unit");
		this.types.add("State");

		//this.brokerAddress = "tcp://139.6.17.21:1883";
		//this.brokerPassword = "THKAbsolvent17";
		//this.brokerUsername = "absolvent";

	}

	private static boolean isEmpty(String s) {
		return s == null || s.trim().isEmpty();
	}

	public void setAcceptedMeasurementsFromString(String s) {
		if (!isEmpty(s))
			this.acceptedMeasurements = Arrays.stream(s.split(","))
					.collect(Collectors.toSet());
	}

	public void setIgnoredMeasurementsFromString(String s) {
		if (!isEmpty(s))
			this.ignoredMeasurements = Arrays.stream(s.split(",")).collect(Collectors.toSet
                    ());
	}

	public void setTopicsFromString(String s) {
		if (!isEmpty(s))
			this.topics = Arrays.stream(s.split(",")).collect(Collectors.toSet());
	}

	public String getRoom(String room) {
		this.rooms.add(room);
		return room;
	}

	public String getType(String type) {
		this.types.add(type);
		return type;

	}

	public void addAcceptedMeasurements(List<String> entities) {
		this.acceptedMeasurements.addAll(entities);
	}

	public void removeAcceptedMeasurements(List<String> entities) {
		this.acceptedMeasurements.removeAll(entities);
	}

	public void addIgnoredMeasurements(List<String> entities) {
		this.ignoredMeasurements.addAll(entities);
	}

	public void removeIgnoredMeasurements(List<String> entities) {
		this.ignoredMeasurements.removeAll(entities);
	}

	public void addTopics(List<String> topics) {
		this.topics.addAll(topics);
	}

	public void removeTopics(List<String> topics) {
		this.topics.removeAll(topics);
	}

	private Set<String> removeEmptyStrings(Set<String> set) {
		return set.stream().filter(x -> !isEmpty(x)).collect(Collectors.toSet());

	}

	public void setAcceptedMeasurements(@NotNull Set<String> acceptedMeasurements) {
		this.acceptedMeasurements = removeEmptyStrings(acceptedMeasurements);
	}

	public void setIgnoredMeasurements(@NotNull Set<String> ignoredMeasurements) {
		this.ignoredMeasurements = removeEmptyStrings(ignoredMeasurements);
	}

	public void setTopics(@NotNull Set<String> topics) {
		this.topics = removeEmptyStrings(topics);
	}


}
