package auction.catalog;

import auction.bidder.Bidder;
import auction.domain.AuctionStatus;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.time.LocalTime;

//all in one thread
public class Auction implements Runnable {
    private long duration;
    private int sec;
    private LocalTime endTime;
    private AuctionStatus status;
    private AuctionItem item;
    private Thread thread;
    private PropertyChangeSupport changeSupport;

    public Auction(AuctionItem auctionItem, int i) {
        this.item = auctionItem;
        this.changeSupport = new PropertyChangeSupport(this);
    }

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

    public final boolean isRunning() {
        return thread != null;
    }

   /* public Auction(LocalTime endTime, AuctionItem item) {
        this.endTime = endTime;
        this.status = AuctionStatus.CREATED;
        this.item = item;
    }*/

    /***
     * change status from not-active to active
     */
    public void start() {
        if (thread == null) {
            thread = new Thread(this);
            thread.setDaemon(true);
            thread.start();
            status = AuctionStatus.CREATED;
            changeSupport.firePropertyChange("start", "", getRemainingTime());
        }
    }

    /***
     * if not working return null
     * else return duration
     */
    public long getRemainingTime() {
        if (status.equals(AuctionStatus.CREATED)) {
            //change status
            status = AuctionStatus.RUNNING;
            LocalTime remainingTime = LocalTime.now();
            duration = remainingTime.getSecond();
            endTime = remainingTime.plusMinutes(1);
            return duration;
        } else {
            return Long.parseLong(null);
        }

    }

    /**
     * stop the thread and change the status
     */
    public void end() {
        if (thread != null) {
            thread = null;
            status = AuctionStatus.ENDED;
        }
        changeSupport.firePropertyChange("end", "", getRemainingTime());
    }

    public Bidder register(Bidder bidder) {
        //creo un nuovo tipo che vuole puntare(può essere umano o robot)
        return bidder;
    }

    public Bidder bid(Bidder b, double amount) {
        //devo salvare l'offerta che ho fatto

        return b;
    }

    @Override
    public void run() {
        while (thread != null) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                //
            }
            if (thread != null) {
                duration++;
                changeSupport.firePropertyChange("time", "", getRemainingTime());
            }
        }

    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.addPropertyChangeListener(listener);
    }
}
