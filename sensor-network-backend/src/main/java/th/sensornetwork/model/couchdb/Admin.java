package th.sensornetwork.model.couchdb;

import lombok.Data;
import lombok.NonNull;

@Data
public class Admin {

	@NonNull
	private String email;
	@NonNull
	private String password;

}