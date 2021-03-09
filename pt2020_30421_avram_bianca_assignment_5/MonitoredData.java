import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MonitoredData {
    private LocalDateTime start_time;
    private LocalDateTime end_time;
    private String activity_label;

    public MonitoredData(LocalDateTime start, LocalDateTime end, String label){
        start_time = start;
        end_time = end;
        activity_label = label;
    }

    public String getActivity_label() {
        return activity_label;
    }

    public LocalDateTime getEnd_time() {
        return end_time;
    }

    public LocalDateTime getStart_time() {
        return start_time;
    }

    public static void main(String[] args){
        String filename = "Activities.txt";
        //get the lines of the text file
        List<String> readList = readFile(filename);

        //convert lines to the required list
        List<MonitoredData> list = getListData(readList);
        //print to Task_1.txt
        try{
            File task1 = new File("Task_1.txt");
            FileWriter fw1 = new FileWriter(task1);
            PrintWriter pw1 = new PrintWriter(fw1);
            pw1.write("TASK1\n\nThe list of monitored data extracted from file is:\n\n");
            for (MonitoredData data:list) {
                pw1.write(data.toString()+"\n");
            }
            pw1.close();
            fw1.close();
        }
        catch (IOException ex){
            System.out.println("Could not write file for TASK 1");
        }

        //count distinct days
        long distinctDays = getCountDistinctDay(list);
        //printf to Task_2.txt
        try{
            File task2 = new File("Task_2.txt");
            FileWriter fw2 = new FileWriter(task2);
            PrintWriter pw2 = new PrintWriter(fw2);
            pw2.write("TASK 2\n\nThe number of distinct days from logger is: "+distinctDays);
            pw2.close();
            fw2.close();
        }
        catch (IOException ex){
            System.out.println("Could not write file for TASK 2");
        }

        //count occurrence of each activity
        Map<String, Integer> activityOccurrence = countActivitiesOccurrence(list);
        //print to Task_3.txt
        try{
            File task3 = new File("Task_3.txt");
            FileWriter fw3 = new FileWriter(task3);
            PrintWriter pw3 = new PrintWriter(fw3);
            pw3.write("TASK 3\n\nActivity count:\n\n");
            for (Map.Entry entry: activityOccurrence.entrySet()) {
                pw3.write(entry.getKey()+": "+entry.getValue()+"\n");
            }
            pw3.close();
            fw3.close();
        }
        catch (IOException ex){
            System.out.println("Could not write file for TASK 3");
        }

        //Count each activity per day
        Map<Integer,Map<String,Integer>> activitiesPerDay = activityOccurrencePerDay(list);
        //print to Task_4.txt
        try{
            File task4 = new File("Task_4.txt");
            FileWriter fw4 = new FileWriter(task4);
            PrintWriter pw4 = new PrintWriter(fw4);
            pw4.write("TASK 4\n\nActivity count:\n\n");
            for (Map.Entry entry: activitiesPerDay.entrySet()) {
                pw4.write("Day "+entry.getKey()+": \n");
                Map<String,Integer> a = (Map<String, Integer>) entry.getValue();
                for (Map.Entry e:a.entrySet()) {
                    pw4.write(e.getKey()+": "+e.getValue()+"\n");
                }
                pw4.write("\n");
            }
            pw4.close();
            fw4.close();
        }
        catch (IOException ex){
            System.out.println("Could not write file for TASK 4");
        }

        //total duration of each activity
        Map<String,LocalTime> activityDuration = activityTotalDuration(list);
        //print to Task_5.txt
        try{
            File task5 = new File("Task_5.txt");
            FileWriter fw5 = new FileWriter(task5);
            PrintWriter pw5 = new PrintWriter(fw5);
            pw5.write("TASK 5\n\nActivity total duration:\n(days:hours:minutes:seconds)\n\n");
            for (Map.Entry entry: activityDuration.entrySet()) {
                pw5.write(entry.getKey()+": "+entry.getValue().toString()+"\n");
            }
            pw5.close();
            fw5.close();
        }
        catch (IOException ex){
            System.out.println("Could not write file for TASK 5");
        }

        //filter activities with 90% of records less than 5 minutes
        List<String> lessThan5Minutes = activitiesLessThan5minutes(list);
        //print to Task_6.txt
        try{
            File task6 = new File("Task_6.txt");
            FileWriter fw6 = new FileWriter(task6);
            PrintWriter pw6 = new PrintWriter(fw6);
            pw6.write("TASK 6\n\nActivities with 90% of records lasting less than 5 minutes:\n\n");
            for (String str:lessThan5Minutes) {
                pw6.write(str+"\n");
            }
            pw6.close();
            fw6.close();
        }
        catch (IOException ex){
            System.out.println("Could not write file for TASK 6");
        }
    }

    //-------------TASK1-----------------
    public static List<String> readFile(String filename){
        List<String> list = new ArrayList<>();
        try(Stream<String> stream = Files.lines(Paths.get(filename))){
            list = stream.collect(Collectors.toList());
        }
        catch (IOException ex){
            System.out.println(ex.getMessage());
        }
        return list;
    }

    public static List<MonitoredData> getListData(List<String> lines){
        List<MonitoredData> list = new ArrayList<>();
        list = lines.stream().map(MonitoredData::getData).collect(Collectors.toList());
        return list;
    }

    public static MonitoredData getData(String str){
        MonitoredData data;
        String[] pieces = str.split("\\s{2,10}");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime d1 = LocalDateTime.parse(pieces[0],formatter);
        LocalDateTime d2 = LocalDateTime.parse(pieces[1],formatter);
        data = new MonitoredData(d1,d2,pieces[2]);
        return data;
    }
    //--------------END OF TASK 1-------------------

    //-------------------TASK 2---------------------
    public static int getDistinctDay(LocalDateTime date){
        int month = date.getMonthValue();
        int dayOfMonth = date.getDayOfMonth();
        return dayOfMonth + month * 31;
    }

    public static long getCountDistinctDay(List<MonitoredData> list){
        List<Integer> endDays = list.stream().map((MonitoredData p)->p.getDistinctDay(p.getEnd_time())).collect(Collectors.toList());
        List<Integer> startDays = list.stream().map((MonitoredData p)->p.getDistinctDay(p.getStart_time())).collect(Collectors.toList());
        List<Integer> merged = Stream.concat(endDays.stream(),startDays.stream()).collect(Collectors.toList());
        return merged.stream().distinct().count();
    }
    //-------------END OF TASK 2----------------

    //------------------TASK 3------------------
    public static Map<String,Integer> countActivitiesOccurrence(List<MonitoredData> list){
        Map<String,Integer> map = new HashMap<>();
        List<String> activities = list.stream().map((MonitoredData p)->p.getActivity_label()).collect(Collectors.toList());
        for (String str:activities) {
            long occ = list.stream().filter(p->p.getActivity_label().equals(str)).count();
            map.put(str, (int) occ);
        }
        return map;
    }
    //---------------END OF TASK 3--------------

    //-----------------TASK 4-------------------
    public static Map<Integer,Map<String,Integer>> activityOccurrencePerDay(List<MonitoredData> list){
        Map<Integer,Map<String,Integer>> map = new HashMap<>();
        //first integer is the number of the day in the monitored period
        int dayNo=1;
        List<Integer> endDays = list.stream().map((MonitoredData p)->p.getDistinctDay(p.getEnd_time())).collect(Collectors.toList());
        List<Integer> startDays = list.stream().map((MonitoredData p)->p.getDistinctDay(p.getStart_time())).collect(Collectors.toList());
        List<Integer> merged = Stream.concat(startDays.stream(),endDays.stream()).distinct().collect(Collectors.toList());
        List<String> activities = list.stream().map((MonitoredData p)->p.getActivity_label()).collect(Collectors.toList());
        for (Integer i:merged) {
            Map<String,Integer> activityCount = new HashMap<>();
            //System.out.println("*********DAY "+dayNo+" ***********");
            for (String str:activities){
                long count = list.stream()
                        .filter(p->p.getActivity_label().equals(str) && getDistinctDay(p.getStart_time())==i)
                        .count();
                activityCount.put(str, (int) count);
            }
            for (Map.Entry<String,Integer> entry:activityCount.entrySet()) {
                //System.out.println(entry.getKey()+": "+entry.getValue());
            }
            //System.out.println("***************************************");
            map.put(dayNo,activityCount);
            dayNo++;
        }
        return map;
    }
    //----------------END OF TASK 4-----------------

    //-------------------TASK 5---------------------

    public static long activityDurationSeconds(LocalDateTime from, LocalDateTime to){
        long d = Duration.between(from,to).toSeconds();
        return d;
    }
    public static LocalTime activityDurationLocalTime(long duration){
        int minutes = (int) (duration/60);
        int seconds = (int) (duration-minutes*60);
        int hours = minutes/60;
        minutes -= hours*60;
        int days = hours/24;
        hours -= days*24;
        LocalTime durationAsTime = new LocalTime(days,hours,minutes,seconds);
        return durationAsTime;
    }

    public static Map<String,LocalTime> activityTotalDuration(List<MonitoredData> list){
        Map<String,LocalTime> out = new HashMap<>();
        List<String> activities = list.stream().map((MonitoredData p)->p.getActivity_label()).collect(Collectors.toList());
        for (String str:activities) {
            long durations = list.stream()
                    .filter(p->p.getActivity_label().equals(str))
                    .map(p->activityDurationSeconds(p.getStart_time(),p.getEnd_time()))
                    .reduce((long) 0,(e1, e2)->e1+e2);
            LocalTime duration = activityDurationLocalTime(durations);
            out.put(str,duration);
        }
        return out;
    }
    //----------------END OF TASK 5-----------------

    //-------------------TASK 6---------------------
    public static int durationMinutes(LocalDateTime from, LocalDateTime to){
        long seconds = activityDurationSeconds(from,to);
        return (int) (seconds/60);
    }

    public static boolean isGood(String str,List<MonitoredData> list){
        List<Integer> durations = list.stream()
                .filter(p->p.getActivity_label().equals(str))
                .map(p->durationMinutes(p.getStart_time(),p.getEnd_time()))
                .collect(Collectors.toList()); //this list contains all the durations in minutes for activity str
        int count = durations.size();
        long lessThan5 = durations.stream().filter(p->p<=5).count();
        float percent = ((((float) lessThan5)/count)*100);
        //System.out.println(str+": total="+count+", lessThan5="+lessThan5+", percent="+percent);
        if(percent > 90)
            return true;
        return false;
    }

    public static List<String> activitiesLessThan5minutes(List<MonitoredData> list){
        List<String> activities = list.stream()
                .map(p->p.getActivity_label())
                .distinct()
                .filter(p->isGood(p,list) == true)
                .collect(Collectors.toList());
        return activities;
    }

    @Override
    public String toString(){
        String str = start_time.toString().replace('T',' ');
        if(start_time.toString().length() < 19)
            str += ":00";
        str += "    "+end_time.toString().replace('T',' ');
        if(end_time.toString().length() < 19)
            str += ":00";
        str += "    "+activity_label;
        return str;
    }
}
