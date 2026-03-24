import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        System.out.println("Room Type: " + roomType);
    }
}

class AddOnService {
    private String serviceName;
    private double serviceCost;

    public AddOnService(String serviceName, double serviceCost) {
        this.serviceName = serviceName;
        this.serviceCost = serviceCost;
    }

    public String getServiceName() {
        return serviceName;
    }

    public double getServiceCost() {
        return serviceCost;
    }

    public void displayService() {
        System.out.println("Service Name: " + serviceName);
        System.out.println("Service Cost: $" + serviceCost);
    }
}

class AddOnServiceManager {
    private Map<String, List<AddOnService>> reservationServices;

    public AddOnServiceManager() {
        reservationServices = new HashMap<>();
    }

    public void addServiceToReservation(String reservationId, AddOnService service) {
        reservationServices.putIfAbsent(reservationId, new ArrayList<AddOnService>());
        reservationServices.get(reservationId).add(service);
    }

    public double calculateTotalAdditionalCost(String reservationId) {
        double total = 0.0;
        List<AddOnService> services = reservationServices.get(reservationId);

        if (services != null) {
            for (AddOnService service : services) {
                total += service.getServiceCost();
            }
        }

        return total;
    }

    public void displayServicesForReservation(String reservationId) {
        List<AddOnService> services = reservationServices.get(reservationId);

        System.out.println("Selected Add-On Services for Reservation ID: " + reservationId);

        if (services == null || services.isEmpty()) {
            System.out.println("No add-on services selected.");
            return;
        }

        for (AddOnService service : services) {
            service.displayService();
            System.out.println();
        }

        System.out.println("Total Additional Cost: $" + calculateTotalAdditionalCost(reservationId));
    }
}

public class BookMyStayApp {
    public static void main(String[] args) {
        Reservation reservation = new Reservation("R101", "Hari", "Double Room");

        AddOnService breakfast = new AddOnService("Breakfast", 350.0);
        AddOnService airportPickup = new AddOnService("Airport Pickup", 800.0);
        AddOnService spaAccess = new AddOnService("Spa Access", 1200.0);

        AddOnServiceManager serviceManager = new AddOnServiceManager();

        serviceManager.addServiceToReservation(reservation.getReservationId(), breakfast);
        serviceManager.addServiceToReservation(reservation.getReservationId(), airportPickup);
        serviceManager.addServiceToReservation(reservation.getReservationId(), spaAccess);

        System.out.println("Book My Stay App - UC7 Add-On Service Selection");
        System.out.println();
        reservation.displayReservation();
        System.out.println();
        serviceManager.displayServicesForReservation(reservation.getReservationId());
    }
}