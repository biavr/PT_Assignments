public class LocalTime {
    private int days;
    private int hours;
    private int minutes;
    private int seconds;

    public LocalTime(int days,int hours,int minutes,int seconds){
        this.days=days;
        this.hours=hours;
        this.minutes=minutes;
        this.seconds=seconds;
    }

    @Override
    public String toString(){
        return days+":"+hours+":"+minutes+":"+seconds;
    }
}
