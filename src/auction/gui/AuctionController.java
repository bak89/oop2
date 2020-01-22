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

    private Auction auction = null;

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
        startAuctionButton.setOnAction(click -> {
            startAuctionButton.setDisable(true);
            startAuction();
        });
    }

    @FXML
    public void startAuction() {
        assert this.auction == null;
        LocalTime endTime = LocalTime.now().plusMinutes(1);
        AuctionItem selectedAuctionItem = auctionItems.getSelectionModel().getSelectedItem();
        this.auction = new Auction(endTime, selectedAuctionItem);
        this.auction.addPropertyChangeListener(this);

        this.endTime.setText(endTime.toString());
        this.status.setText(String.valueOf(this.auction.getStatus()));
        this.remTime.setText(String.valueOf(this.auction.getRemainingTime()));

        this.auction.start();
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
    public void propertyChange(PropertyChangeEvent evt) {
        Platform.runLater(() -> {
            switch (evt.getPropertyName()) {
                case "status":
                    this.status.setText(evt.getNewValue().toString());
                    this.startAuctionButton.setDisable(evt.getNewValue() == AuctionStatus.RUNNING);
                    this.placeBidButton.setDisable(evt.getNewValue() != AuctionStatus.RUNNING);
                    break;
                case "remainingTime":
                    this.remTime.setText(evt.getNewValue().toString());
                    break;
                case "bid":
                    this.currentBid.setText(evt.getNewValue().toString());
                    break;
            }
        });

    }

}
