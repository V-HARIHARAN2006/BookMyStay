import java.util.HashMap;
import java.util.Map;

abstract class Room {
    protected int numOfBeds;
    protected int roomSize;
    protected double pricePerNight;

    public Room(int numOfBeds, int roomSize, double pricePerNight) {
        this.numOfBeds = numOfBeds;
        this.roomSize = roomSize;
        this.pricePerNight = pricePerNight;
    }

    public void displayRoomDetails() {
        System.out.println("Beds: " + numOfBeds);
        System.out.println("Size: " + roomSize + " sqft");
        System.out.println("Price per night: $" + pricePerNight);
    }
}

class SingleRoom extends Room {
    public SingleRoom() {
        super(1, 150, 1200.0);
    }
}

class DoubleRoom extends Room {
    public DoubleRoom() {
        super(2, 250, 2200.0);
    }
}

class SuiteRoom extends Room {
    public SuiteRoom() {
        super(3, 500, 5000.0);
    }
}

class RoomInventory {
    private HashMap<String, Integer> availability;

    public RoomInventory() {
        availability = new HashMap<>();
        availability.put("Single Room", 5);
        availability.put("Double Room", 3);
        availability.put("Suite Room", 2);
    }

    public int getAvailability(String roomType) {
        return availability.getOrDefault(roomType, 0);
    }

    public void updateAvailability(String roomType, int count) {
        availability.put(roomType, count);
    }

    public void displayInventory() {
        System.out.println("Current Room Inventory:");
        for (Map.Entry<String, Integer> entry : availability.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
    }
}

public class BookMyStayApp {
    public static void main(String[] args) {
        Room singleRoom = new SingleRoom();
        Room doubleRoom = new DoubleRoom();
        Room suiteRoom = new SuiteRoom();

        RoomInventory inventory = new RoomInventory();

        System.out.println("Book My Stay App - UC3 Centralized Room Inventory Management");
        System.out.println();

        System.out.println("Single Room Details:");
        singleRoom.displayRoomDetails();
        System.out.println("Available: " + inventory.getAvailability("Single Room"));
        System.out.println();

        System.out.println("Double Room Details:");
        doubleRoom.displayRoomDetails();
        System.out.println("Available: " + inventory.getAvailability("Double Room"));
        System.out.println();

        System.out.println("Suite Room Details:");
        suiteRoom.displayRoomDetails();
        System.out.println("Available: " + inventory.getAvailability("Suite Room"));
        System.out.println();

        inventory.updateAvailability("Single Room", 4);
        inventory.updateAvailability("Double Room", 2);

        System.out.println("Inventory After Update:");
        inventory.displayInventory();
    }
}