package auction.gui;

import auction.catalog.Auction;
import auction.catalog.AuctionCatalog;
import auction.catalog.AuctionCatalogDAO;
import auction.catalog.AuctionItem;
import auction.domain.AuctionStatus;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import java.time.LocalTime;

public class AuctionController {

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
            startAuctionButton.setDisable(false);
           // AuctionItem selectedAuctionItem = auctionItems.getSelectionModel().getSelectedItem();
            status.setText(String.valueOf(AuctionStatus.CREATED));
            Auction test = new Auction(LocalTime.now(),auctionItems.getItems().get(1));
            remTime.setText(String.valueOf(test.getRemainingTime()));
            endTime.setText(String.valueOf(LocalTime.now().plusMinutes(1)));


            status.setText(String.valueOf(AuctionStatus.RUNNING));
            /*LocalTime remainingTime = LocalTime.now();
            LocalTime endOFTime = remainingTime.plusMinutes(1);
            endTime.setText(String.valueOf(endOFTime));
            //print endTime to label!

            int remaTime;

            while (remainingTime == endOFTime) {
                remainingTime = remainingTime.plusSeconds(1);
                //print remTime to label!
                int end = endOFTime.getSecond();
                int rem = remainingTime.getSecond();
                remaTime = end - rem;
                remTime.setText(String.valueOf(remaTime));
            }*/


            //startAuctionButton.setDisable(true);

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

}
