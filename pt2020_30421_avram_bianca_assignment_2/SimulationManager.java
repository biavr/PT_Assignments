import java.io.*;
import java.util.*;

public class SimulationManager implements Runnable{
    public int n;
    public int q;
    public int tsimulation;
    public int tarrivalMin;
    public int tarrivalMax;
    public int tserviceMin;
    public int tserviceMax;
    public List<Client> clients = new ArrayList<Client>();
    public List<Queue> queues = new ArrayList<Queue>();
    public int totalWaitingTime;
    public int totalClientsServed;
    public String outfile = new String();

    public static void generateClientsList(int n, List<Client> clients, int tarrivalMax, int tarrivalMin, int tserviceMax, int tserviceMin){
        for (int i = 0 ; i < n ; i++){
            Client c = new Client(tarrivalMax, tarrivalMin,tserviceMax,tserviceMin);
            clients.add(c);
        }
    }

    public static void generateQueuesList(int q, List<Queue> queues){
        for(int i = 0 ; i < q ; i ++){
            Queue qu = new Queue();
            queues.add(qu);
        }
    }

    public Queue getShortestQueue(){
        int min = 2000000;
        int number = 0;
        for (Queue qu : queues) {
            if(qu.getWaitTime() < min){
                min = qu.getWaitTime();
                number = qu.getqNumber();
            }
        }
        for (Queue qu : queues) {
            if(qu.getqNumber() == number){
                return qu;
            }
        }
        return null;
    }

    public static void main(String[] args){
        try{
            SimulationManager simulator = new SimulationManager();
            File in = new File(args[0]);
            BufferedReader br = new BufferedReader(new FileReader(in));
            simulator.n = Integer.parseInt(br.readLine());
            simulator.q = Integer.parseInt(br.readLine());
            simulator.tsimulation = Integer.parseInt(br.readLine());
            String line = br.readLine();
            String[] two = line.split(",");
            simulator.tarrivalMin = Integer.parseInt(two[0]);
            simulator.tarrivalMax = Integer.parseInt(two[1]);
            line = br.readLine();
            br.close();
            two = line.split(",");
            simulator.tserviceMin = Integer.parseInt(two[0]);
            simulator.tserviceMax = Integer.parseInt(two[1]);
            generateClientsList(simulator.n,simulator.clients,simulator.tarrivalMax,simulator.tarrivalMin,simulator.tserviceMax,simulator.tserviceMin);
            generateQueuesList(simulator.q,simulator.queues);
            simulator.totalWaitingTime = 0;
            simulator.totalClientsServed = 0;
            simulator.outfile = args[1];
            Thread t = new Thread(simulator);
            t.start();
        }
        catch (FileNotFoundException ex){
            System.out.println("File not found!");
        }
        catch (IOException ex){
            System.out.println("Could not read from file!");
        }
    }

    public void distributeClientsToQueues(int currentTime, int simulatioinTime){
        Iterator it = clients.iterator();
        while(it.hasNext()){
            Client c = (Client)it.next();
            if(c.getTarrival() == currentTime){
                Queue q = getShortestQueue();
                c.setWaitTime(q.getWaitTime());
                if(c.getWaitTime() + c.getTservice() <= simulatioinTime) {
                    totalWaitingTime += c.getWaitTime();
                }
                if(q.isOccupied() == false) {
                    q.setIsOccupied(true);
                    Thread t = new Thread(q);
                    t.start();
                }
                q.addClient(c);
                it.remove();
            }
        }
    }

    public String waitingClientsString(){
        if(clients.size() == 0)
            return "none";
        String str = new String();
        for (Client c : clients) {
            str += c.toString() + ";";
        }
        return str;
    }

    @Override
    public void run() {
        try {
            PrintWriter pr = new PrintWriter(outfile);
            int currentTime = 0;
            while (currentTime < tsimulation) {
                boolean alldone = true;
                pr.println("Time " + currentTime);
                distributeClientsToQueues(currentTime,tsimulation);
                pr.print("Waiting clients: ");
                pr.println(waitingClientsString());
                for (Queue q : queues) {
                    if (q.getWaitTime() != 0) {
                        alldone = false;
                    }
                    if (clients.size() != 0) {
                        alldone = false;
                    }
                    pr.println(q.toString());
                }
                if (alldone == true && clients.size() == 0) {
                    break;
                }
                currentTime++;
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    System.out.println("Simulation interrupted at time " + currentTime);
                }
            }
            for (Queue q : queues) {
                q.setIsOccupied(false);
            }
            pr.println("");
            pr.println("Average waiting time: " + (double) this.totalWaitingTime / Queue.clientsServed);
            pr.close();
        }
        catch (IOException ex){
            System.out.println("Could not open output file");
        }
    }
}
