package auction.domain;

import auction.bidder.Bidder;
import auction.catalog.Auction;
import auction.catalog.AuctionItem;
import auction.catalog.InvalidBidException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AuctionTest {

    private Auction auction;
    private Bidder bidder;

    @BeforeEach
    public void init() {
        AuctionItem item = new AuctionItem(0, "Test Item", 100.0);
       // auction = new Auction(item, 10);
        bidder = new Bidder("Test Bidder", auction) {
            public void auctionChanged() {
            }
        };
    }

    @Test
    public void testAuctionItem() {
        assertEquals(0, auction.getItem().getNr());
    }

    @Test
    public void testAuctionCreated() {
        assertEquals(AuctionStatus.CREATED, auction.getStatus());
        auction.end();
        assertEquals(AuctionStatus.CREATED, auction.getStatus());
    }

    @Test
    public void testAuctionRunning() throws InterruptedException {
        auction.start();
        assertEquals(AuctionStatus.RUNNING, auction.getStatus());
        auction.start();
        assertEquals(AuctionStatus.RUNNING, auction.getStatus());
    }

    @Test
    public void testAuctionEnded() throws InterruptedException {
        auction.start();
        auction.end();
        assertEquals(AuctionStatus.ENDED, auction.getStatus());
        auction.end();
        assertEquals(AuctionStatus.ENDED, auction.getStatus());
        auction.start();
        assertEquals(AuctionStatus.ENDED, auction.getStatus());
    }

    @Test
    public void testEndTime() throws InterruptedException {
        int endTime = LocalTime.now().toSecondOfDay() + 10;
        auction.start();
        assertEquals(endTime, auction.getEndTime().toSecondOfDay(), 1);
    }

    @Test
    public void testRemainingTime() throws InterruptedException {
        auction.start();
        assertEquals(10, auction.getRemainingTime(), 1);
    }

    @Test
    public void testBid() throws InvalidBidException {
        auction.start();
        auction.bid(bidder, 100.0);
        //assertEquals(bidder, auction.getCurrentBid().getBidder());
        //assertEquals(100.0, auction.getCurrentBid().getAmount(), 0.0);
    }

    public void testBidBeforeAuctionStarted() throws InvalidBidException {
        Assertions.assertThrows(InvalidBidException.class, () -> {
            auction.bid(bidder, 100.0);
        });
    }

    public void testBidAfterAuctionEnded() throws InvalidBidException {
        Assertions.assertThrows(InvalidBidException.class, () -> {
            auction.start();
            auction.end();
            auction.bid(bidder, 100.0);
        });
    }

    public void testBidLowerThanMinimumPrice() throws InvalidBidException {
        Assertions.assertThrows(InvalidBidException.class, () -> {
            auction.start();
            auction.bid(bidder, 90.0);
        });
    }

    public void testBidNotGreaterThanCurrentBid() throws InvalidBidException {
        Assertions.assertThrows(InvalidBidException.class, () -> {
            auction.start();
            auction.bid(bidder, 100.0);
            auction.bid(bidder, 100.0);
        });
    }
}
