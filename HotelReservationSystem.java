import java.util.*;
import java.text.SimpleDateFormat;

// Room class
class Room {
    private int roomNumber;
    private String category;
    private double price;
    private boolean isAvailable;

    public Room(int roomNumber, String category, double price) {
        this.roomNumber = roomNumber;
        this.category = category;
        this.price = price;
        this.isAvailable = true;
    }

    public int getRoomNumber() { return roomNumber; }
    public String getCategory() { return category; }
    public double getPrice() { return price; }
    public boolean isAvailable() { return isAvailable; }

    public boolean book() {
        if (!isAvailable) return false;
        isAvailable = false;
        return true;
    }

    public void cancel() {
        isAvailable = true;
    }
}

// Booking class
class Booking {
    private User user;
    private Room room;
    private Date checkIn;
    private Date checkOut;
    private boolean paid;

    public Booking(User user, Room room, Date checkIn, Date checkOut) {
        this.user = user;
        this.room = room;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.paid = false;
    }

    public Room getRoom() { return room; }
    public Date getCheckIn() { return checkIn; }
    public Date getCheckOut() { return checkOut; }
    public boolean isPaid() { return paid; }

    public void makePayment() {
        paid = true;
        System.out.println("Payment successful for Room " + room.getRoomNumber());
    }
}

// User class
class User {
    private String name;
    private String email;
    private List<Booking> bookings;

    public User(String name, String email) {
        this.name = name;
        this.email = email;
        this.bookings = new ArrayList<>();
    }

    public void addBooking(Booking booking) {
        bookings.add(booking);
    }

    public void removeBooking(Booking booking) {
        bookings.remove(booking);
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    public void viewBookings() {
        if (bookings.isEmpty()) {
            System.out.println("No bookings yet.");
            return;
        }
        System.out.println("\n--- Your Bookings ---");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for (Booking b : bookings) {
            System.out.println("Room " + b.getRoom().getRoomNumber() + " | " + b.getRoom().getCategory() +
                    " | " + sdf.format(b.getCheckIn()) + " to " + sdf.format(b.getCheckOut()) +
                    " | Paid: " + (b.isPaid() ? "Yes" : "No"));
        }
    }
}

// Hotel class
class Hotel {
    private List<Room> rooms;

    public Hotel() {
        rooms = new ArrayList<>();
        rooms.add(new Room(101, "Standard", 100));
        rooms.add(new Room(102, "Standard", 100));
        rooms.add(new Room(201, "Deluxe", 200));
        rooms.add(new Room(202, "Deluxe", 200));
        rooms.add(new Room(301, "Suite", 400));
    }

    public void showAvailableRooms(String category) {
        System.out.println("\n--- Available " + category + " Rooms ---");
        boolean anyAvailable = false;
        for (Room r : rooms) {
            if (r.getCategory().equalsIgnoreCase(category) && r.isAvailable()) {
                System.out.println("Room " + r.getRoomNumber() + " | Price: $" + r.getPrice());
                anyAvailable = true;
            }
        }
        if (!anyAvailable) System.out.println("No rooms available.");
    }

    public Room getRoomByNumber(int number) {
        for (Room r : rooms) {
            if (r.getRoomNumber() == number) return r;
        }
        return null;
    }
}

// Main class
public class HotelReservationSystem {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Hotel hotel = new Hotel();

        System.out.print("Enter your name: ");
        String name = sc.nextLine();
        System.out.print("Enter your email: ");
        String email = sc.nextLine();
        User user = new User(name, email);

        boolean running = true;
        while (running) {
            System.out.println("\n--- Menu ---");
            System.out.println("1. Search Rooms");
            System.out.println("2. Book Room");
            System.out.println("3. Cancel Booking");
            System.out.println("4. View My Bookings");
            System.out.println("0. Exit");
            System.out.print("Choose an option: ");
            int choice = sc.nextInt();
            sc.nextLine(); // consume newline

            switch (choice) {
                case 1 -> {
                    System.out.print("Enter room category (Standard/Deluxe/Suite): ");
                    String category = sc.nextLine();
                    hotel.showAvailableRooms(category);
                }
                case 2 -> {
                    System.out.print("Enter room number to book: ");
                    int roomNum = sc.nextInt();
                    sc.nextLine();
                    Room room = hotel.getRoomByNumber(roomNum);
                    if (room == null) {
                        System.out.println("Invalid room number.");
                        break;
                    }
                    if (!room.isAvailable()) {
                        System.out.println("Room not available.");
                        break;
                    }
                    System.out.print("Enter check-in date (yyyy-MM-dd): ");
                    Date checkIn = sdf.parse(sc.nextLine());
                    System.out.print("Enter check-out date (yyyy-MM-dd): ");
                    Date checkOut = sdf.parse(sc.nextLine());

                    room.book();
                    Booking booking = new Booking(user, room, checkIn, checkOut);
                    booking.makePayment();
                    user.addBooking(booking);
                    System.out.println("Room " + roomNum + " booked successfully!");
                }
                case 3 -> {
                    System.out.print("Enter room number to cancel: ");
                    int roomNum = sc.nextInt();
                    Room room = hotel.getRoomByNumber(roomNum);
                    if (room == null) {
                        System.out.println("Invalid room number.");
                        break;
                    }
                    Booking toRemove = null;
                    for (Booking b : user.getBookings()) {
                        if (b.getRoom().getRoomNumber() == roomNum) {
                            toRemove = b;
                            break;
                        }
                    }
                    if (toRemove != null) {
                        toRemove.getRoom().cancel();
                        user.removeBooking(toRemove);
                        System.out.println("Booking cancelled for Room " + roomNum);
                    } else {
                        System.out.println("No booking found for this room.");
                    }
                }
                case 4 -> user.viewBookings();
                case 0 -> running = false;
                default -> System.out.println("Invalid option!");
            }
        }
        sc.close();
        System.out.println("Exiting. Goodbye!");
    }
}
