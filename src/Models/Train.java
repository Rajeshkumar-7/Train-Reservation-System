package Models;

import java.util.HashMap;
import java.util.TreeMap;

public class Train {

    public static int id = 1;

    public HashMap<Character , int[]> trainSeats;

    public HashMap<Character , int[]> waitingListSeats;

    public HashMap<Integer , Ticket> bookedTickets;

    public TreeMap<Integer , Ticket> waitingListTickets;

    public HashMap<Integer , Ticket> cancelledTickets;

    public Train(){

        // Creating 5 stations and 8 Seats
        trainSeats = new HashMap<>();
        for(char c = 'A';c<='E';c++){
            trainSeats.put(c , new int[8]);
        }

        // Creating 2 waiting List tickets for all stations
        waitingListSeats = new HashMap<>();
        for(char c = 'A';c<='E';c++){
            waitingListSeats.put(c , new int[2]);
        }

        // Creating map for storing booked tickets
        bookedTickets = new HashMap<>();

        // Creating map for storing waiting list tickets
        waitingListTickets = new TreeMap<>();

        // Creating map for storing cancelled tickets
        cancelledTickets = new HashMap<>();
    }
}
