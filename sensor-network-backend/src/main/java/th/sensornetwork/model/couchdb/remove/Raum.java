package th.sensornetwork.model.couchdb.remove;

public class Raum {

	private String id;
	private String nummer;

	public Raum() {
	}

	public Raum(String id, String nummer) {
		this.id = id;
		this.nummer = nummer;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNummer() {
		return nummer;
	}

	public void setNummer(String nummer) {
		this.nummer = nummer;
	}
}
