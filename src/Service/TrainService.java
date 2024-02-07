package Service;

import Models.Ticket;
import Models.Train;

import java.util.HashMap;
import java.util.Scanner;
import java.util.Stack;
import java.util.TreeMap;

public class TrainService {

    public String bookTicket(char start, char end, int numberOfPeople , Train train) {

        HashMap<Character , int[]> trainSeats = train.trainSeats;
        HashMap<Character , int[]> waitingListSeats = train.waitingListSeats;

        // Check ticket is available in train
        boolean isSeatAvailable = utility.checkTicketAvailability(start , end , numberOfPeople , trainSeats);
        if(!isSeatAvailable){
            // If seats are not available in train check in the waiting List
            if(numberOfPeople > 2) return "Sorry !!! Tickets are not available ";
            boolean isWaitingListAvailable = utility.checkTicketAvailability(start , end , numberOfPeople , waitingListSeats);
            if(!isWaitingListAvailable) return "Sorry !!! Tickets are not available ";

            // Now book the tickets in waiting List
            Ticket ticket = new Ticket(Train.id++ , numberOfPeople , start , end , true);

            // Update the tickets in waiting List
            utility.updateTrainSeats(start ,end , numberOfPeople , 0 , ticket.getId() , waitingListSeats);

            // Store the ticket in respective map
            train.waitingListTickets.put(ticket.getId(), ticket);

            return "Tickets are not available on the Train. So we have booked a ticket in waiting list.";
        }

        // Tickets are available so now book the tickets
        Ticket ticket = new Ticket(Train.id++ , numberOfPeople , start , end , false);

        // Update in trainSeats
        utility.updateTrainSeats(start , end , numberOfPeople , 0 , ticket.getId() , trainSeats);

        // Store the tickets in respective maps
        train.bookedTickets.put(ticket.getId() , ticket);

        return "Tickets has been booked";
    }

    public String cancelTicket(int id, int noOfPeopleToCancel, Train train) {

        HashMap<Character , int[]> trainSeats = train.trainSeats;
        HashMap<Character , int[]> waitingListSeats = train.waitingListSeats;
        HashMap<Integer , Ticket> bookedTickets = train.bookedTickets;
        TreeMap<Integer , Ticket> waitingListTickets = train.waitingListTickets;
        HashMap<Integer , Ticket> cancelledTickets = train.cancelledTickets;

        // Check if we have the valid id
        if(!bookedTickets.containsKey(id) && !waitingListTickets.containsKey(id)){
            return "Invalid ID";
        }

        // Check if the ticket is in waiting list
        if(waitingListTickets.containsKey(id)){
            // Get the ticket from waiting list tickets
            Ticket ticket = waitingListTickets.get(id);

            // Update the seats in waiting list
            utility.updateTrainSeats(ticket.getStart() , ticket.getEnd() , noOfPeopleToCancel , ticket.getId() , 0 , waitingListSeats);

            // Update the cancelled number of people in ticket
            ticket.setNumberOfPeopleCancelled(ticket.getNumberOfPeopleCancelled() + noOfPeopleToCancel);

            // if all the seats are cancelled in ticket move the ticket to cancelled tickets
            if(ticket.getNumberOfPeople() == ticket.getNumberOfPeopleCancelled()){
                waitingListTickets.remove(ticket.getId());
                cancelledTickets.put(ticket.getId() , ticket);
                return "Ticket has been cancelled";
            }

            // Check if we can update any tickets from waiting list to booked
            utility.updateWaitingListToBooked(train);

            return "Ticket has been cancelled";
        }

        // Get the ticket
        Ticket ticket = bookedTickets.get(id);
        char start = ticket.getStart();
        char end = ticket.getEnd();

        // Update the train seats
        utility.updateTrainSeats(start , end , noOfPeopleToCancel , ticket.getId() , 0 , trainSeats);

        // Update the ticket
        ticket.setNumberOfPeopleCancelled( ticket.getNumberOfPeopleCancelled() + noOfPeopleToCancel);
        if(ticket.getNumberOfPeople() == ticket.getNumberOfPeopleCancelled()){
            // All the people have cancelled means remove
            // from the booked ticket and add it to cancelled tickets
            bookedTickets.remove(ticket.getId());
            cancelledTickets.put(ticket.getId() , ticket);
        }

        // Check if we can update any tickets from waiting list to booked
        utility.updateWaitingListToBooked(train);

        return "Ticket has been cancelled";
    }

    public void printSummary(Train train){

        HashMap<Character , int[]> trainSeats = train.trainSeats;
        HashMap<Character , int[]> waitingListSeats = train.waitingListSeats;

        // Printing the TrainSeats
        for(char c = 'A';c<='E';c++){
            int[] arr = trainSeats.get(c);
            System.out.print("  " + c + "  :  ");
            for(int i=0;i<8;i++){
                System.out.print(arr[i] + "  ");
            }
            System.out.println();
        }

        // printing the waiting list seats
        System.out.println("Waiting List");
        for(char c = 'A';c<='E';c++){
            int[] arr = waitingListSeats.get(c);
            System.out.print("  " + c + "  :  ");
            for(int i=0;i<2;i++){
                System.out.print(arr[i] + "  ");
            }
            System.out.println();
        }
    }

    public void availableSeats(char s, char e, HashMap<Character,int[]> trainSeats) {

        for(char c=s;c<=e;c++){
            int[] arr = trainSeats.get(c);
            int count = 0;
            for(int i=0;i<arr.length;i++){
                if(arr[i] == 0) count++;
            }
            System.out.println("Station " + c +" : " + count + " seats available");
        }
    }

    public void printCancelledTickets(Train train) {

        // Get all the Hashmap where tickets are stored
        HashMap<Integer , Ticket> bookedTickets = train.bookedTickets;
        TreeMap<Integer , Ticket> waitingListTickets = train.waitingListTickets;
        HashMap<Integer , Ticket> cancelledTickets = train.cancelledTickets;

        System.out.println("Fully Cancelled tickets");
        // Print fully cancelled tickets
        for(int id : cancelledTickets.keySet()){
            Ticket ticket = cancelledTickets.get(id);
            System.out.println("Ticket ID : " + ticket.getId() +" , Cancelled Seats : " + ticket.getNumberOfPeopleCancelled());
        }

        System.out.println("Partially cancelled tickets");
        // Print Partially cancelled tickets from booked tickets
        for(int id : bookedTickets.keySet()){
            Ticket ticket = bookedTickets.get(id);
            if(ticket.getNumberOfPeopleCancelled() > 0){
                System.out.println("Ticket ID : " + ticket.getId() +" , Cancelled Seats : " + ticket.getNumberOfPeopleCancelled());
            }
        }

        // Print partially cancelled tickets from waiting list tickets
        for(int id : waitingListTickets.keySet()){
            Ticket ticket = waitingListTickets.get(id);
            if(ticket.getNumberOfPeopleCancelled() > 0){
                System.out.println("Ticket ID : " + ticket.getId() +" , Cancelled Seats : " + ticket.getNumberOfPeopleCancelled());
            }
        }
    }
}
