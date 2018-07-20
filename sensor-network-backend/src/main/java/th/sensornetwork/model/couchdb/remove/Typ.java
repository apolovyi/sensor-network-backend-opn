package th.sensornetwork.model.couchdb.remove;

public class Typ {

	private String id;
	private String bezeichnung;

	public Typ() {
	}

	public Typ(String id, String bezeichnung) {
		this.id = id;
		this.bezeichnung = bezeichnung;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getBezeichnung() {
		return bezeichnung;
	}

	public void setBezeichnung(String bezeichnung) {
		this.bezeichnung = bezeichnung;
	}
}
