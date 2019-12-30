package auction.catalog;

import java.util.ArrayList;
import java.util.List;

public class AuctionCatalog {

	private List<AuctionItem> items = new ArrayList<>();

	public List<AuctionItem> getItems() {
		return items;
	}

	public void setItems(List<AuctionItem> items) {
		this.items = items;
	}
}
