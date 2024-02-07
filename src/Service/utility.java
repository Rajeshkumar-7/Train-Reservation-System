package Service;

import Models.Ticket;
import Models.Train;

import java.util.HashMap;
import java.util.Stack;
import java.util.TreeMap;

public class utility {

    public static boolean checkTicketAvailability(char s , char e , int n , HashMap<Character , int[]> trainSeats){

        for(char c = s;c<e;c++){
            int count = 0;
            int[] arr = trainSeats.get(c);
            for(int i=0;i<arr.length;i++){
                if(arr[i] == 0) count++;
            }
            if(count < n) return false;
        }

        return true;
    }

    public static void updateTrainSeats(char s , char e , int n , int x , int id , HashMap<Character , int[]> trainSeats){

        // n --> No of people
        // X --> Value to be Updated
        for(char c = s;c<e;c++){
            int count = 0;
            int[] arr = trainSeats.get(c);
            for(int i=0;i<arr.length;i++){
                if(arr[i] == x){
                    arr[i] = id;
                    count++;
                }
                if(count == n) break;
            }
        }
    }

    public static void updateWaitingListToBooked(Train train){

        HashMap<Character , int[]> trainSeats = train.trainSeats;
        HashMap<Character , int[]> waitingListSeats = train.waitingListSeats;
        HashMap<Integer , Ticket> bookedTickets = train.bookedTickets;
        TreeMap<Integer , Ticket> waitingListTickets = train.waitingListTickets;
        HashMap<Integer , Ticket> cancelledTickets = train.cancelledTickets;

        // Stack will store the ticket numbers from the waiting list which is updated to booked list
        Stack<Integer> tickets = new Stack<>();

        // Iterate all the waiting list tickets in a sorted manner
        for(int ticketNumber : waitingListTickets.keySet()){

            // Current Waiting list ticket
            Ticket waitingListTicket = waitingListTickets.get(ticketNumber);

            // Check if the waiting list ticket can be booked in train
            boolean isSeatAvailable = utility.checkTicketAvailability(waitingListTicket.getStart() , waitingListTicket.getEnd(),
                    waitingListTicket.getNumberOfPeople() - waitingListTicket.getNumberOfPeopleCancelled(), trainSeats);

            // If The Seats are available than update the waiting list to booked
            if(isSeatAvailable){
                // Update the train seats
                utility.updateTrainSeats(waitingListTicket.getStart() , waitingListTicket.getEnd() , waitingListTicket.getNumberOfPeople() , 0 , ticketNumber , trainSeats);

                // Update the waiting list seats
                utility.updateTrainSeats(waitingListTicket.getStart() , waitingListTicket.getEnd() , waitingListTicket.getNumberOfPeople() , ticketNumber , 0 , waitingListSeats);

                // Add the ticket number to stack and ticket to booked tickets
                tickets.push(ticketNumber);
                bookedTickets.put(ticketNumber , waitingListTicket);
            }
        }

        // Remove the tickets from waiting list
        while(!tickets.isEmpty()){
            waitingListTickets.remove(tickets.pop());
        }
    }
}
