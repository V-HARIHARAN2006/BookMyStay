import java.util.HashMap;
import java.util.Map;

abstract class Room {
    protected String roomType;
    protected int numOfBeds;
    protected int roomSize;
    protected double pricePerNight;

    public Room(String roomType, int numOfBeds, int roomSize, double pricePerNight) {
        this.roomType = roomType;
        this.numOfBeds = numOfBeds;
        this.roomSize = roomSize;
        this.pricePerNight = pricePerNight;
    }

    public String getRoomType() {
        return roomType;
    }

    public void displayRoomDetails() {
        System.out.println("Room Type: " + roomType);
        System.out.println("Beds: " + numOfBeds);
        System.out.println("Size: " + roomSize + " sqft");
        System.out.println("Price per night: $" + pricePerNight);
    }
}

class SingleRoom extends Room {
    public SingleRoom() {
        super("Single Room", 1, 150, 1200.0);
    }
}

class DoubleRoom extends Room {
    public DoubleRoom() {
        super("Double Room", 2, 250, 2200.0);
    }
}

class SuiteRoom extends Room {
    public SuiteRoom() {
        super("Suite Room", 3, 500, 5000.0);
    }
}

class RoomInventory {
    private HashMap<String, Integer> availability;

    public RoomInventory() {
        availability = new HashMap<>();
        availability.put("Single Room", 5);
        availability.put("Double Room", 3);
        availability.put("Suite Room", 0);
    }

    public int getAvailability(String roomType) {
        return availability.getOrDefault(roomType, 0);
    }
}

class SearchService {
    private RoomInventory inventory;
    private Room[] rooms;

    public SearchService(RoomInventory inventory, Room[] rooms) {
        this.inventory = inventory;
        this.rooms = rooms;
    }

    public void displayAvailableRooms() {
        System.out.println("Available Room Search Results:");
        System.out.println();

        for (Room room : rooms) {
            int count = inventory.getAvailability(room.getRoomType());
            if (count > 0) {
                room.displayRoomDetails();
                System.out.println("Available Rooms: " + count);
                System.out.println();
            }
        }
    }
}

public class BookMyStayApp {
    public static void main(String[] args) {
        RoomInventory inventory = new RoomInventory();

        Room[] rooms = {
                new SingleRoom(),
                new DoubleRoom(),
                new SuiteRoom()
        };

        SearchService searchService = new SearchService(inventory, rooms);

        System.out.println("Book My Stay App - UC4 Room Search & Availability Check");
        System.out.println();
        searchService.displayAvailableRooms();
    }
}