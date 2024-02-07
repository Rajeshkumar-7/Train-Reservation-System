package Models;

public class Ticket {

    private int id;

    private int numberOfPeople;

    private int numberOfPeopleCancelled;

    private char start;

    private char end;

    private boolean isInWaitingList;

    public Ticket(int id, int numberOfPeople, char start, char end, boolean isInWaitingList) {
        this.id = id;
        this.numberOfPeople = numberOfPeople;
        this.start = start;
        this.end = end;
        this.isInWaitingList = isInWaitingList;
        this.numberOfPeopleCancelled = 0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumberOfPeople() {
        return numberOfPeople;
    }

    public void setNumberOfPeople(int numberOfPeople) {
        this.numberOfPeople = numberOfPeople;
    }

    public char getStart() {
        return start;
    }

    public void setStart(char start) {
        this.start = start;
    }

    public char getEnd() {
        return end;
    }

    public void setEnd(char end) {
        this.end = end;
    }

    public boolean isInWaitingList() {
        return isInWaitingList;
    }

    public void setInWaitingList(boolean inWaitingList) {
        isInWaitingList = inWaitingList;
    }

    public int getNumberOfPeopleCancelled() {
        return numberOfPeopleCancelled;
    }

    public void setNumberOfPeopleCancelled(int numberOfPeopleCancelled) {
        this.numberOfPeopleCancelled = numberOfPeopleCancelled;
    }
}
