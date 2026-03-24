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

public class BookMyStayApp {
    public static void main(String[] args) {
        Room singleRoom = new SingleRoom();
        Room doubleRoom = new DoubleRoom();
        Room suiteRoom = new SuiteRoom();

        int singleRoomAvailable = 5;
        int doubleRoomAvailable = 3;
        int suiteRoomAvailable = 2;

        System.out.println("Hotel Room Initialization");
        System.out.println();

        System.out.println("Single Room:");
        singleRoom.displayRoomDetails();
        System.out.println("Available: " + singleRoomAvailable);
        System.out.println();

        System.out.println("Double Room:");
        doubleRoom.displayRoomDetails();
        System.out.println("Available: " + doubleRoomAvailable);
        System.out.println();

        System.out.println("Suite Room:");
        suiteRoom.displayRoomDetails();
        System.out.println("Available: " + suiteRoomAvailable);
    }
}