package auction.catalog;

import auction.bidder.Bidder;
import auction.domain.AuctionStatus;

import java.time.LocalTime;

public class Auction {
    private long duration;
    private LocalTime endTime;
    private AuctionStatus status;
    private AuctionItem item;

    public long getDuration() {
        return duration;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public AuctionStatus getStatus() {
        return status;
    }

    public AuctionItem getItem() {
        return item;
    }

    public Auction(LocalTime endTime, AuctionItem item) {
        this.endTime = endTime;
        this.status = AuctionStatus.CREATED;
        this.item = item;
    }

    public void start() {
        //cambia lo status da non attivo in attivo(e anche il label status)
        status = AuctionStatus.CREATED;
        getRemainingTime();
    }

    public int getRemainingTime() {
        //se non lavora null

        //cambia lo status in running
        status = AuctionStatus.RUNNING;

        LocalTime remainingTime = LocalTime.now();
        endTime = remainingTime.plusMinutes(1);
        //print endTime to label!

        int remTime = 0;

        while (remainingTime == endTime) {
            remainingTime = remainingTime.plusSeconds(1);
            //print remTime to label!
            int end = endTime.getSecond();
            int rem = remainingTime.getSecond();
            remTime = end - rem;
            System.out.println(remTime);
        }
      //  end();

        return remTime;

    }

    public void end() {
        //cambia lo status
        status = AuctionStatus.ENDED;
        //stoppa il timer local time
    }

    public Bidder register(Bidder bidder) {
        //creo un nuovo tipo che vuole puntare(può essere umano o robot)
        return bidder;
    }

    public Bidder bid(Bidder b, double amount) {
        //devo salvare l'offerta che ho fatto

        return b;
    }


}
