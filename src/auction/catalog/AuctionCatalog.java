package auction.catalog;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;
//questa è la main class per il jaxb
@XmlRootElement
public class AuctionCatalog {

	@XmlElement(name="item")
	private List<AuctionItem> items = new ArrayList<>();

	public List<AuctionItem> getItems() {
		return items;
	}

	public void setItems(List<AuctionItem> items) {
		this.items = items;
	}
}
