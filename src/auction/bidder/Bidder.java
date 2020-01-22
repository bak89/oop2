package auction.bidder;

import auction.catalog.Auction;

public abstract class Bidder {

	private String name;

	public Bidder(String name) {
		this.name = name;
	}

	public Bidder(String s, Auction auction) {

	}

    public String getName() {
		return name;
	}

	public abstract void auctionChanged();
}
