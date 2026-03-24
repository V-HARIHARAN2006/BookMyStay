import java.util.HashMap;
import java.util.Map;

class InvalidBookingException extends Exception {
    public InvalidBookingException(String message) {
        super(message);
    }
}

class Reservation {
    private String reservationId;
    private String guestName;
    private String roomType;

    public Reservation(String reservationId, String guestName, String roomType) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
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

    public void displayReservation() {
        System.out.println("Reservation ID: " + reservationId);
        System.out.println("Guest Name: " + guestName);
        System.out.println("Requested Room Type: " + roomType);
    }
}

class RoomInventory {
    private Map<String, Integer> availability;

    public RoomInventory() {
        availability = new HashMap<>();
        availability.put("Single Room", 2);
        availability.put("Double Room", 1);
        availability.put("Suite Room", 1);
    }

    public boolean isValidRoomType(String roomType) {
        return availability.containsKey(roomType);
    }

    public int getAvailability(String roomType) {
        return availability.getOrDefault(roomType, 0);
    }

    public void decrementAvailability(String roomType) throws InvalidBookingException {
        if (!isValidRoomType(roomType)) {
            throw new InvalidBookingException("Invalid room type: " + roomType);
        }

        int currentCount = availability.get(roomType);

        if (currentCount <= 0) {
            throw new InvalidBookingException("No rooms available for room type: " + roomType);
        }

        availability.put(roomType, currentCount - 1);
    }

    public void displayInventory() {
        System.out.println("Current Inventory:");
        for (Map.Entry<String, Integer> entry : availability.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
    }
}

class InvalidBookingValidator {
    public void validateReservation(Reservation reservation, RoomInventory inventory) throws InvalidBookingException {
        if (reservation.getGuestName() == null || reservation.getGuestName().trim().isEmpty()) {
            throw new InvalidBookingException("Guest name cannot be empty.");
        }

        if (reservation.getRoomType() == null || reservation.getRoomType().trim().isEmpty()) {
            throw new InvalidBookingException("Room type cannot be empty.");
        }

        if (!inventory.isValidRoomType(reservation.getRoomType())) {
            throw new InvalidBookingException("Requested room type does not exist: " + reservation.getRoomType());
        }

        if (inventory.getAvailability(reservation.getRoomType()) <= 0) {
            throw new InvalidBookingException("Requested room type is not available: " + reservation.getRoomType());
        }
    }
}

class BookingService {
    private RoomInventory inventory;
    private InvalidBookingValidator validator;

    public BookingService(RoomInventory inventory) {
        this.inventory = inventory;
        this.validator = new InvalidBookingValidator();
    }

    public void processBooking(Reservation reservation) {
        try {
            validator.validateReservation(reservation, inventory);
            inventory.decrementAvailability(reservation.getRoomType());
            System.out.println("Booking confirmed for " + reservation.getGuestName() + " in " + reservation.getRoomType());
        } catch (InvalidBookingException e) {
            System.out.println("Booking failed for reservation ID " + reservation.getReservationId() + ": " + e.getMessage());
        }
    }
}

public class BookMyStayApp {
    public static void main(String[] args) {
        RoomInventory inventory = new RoomInventory();
        BookingService bookingService = new BookingService(inventory);

        Reservation reservation1 = new Reservation("R101", "Hari", "Single Room");
        Reservation reservation2 = new Reservation("R102", " ", "Double Room");
        Reservation reservation3 = new Reservation("R103", "Priya", "Deluxe Room");
        Reservation reservation4 = new Reservation("R104", "Meena", "Suite Room");
        Reservation reservation5 = new Reservation("R105", "Arun", "Suite Room");

        System.out.println("Book My Stay App - UC9 Error Handling & Validation");
        System.out.println();

        bookingService.processBooking(reservation1);
        bookingService.processBooking(reservation2);
        bookingService.processBooking(reservation3);
        bookingService.processBooking(reservation4);
        bookingService.processBooking(reservation5);

        System.out.println();
        inventory.displayInventory();
    }
}