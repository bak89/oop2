package auction.domain;

import java.time.LocalTime;

import auction.bidder.Bidder;

public class Bid {

	private Bidder bidder;
	private double amount;
	private LocalTime timestamp;

	public Bid(Bidder bidder, double amount) {
		this.bidder = bidder;
		this.amount = amount;
		this.timestamp = LocalTime.now();
	}

	public Bidder getBidder() {
		return bidder;
	}

	public double getAmount() {
		return amount;
	}

	public LocalTime getTimestamp() {
		return timestamp;
	}

	public String toString() {
		return amount + " from " + bidder.getName() + " (" + timestamp + ")";
	}
}
