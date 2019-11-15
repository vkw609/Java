package application;

public class Publishers {
	private int id;
	private String publisher;
	
	public Publishers() {
		id = 0;
		publisher = "";
	}
	
	public Publishers(int id, String make) {
		this.id = id;
		this.publisher = make;
	}

	@Override
	public String toString() {
		return publisher;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String make) {
		this.publisher = make;
	}

}