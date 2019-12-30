package auction.bidder;

public abstract class Bidder {

	private String name;

	public Bidder(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
