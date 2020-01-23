package auction.catalog;

import javafx.fxml.FXML;

import java.awt.print.Book;

public class AuctionCatalogDAO {
    private static AuctionCatalog catalog = null;
    public static AuctionCatalog loadCatalog() {
        if (catalog == null) {
            catalog = new AuctionCatalog();
            catalog.getItems().add(new AuctionItem(1, "Baby Phone", 10.0));
            catalog.getItems().add(new AuctionItem(2, "Lawn Mower", 50.0));
            catalog.getItems().add(new AuctionItem(3, "Exam Solution", 200.0));
        }
        return catalog;
    }
}
