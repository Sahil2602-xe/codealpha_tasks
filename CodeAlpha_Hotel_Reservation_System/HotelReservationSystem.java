import java.io.*;
import java.util.*;

// Room class
class Room {
    int roomNumber;
    String category;
    boolean isBooked;

    Room(int roomNumber, String category) {
        this.roomNumber = roomNumber;
        this.category = category;
        this.isBooked = false;
    }
}

// Booking class
class Booking {
    int roomNumber;
    String customerName;

    Booking(int roomNumber, String customerName) {
        this.roomNumber = roomNumber;
        this.customerName = customerName;
    }
}

public class HotelReservationSystem {

    static ArrayList<Room> rooms = new ArrayList<>();
    static ArrayList<Booking> bookings = new ArrayList<>();

    public static void main(String[] args) {

        // Initialize rooms
        rooms.add(new Room(101, "Standard"));
        rooms.add(new Room(102, "Deluxe"));
        rooms.add(new Room(103, "Suite"));
        rooms.add(new Room(104, "Standard"));

        Scanner sc = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n===== HOTEL RESERVATION SYSTEM =====");
            System.out.println("1. View Available Rooms");
            System.out.println("2. Book Room");
            System.out.println("3. Cancel Booking");
            System.out.println("4. View Bookings");
            System.out.println("5. Save Bookings");
            System.out.println("6. Exit");
            System.out.print("Enter choice: ");

            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1 -> viewRooms();
                case 2 -> bookRoom(sc);
                case 3 -> cancelBooking(sc);
                case 4 -> viewBookings();
                case 5 -> saveBookings();
            }

        } while (choice != 6);

        sc.close();
    }

    static void viewRooms() {
        System.out.println("\n--- Available Rooms ---");
        for (Room r : rooms) {
            if (!r.isBooked) {
                System.out.println("Room " + r.roomNumber + " (" + r.category + ")");
            }
        }
    }

    static void bookRoom(Scanner sc) {
        System.out.print("Enter room number: ");
        int num = sc.nextInt();
        sc.nextLine();

        for (Room r : rooms) {
            if (r.roomNumber == num && !r.isBooked) {

                System.out.print("Enter customer name: ");
                String name = sc.nextLine();

                // Payment simulation
                System.out.println("Processing payment...");
                System.out.println("Payment successful!");

                r.isBooked = true;
                bookings.add(new Booking(num, name));

                System.out.println("Room booked successfully!");
                return;
            }
        }

        System.out.println("Room not available.");
    }

    static void cancelBooking(Scanner sc) {
        System.out.print("Enter room number: ");
        int num = sc.nextInt();

        Iterator<Booking> iterator = bookings.iterator();

        while (iterator.hasNext()) {
            Booking b = iterator.next();
            if (b.roomNumber == num) {
                iterator.remove();

                for (Room r : rooms) {
                    if (r.roomNumber == num) r.isBooked = false;
                }

                System.out.println("Booking cancelled.");
                return;
            }
        }

        System.out.println("Booking not found.");
    }

    static void viewBookings() {
        System.out.println("\n--- Booking Details ---");
        for (Booking b : bookings) {
            System.out.println("Room " + b.roomNumber + " booked by " + b.customerName);
        }
    }

    static void saveBookings() {
        try {
            PrintWriter writer = new PrintWriter("bookings.txt");

            for (Booking b : bookings) {
                writer.println(b.roomNumber + "," + b.customerName);
            }

            writer.close();
            System.out.println("Bookings saved to file!");

        } catch (Exception e) {
            System.out.println("Error saving file.");
        }
    }
}