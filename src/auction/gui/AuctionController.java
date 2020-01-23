package auction.gui;

import auction.catalog.Auction;
import auction.catalog.AuctionCatalog;
import auction.catalog.AuctionCatalogDAO;
import auction.catalog.AuctionItem;
import auction.domain.AuctionStatus;
import auction.domain.Bid;
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
            startAuction();
        });
    }

    @FXML
    public void startAuction() {
        assert this.auction == null;
        LocalTime endTime = LocalTime.now().plusMinutes(1);
        AuctionItem selectedAuctionItem = auctionItems.getSelectionModel().getSelectedItem();
        this.auction = new Auction(endTime, selectedAuctionItem);
        this.render();
        this.auction.addPropertyChangeListener(this);
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

    private void render() {
        if (this.auction != null) {
            this.endTime.setText(this.auction.getEndTime().toString());
            this.status.setText(String.valueOf(this.auction.getStatus()));
            this.remTime.setText(String.valueOf(this.auction.getRemainingTime()));
            boolean isRunning = this.auction.getStatus() == AuctionStatus.RUNNING;
            this.startAuctionButton.setDisable(isRunning);
            this.placeBidButton.setDisable(!isRunning);
            Bid currentBid = this.auction.getCurrentBid();
            if (currentBid != null) {
                this.currentBid.setText(currentBid.toString());
            } else {
                this.currentBid.setText("No one");
            }
        } else {
            this.startAuctionButton.setDisable(false);
            this.placeBidButton.setDisable(true);
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        Platform.runLater(this::render);
    }

}
