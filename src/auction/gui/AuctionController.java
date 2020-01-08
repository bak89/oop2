package auction.gui;

import auction.catalog.Auction;
import auction.catalog.AuctionCatalog;
import auction.catalog.AuctionCatalogDAO;
import auction.catalog.AuctionItem;
import auction.domain.AuctionStatus;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.time.LocalTime;

public class AuctionController implements PropertyChangeListener {

    private Auction auction;

    @FXML
    public Label status;
    public Label endTime;
    public Label remTime;
    public Label currentBid;

    @FXML
    private ListView<AuctionItem> auctionItems;

    @FXML
    private Button startAuctionButton;

    @FXML
    private Button placeBidButton;


    public void initialize() {
        AuctionCatalog catalog = AuctionCatalogDAO.loadCatalog();
        catalog.getItems().forEach(item -> auctionItems.getItems().add(item));
        auctionItems.getSelectionModel().select(0);
    }

    @FXML
    public void startAuction() {
        startAuctionButton.setOnAction(click -> {
            status.setText(String.valueOf(AuctionStatus.CREATED));
            auction.start();
            status.setText(String.valueOf(auction.getStatus()));


            // AuctionItem selectedAuctionItem = auctionItems.getSelectionModel().getSelectedItem();
            //Auction test = new Auction(LocalTime.now(), auctionItems.getItems().get(1));
            //  remTime.setText(String.valueOf(test.getRemainingTime()));
            endTime.setText(String.valueOf(LocalTime.now().plusMinutes(1)));


            status.setText(String.valueOf(AuctionStatus.RUNNING));
            /*LocalTime remainingTime = LocalTime.now();
            LocalTime endOFTime = remainingTime.plusMinutes(1);
            endTime.setText(String.valueOf(endOFTime));

           */


        });
    }

    @FXML
    public void placeBid() {
        //placeBid.setOnAction();
    }

    class HumanBidder {
        public void auctionChanged() {
        }

    }

    @Override
    public void propertyChange(PropertyChangeEvent propertyChangeEvent) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                startAuctionButton.setDisable(auction.isRunning());
                placeBidButton.setDisable(!auction.isRunning());
            }
        });

    }

}
