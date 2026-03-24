import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Reservation implements Serializable {
    private static final long serialVersionUID = 1L;

    private String reservationId;
    private String guestName;
    private String roomType;
    private String assignedRoomId;
    private boolean confirmed;

    public Reservation(String reservationId, String guestName, String roomType, String assignedRoomId, boolean confirmed) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
        this.assignedRoomId = assignedRoomId;
        this.confirmed = confirmed;
    }

    public String getReservationId() {
        return reservationId;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }

    public String getAssignedRoomId() {
        return assignedRoomId;
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public void displayReservation() {
        System.out.println("Reservation ID: " + reservationId);
        System.out.println("Guest Name: " + guestName);
        System.out.println("Room Type: " + roomType);
        System.out.println("Assigned Room ID: " + assignedRoomId);
        System.out.println("Confirmed: " + confirmed);
    }
}

class RoomInventory implements Serializable {
    private static final long serialVersionUID = 1L;

    private Map<String, Integer> availability;

    public RoomInventory() {
        availability = new HashMap<>();
        availability.put("Single Room", 2);
        availability.put("Double Room", 1);
        availability.put("Suite Room", 1);
    }

    public void setAvailability(String roomType, int count) {
        availability.put(roomType, count);
    }

    public int getAvailability(String roomType) {
        return availability.getOrDefault(roomType, 0);
    }

    public Map<String, Integer> getAvailabilityMap() {
        return availability;
    }

    public void displayInventory() {
        System.out.println("Recovered Inventory State:");
        for (Map.Entry<String, Integer> entry : availability.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
    }
}

class BookingHistory implements Serializable {
    private static final long serialVersionUID = 1L;

    private List<Reservation> reservations;

    public BookingHistory() {
        reservations = new ArrayList<>();
    }

    public void addReservation(Reservation reservation) {
        reservations.add(reservation);
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void displayBookingHistory() {
        System.out.println("Recovered Booking History:");
        System.out.println();

        if (reservations.isEmpty()) {
            System.out.println("No bookings found.");
            return;
        }

        for (Reservation reservation : reservations) {
            reservation.displayReservation();
            System.out.println();
        }
    }
}

class SystemState implements Serializable {
    private static final long serialVersionUID = 1L;

    private RoomInventory inventory;
    private BookingHistory bookingHistory;

    public SystemState(RoomInventory inventory, BookingHistory bookingHistory) {
        this.inventory = inventory;
        this.bookingHistory = bookingHistory;
    }

    public RoomInventory getInventory() {
        return inventory;
    }

    public BookingHistory getBookingHistory() {
        return bookingHistory;
    }
}

class PersistenceService {
    private String fileName;

    public PersistenceService(String fileName) {
        this.fileName = fileName;
    }

    public void saveSystemState(SystemState state) {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(fileName));
            outputStream.writeObject(state);
            outputStream.close();
            System.out.println("System state saved successfully.");
        } catch (IOException e) {
            System.out.println("Failed to save system state: " + e.getMessage());
        }
    }

    public SystemState loadSystemState() {
        File file = new File(fileName);

        if (!file.exists()) {
            System.out.println("Persistence file not found. Starting with a new valid system state.");
            return new SystemState(new RoomInventory(), new BookingHistory());
        }

        try {
            ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(file));
            SystemState state = (SystemState) inputStream.readObject();
            inputStream.close();
            System.out.println("System state loaded successfully.");
            return state;
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Failed to load persisted state. Starting with a new valid system state.");
            return new SystemState(new RoomInventory(), new BookingHistory());
        }
    }
}

public class BookMyStayApp {
    public static void main(String[] args) {
        String fileName = "bookmystay_data.ser";
        PersistenceService persistenceService = new PersistenceService(fileName);

        RoomInventory inventory = new RoomInventory();
        inventory.setAvailability("Single Room", 1);
        inventory.setAvailability("Double Room", 0);
        inventory.setAvailability("Suite Room", 1);

        BookingHistory bookingHistory = new BookingHistory();
        bookingHistory.addReservation(new Reservation("R101", "Hari", "Single Room", "SR1", true));
        bookingHistory.addReservation(new Reservation("R102", "Arun", "Double Room", "DR1", true));

        SystemState currentState = new SystemState(inventory, bookingHistory);

        System.out.println("Book My Stay App - UC12 Data Persistence & System Recovery");
        System.out.println();
        System.out.println("Saving current system state...");
        persistenceService.saveSystemState(currentState);

        System.out.println();
        System.out.println("Simulating system restart...");
        System.out.println();

        SystemState recoveredState = persistenceService.loadSystemState();

        recoveredState.getInventory().displayInventory();
        System.out.println();
        recoveredState.getBookingHistory().displayBookingHistory();
    }
}