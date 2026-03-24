import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

class Reservation {
    private String reservationId;
    private String guestName;
    private String roomType;
    private String assignedRoomId;
    private boolean confirmed;
    private boolean cancelled;

    public Reservation(String reservationId, String guestName, String roomType, String assignedRoomId, boolean confirmed) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
        this.assignedRoomId = assignedRoomId;
        this.confirmed = confirmed;
        this.cancelled = false;
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

    public boolean isCancelled() {
        return cancelled;
    }

    public void cancelReservation() {
        this.cancelled = true;
        this.confirmed = false;
    }

    public void displayReservation() {
        System.out.println("Reservation ID: " + reservationId);
        System.out.println("Guest Name: " + guestName);
        System.out.println("Room Type: " + roomType);
        System.out.println("Assigned Room ID: " + assignedRoomId);
        System.out.println("Confirmed: " + confirmed);
        System.out.println("Cancelled: " + cancelled);
    }
}

class RoomInventory {
    private Map<String, Integer> availability;

    public RoomInventory() {
        availability = new HashMap<>();
        availability.put("Single Room", 0);
        availability.put("Double Room", 1);
        availability.put("Suite Room", 1);
    }

    public void incrementAvailability(String roomType) {
        int current = availability.getOrDefault(roomType, 0);
        availability.put(roomType, current + 1);
    }

    public void displayInventory() {
        System.out.println("Current Inventory:");
        for (Map.Entry<String, Integer> entry : availability.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
    }
}

class BookingHistory {
    private Map<String, Reservation> reservations;

    public BookingHistory() {
        reservations = new HashMap<>();
    }

    public void addReservation(Reservation reservation) {
        reservations.put(reservation.getReservationId(), reservation);
    }

    public Reservation getReservation(String reservationId) {
        return reservations.get(reservationId);
    }

    public void displayHistory() {
        System.out.println("Booking History:");
        for (Reservation reservation : reservations.values()) {
            reservation.displayReservation();
            System.out.println();
        }
    }
}

class CancellationService {
    private RoomInventory inventory;
    private BookingHistory bookingHistory;
    private Stack<String> rollbackStack;

    public CancellationService(RoomInventory inventory, BookingHistory bookingHistory) {
        this.inventory = inventory;
        this.bookingHistory = bookingHistory;
        this.rollbackStack = new Stack<>();
    }

    public void cancelBooking(String reservationId) {
        Reservation reservation = bookingHistory.getReservation(reservationId);

        if (reservation == null) {
            System.out.println("Cancellation failed: Reservation ID " + reservationId + " does not exist.");
            return;
        }

        if (!reservation.isConfirmed()) {
            System.out.println("Cancellation failed: Reservation ID " + reservationId + " is not an active confirmed booking.");
            return;
        }

        if (reservation.isCancelled()) {
            System.out.println("Cancellation failed: Reservation ID " + reservationId + " has already been cancelled.");
            return;
        }

        rollbackStack.push(reservation.getAssignedRoomId());
        inventory.incrementAvailability(reservation.getRoomType());
        reservation.cancelReservation();

        System.out.println("Cancellation successful for Reservation ID: " + reservationId);
        System.out.println("Released Room ID: " + rollbackStack.peek());
    }

    public void displayRollbackStack() {
        System.out.println("Rollback Stack: " + rollbackStack);
    }
}

public class BookMyStayApp {
    public static void main(String[] args) {
        RoomInventory inventory = new RoomInventory();
        BookingHistory bookingHistory = new BookingHistory();
        CancellationService cancellationService = new CancellationService(inventory, bookingHistory);

        Reservation reservation1 = new Reservation("R101", "Hari", "Single Room", "SR1", true);
        Reservation reservation2 = new Reservation("R102", "Arun", "Double Room", "DR1", true);
        Reservation reservation3 = new Reservation("R103", "Priya", "Suite Room", "SU1", false);

        bookingHistory.addReservation(reservation1);
        bookingHistory.addReservation(reservation2);
        bookingHistory.addReservation(reservation3);

        System.out.println("Book My Stay App - UC10 Booking Cancellation & Inventory Rollback");
        System.out.println();

        cancellationService.cancelBooking("R101");
        cancellationService.cancelBooking("R999");
        cancellationService.cancelBooking("R103");
        cancellationService.cancelBooking("R101");

        System.out.println();
        inventory.displayInventory();
        System.out.println();
        bookingHistory.displayHistory();
        cancellationService.displayRollbackStack();
    }
}