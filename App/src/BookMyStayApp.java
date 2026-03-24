import java.util.LinkedList;
import java.util.Queue;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

class Reservation {
    private String guestName;
    private String roomType;
    private String assignedRoomId;
    private boolean confirmed;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
        this.assignedRoomId = null;
        this.confirmed = false;
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

    public void confirmReservation(String assignedRoomId) {
        this.assignedRoomId = assignedRoomId;
        this.confirmed = true;
    }

    public void displayReservation() {
        System.out.println("Guest Name: " + guestName);
        System.out.println("Requested Room Type: " + roomType);
        if (confirmed) {
            System.out.println("Assigned Room ID: " + assignedRoomId);
            System.out.println("Reservation Status: Confirmed");
        } else {
            System.out.println("Reservation Status: Pending");
        }
    }
}

class BookingRequestQueue {
    private Queue<Reservation> requestQueue;

    public BookingRequestQueue() {
        requestQueue = new LinkedList<>();
    }

    public void addRequest(Reservation reservation) {
        requestQueue.offer(reservation);
    }

    public Reservation getNextRequest() {
        return requestQueue.poll();
    }

    public boolean isEmpty() {
        return requestQueue.isEmpty();
    }
}

class RoomInventory {
    private HashMap<String, Integer> availability;

    public RoomInventory() {
        availability = new HashMap<>();
        availability.put("Single Room", 2);
        availability.put("Double Room", 1);
        availability.put("Suite Room", 1);
    }

    public int getAvailability(String roomType) {
        return availability.getOrDefault(roomType, 0);
    }

    public void decrementAvailability(String roomType) {
        int current = getAvailability(roomType);
        if (current > 0) {
            availability.put(roomType, current - 1);
        }
    }

    public void displayInventory() {
        System.out.println("Current Inventory State:");
        for (Map.Entry<String, Integer> entry : availability.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
    }
}

class BookingService {
    private RoomInventory inventory;
    private HashMap<String, Set<String>> allocatedRooms;
    private Set<String> allAllocatedRoomIds;
    private HashMap<String, Integer> roomCounters;

    public BookingService(RoomInventory inventory) {
        this.inventory = inventory;
        this.allocatedRooms = new HashMap<>();
        this.allAllocatedRoomIds = new HashSet<>();
        this.roomCounters = new HashMap<>();

        allocatedRooms.put("Single Room", new HashSet<String>());
        allocatedRooms.put("Double Room", new HashSet<String>());
        allocatedRooms.put("Suite Room", new HashSet<String>());

        roomCounters.put("Single Room", 0);
        roomCounters.put("Double Room", 0);
        roomCounters.put("Suite Room", 0);
    }

    public void processReservation(Reservation reservation) {
        String roomType = reservation.getRoomType();

        if (inventory.getAvailability(roomType) <= 0) {
            System.out.println("Reservation failed for " + reservation.getGuestName() + ". No rooms available for " + roomType);
            return;
        }

        String roomId = generateUniqueRoomId(roomType);

        reservation.confirmReservation(roomId);
        allocatedRooms.get(roomType).add(roomId);
        allAllocatedRoomIds.add(roomId);
        inventory.decrementAvailability(roomType);

        System.out.println("Reservation confirmed for " + reservation.getGuestName());
        System.out.println("Room Type: " + roomType);
        System.out.println("Assigned Room ID: " + roomId);
        System.out.println();
    }

    private String generateUniqueRoomId(String roomType) {
        String prefix = "";

        if (roomType.equals("Single Room")) {
            prefix = "SR";
        } else if (roomType.equals("Double Room")) {
            prefix = "DR";
        } else if (roomType.equals("Suite Room")) {
            prefix = "SU";
        }

        String roomId;
        do {
            int next = roomCounters.get(roomType) + 1;
            roomCounters.put(roomType, next);
            roomId = prefix + next;
        } while (allAllocatedRoomIds.contains(roomId));

        return roomId;
    }

    public void displayAllocatedRooms() {
        System.out.println("Allocated Room Records:");
        for (Map.Entry<String, Set<String>> entry : allocatedRooms.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
    }
}

public class BookMyStayApp {
    public static void main(String[] args) {
        BookingRequestQueue bookingQueue = new BookingRequestQueue();
        RoomInventory inventory = new RoomInventory();
        BookingService bookingService = new BookingService(inventory);

        bookingQueue.addRequest(new Reservation("Hari", "Single Room"));
        bookingQueue.addRequest(new Reservation("Arun", "Double Room"));
        bookingQueue.addRequest(new Reservation("Priya", "Suite Room"));
        bookingQueue.addRequest(new Reservation("Kavin", "Single Room"));
        bookingQueue.addRequest(new Reservation("Meena", "Single Room"));

        System.out.println("Book My Stay App - UC6 Reservation Confirmation & Room Allocation");
        System.out.println();

        while (!bookingQueue.isEmpty()) {
            Reservation reservation = bookingQueue.getNextRequest();
            bookingService.processReservation(reservation);
        }

        inventory.displayInventory();
        System.out.println();
        bookingService.displayAllocatedRooms();
    }
}