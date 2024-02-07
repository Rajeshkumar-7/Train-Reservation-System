import Models.Train;
import Service.TrainService;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Train train = new Train();
        TrainService trainService = new TrainService();
        Scanner sc = new Scanner(System.in);

        boolean value = true;
        while(value){
            System.out.println("Print 1 : To Book Tickets \n" +
                    "Print 2 : To see available seats from start to destination \n" +
                    "Print 3 : To cancel Tickets \n" +
                    "Print 4 : To print Booking Summary \n" +
                    "Print 5 : To print cancelled Tickets \n" +
                    "print any other to exit");
            int choice = sc.nextInt();


            switch (choice){
                case 1:
                    /* Book Tickets */
                    System.out.println("Give start , end and numberOfPeople");
                    char start = sc.next().charAt(0);
                    char end = sc.next().charAt(0);
                    int numberOfPeople = sc.nextInt();
                    String response = trainService.bookTicket(start , end , numberOfPeople , train);
                    System.out.println(response);
                    break;
                case 2:
                    // To print available seats
                    System.out.println("Give start and End");
                    char s = sc.next().charAt(0);
                    char e = sc.next().charAt(0);
                    trainService.availableSeats(s , e , train.trainSeats);
                    break;
                case 3:
                    /* To Cancel Tickets */
                    System.out.println("Give Ticket id and noOfPeople");
                    int id = sc.nextInt();
                    int noOfPeopleToCancel = sc.nextInt();
                    String response2 = trainService.cancelTicket(id , noOfPeopleToCancel , train);
                    System.out.println(response2);
                    break;
                case 4:
                    // To Print All the Summary
                    trainService.printSummary(train);
                    break;
                case 5:
                    // To print cancelled tickets
                    trainService.printCancelledTickets(train);
                    break;
                default:
                    System.out.println("Thank you");
                    value = false;
                    break;
            }
        }
    }
}