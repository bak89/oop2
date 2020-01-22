package auction.catalog;

import auction.bidder.Bidder;
import auction.domain.AuctionStatus;
import auction.domain.Bid;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

//all in one thread
public class Auction implements Runnable {
    private LocalTime endTime;
    private AuctionStatus status;
    private AuctionItem item;
    private Thread thread;
    private PropertyChangeSupport changeSupport;
    private List<Bidder> bidders = new ArrayList<Bidder>();
    private Bid currentBid = null;

    public Auction(LocalTime endTime, AuctionItem item) {
        this.endTime = endTime;
        this.status = AuctionStatus.CREATED;
        this.item = item;
        this.changeSupport = new PropertyChangeSupport(this);
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public AuctionStatus getStatus() {
        return status;
    }

    private void setStatus(AuctionStatus s) {
        AuctionStatus oldStatus = this.status;
        this.status = s;
        this.changeSupport.firePropertyChange("status", oldStatus, this.status);
    }

    public AuctionItem getItem() {
        return item;
    }

    public Bid getCurrentBid() {
        return currentBid;
    }

    public final boolean isRunning() {
        return thread != null;
    }

    /***
     * change status from not-active to active
     */
    public void start() {
        assert thread == null;
        thread = new Thread(this);
        thread.setDaemon(true);
        thread.start();
    }

    /***
     * if not working return null
     * else return duration
     */
    public long getRemainingTime() {
        return Math.max(0, LocalTime.now().until(endTime, ChronoUnit.SECONDS));
    }

    /**
     * stop the thread and change the status
     */
    public void end() {
        assert thread != null;
        thread = null;
        this.setStatus(AuctionStatus.ENDED);
    }

    public void register(Bidder bidder) {
        //creo un nuovo tipo che vuole puntare (può essere umano o robot)
        //salvo nella lista il bidder
        this.bidders.add(bidder);
    }

    public void bid(Bidder b, double amount) throws InvalidBidException {
        // Ist die Auktion am Laufen?
        if (this.status != AuctionStatus.RUNNING) {
            throw new InvalidBidException();
        }
        // Ist das Gebot mindestens so hoch wie der minimale Preis des Objekts?
        if (this.item.getMinimumPrice() > amount) {
            throw new InvalidBidException();
        }
        // Ist das Gebot höher als das aktuelle Gebot (wenn vorhanden)?
        if (this.currentBid != null && this.currentBid.getAmount() > amount) {
            throw new InvalidBidException();
        }
        // devo salvare l'offerta che ho fatto
        Bid oldBid = this.currentBid;
        this.currentBid = new Bid(b, amount);
        this.changeSupport.firePropertyChange("bid", oldBid, this.currentBid);
        for (Bidder aBidder : this.bidders) {
            aBidder.auctionChanged();
        }
    }

    @Override
    public void run() {
        setStatus(AuctionStatus.RUNNING);
        long oldRemainingTime = this.getRemainingTime();
        while (oldRemainingTime > 0) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            long remainingTime = this.getRemainingTime();
            this.changeSupport.firePropertyChange("remainingTime", oldRemainingTime, remainingTime);
            oldRemainingTime = remainingTime;
        }
        assert oldRemainingTime == 0;
        this.end();
    }

    //il controller dice ti sto ascoltando
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.addPropertyChangeListener(listener);
    }
}
