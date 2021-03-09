import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class Queue implements Runnable{
    private int qNumber;
    private static int count = 0;
    public static int clientsServed = 0;
    private AtomicInteger waitTime;
    private AtomicBoolean isOccupied;
    private BlockingQueue<Client> clients;

    public Queue(){
        count++;
        qNumber = count;
        waitTime = new AtomicInteger(0);
        clients = new LinkedBlockingDeque<Client>();
        isOccupied = new AtomicBoolean(false);
    }
    public boolean isOccupied(){
        return isOccupied.get();
    }
    public int getWaitTime() {
        return waitTime.intValue();
    }

    public int getqNumber() {
        return qNumber;
    }

    public void setIsOccupied(boolean b){
        this.isOccupied.set(b);
    }

    public void addClient(Client c){
        clients.add(c);
        waitTime = new AtomicInteger(waitTime.intValue() + c.getTservice());
        setIsOccupied(true);
    }

    public void removeClient(Client c){
        clients.remove(c);
    }

    @Override
    public String toString(){
        String str = "Queue " + qNumber + ": ";
        if(waitTime.intValue() != 0){
            for (Client c : clients) {
                str += c.toString() + "; ";
            }
        }
        else{
            str += "closed";
        }
        return str;
    }

    public void updateWaitTime(){
        for (Client c : clients) {
            c.setWaitTime(c.getWaitTime() - 1);
        }
        this.clients.peek().decrementTservice();
        this.waitTime.set(this.waitTime.intValue() - 1);
    }

    @Override
    public void run() {
        while(isOccupied() == true){
            Client c = clients.peek();
            int t = c.getTservice();
            try{
                for(int i = 0 ; i < t ; i++){
                    synchronized (this){
                        wait(1000);
                    }
                    updateWaitTime();
                }
                removeClient(c);
                clientsServed++;
                if(waitTime.intValue() == 0){
                    setIsOccupied(false);
                }
            }
            catch (InterruptedException ex){
                System.out.println("Queue thread interrupted");
            }
        }
    }
}
