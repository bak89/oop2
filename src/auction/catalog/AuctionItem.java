package auction.catalog;

import javax.xml.bind.annotation.*;

@XmlRootElement
public class AuctionItem {
    @XmlAttribute
    private int nr;//questo è un xml attribute->guarda catalog.xml

	@XmlElement
    private String description;//
	@XmlElement
	private double minimumPrice;//questo è un xml element->guarda catalog.xml

    public AuctionItem() {
    }

    public AuctionItem(int nr, String description, double minimumPrice) {
        this.nr = nr;
        this.description = description;
        this.minimumPrice = minimumPrice;
    }

    public int getNr() {
        return nr;
    }

    public void setNr(int nr) {
        this.nr = nr;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getMinimumPrice() {
        return minimumPrice;
    }

    public void setMinimumPrice(double minimumPrice) {
        this.minimumPrice = minimumPrice;
    }

    public String toString() {
        return nr + ": " + description + " (minimum price " + String.format("%1.2f", minimumPrice) + ")";
    }
}
