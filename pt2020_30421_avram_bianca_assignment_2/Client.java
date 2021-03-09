import java.util.Random;
public class Client implements Comparable {
    private static int newid = 0;
    private int id;
    private int tarrival;
    private int tservice;
    private int waitTime;

    public Client(int tarrivalMax, int tarrivalMin, int tserviceMax, int tserviceMin){
        newid++;
        waitTime = 0;
        id = newid;
        Random r = new Random();
        tarrival = r.nextInt(tarrivalMax - tarrivalMin + 1) + tarrivalMin;
        tservice = r.nextInt(tserviceMax - tserviceMin + 1) + tserviceMin;
    }

    public int getId() {
        return id;
    }

    public int getTarrival() {
        return tarrival;
    }

    public void decrementTservice(){
        tservice--;
    }

    public int getTservice() {
        return tservice;
    }

    public void setWaitTime(int wait){
        waitTime = tservice + wait;
    }

    public int getWaitTime() {
        return waitTime;
    }

    @Override
    public String toString(){
        return "(" + getId() + "," + getTarrival() + "," + getTservice() + ")";
    }

    @Override
    public int compareTo(Object o) {
        return getTarrival();
    }
}
