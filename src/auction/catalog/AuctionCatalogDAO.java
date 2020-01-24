package auction.catalog;

import javafx.fxml.FXML;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.FileNotFoundException;
import java.io.FileReader;


public class AuctionCatalogDAO {


    public static AuctionCatalog loadCatalog() {

        try {
            JAXBContext context = JAXBContext.newInstance(AuctionCatalog.class);
            Unmarshaller um = context.createUnmarshaller();

            return (AuctionCatalog) um.unmarshal(new FileReader("catalog.xml"));


        } catch (JAXBException | FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }


        /*if (catalog == null) {
            catalog = new AuctionCatalog();
            catalog.getItems().add(new AuctionItem(1, "Baby Phone", 10.0));
            catalog.getItems().add(new AuctionItem(2, "Lawn Mower", 50.0));
            catalog.getItems().add(new AuctionItem(3, "Exam Solution", 200.0));

        return catalog;}*/
    }
}
