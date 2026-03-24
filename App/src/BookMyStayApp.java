import java.util.ArrayList;
import java.util.List;

class Reservation {
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
        System.out.println("Status: " + (confirmed ? "Confirmed" : "Pending"));
    }
}

class BookingHistory {
    private List<Reservation> confirmedBookings;

    public BookingHistory() {
        confirmedBookings = new ArrayList<>();
    }

    public void addConfirmedReservation(Reservation reservation) {
        if (reservation.isConfirmed()) {
            confirmedBookings.add(reservation);
        }
    }

    public List<Reservation> getConfirmedBookings() {
        return confirmedBookings;
    }

    public void displayBookingHistory() {
        System.out.println("Booking History:");
        System.out.println();

        if (confirmedBookings.isEmpty()) {
            System.out.println("No confirmed bookings available.");
            return;
        }

        for (Reservation reservation : confirmedBookings) {
            reservation.displayReservation();
            System.out.println();
        }
    }
}

class BookingReportService {
    public void generateSummaryReport(List<Reservation> reservations) {
        System.out.println("Booking Summary Report");
        System.out.println();

        int totalBookings = reservations.size();
        int singleRoomCount = 0;
        int doubleRoomCount = 0;
        int suiteRoomCount = 0;

        for (Reservation reservation : reservations) {
            if (reservation.getRoomType().equals("Single Room")) {
                singleRoomCount++;
            } else if (reservation.getRoomType().equals("Double Room")) {
                doubleRoomCount++;
            } else if (reservation.getRoomType().equals("Suite Room")) {
                suiteRoomCount++;
            }
        }

        System.out.println("Total Confirmed Bookings: " + totalBookings);
        System.out.println("Single Room Bookings: " + singleRoomCount);
        System.out.println("Double Room Bookings: " + doubleRoomCount);
        System.out.println("Suite Room Bookings: " + suiteRoomCount);
    }
}

public class BookMyStayApp {
    public static void main(String[] args) {
        BookingHistory bookingHistory = new BookingHistory();
        BookingReportService reportService = new BookingReportService();

        Reservation reservation1 = new Reservation("R101", "Hari", "Single Room", "SR1", true);
        Reservation reservation2 = new Reservation("R102", "Arun", "Double Room", "DR1", true);
        Reservation reservation3 = new Reservation("R103", "Priya", "Suite Room", "SU1", true);
        Reservation reservation4 = new Reservation("R104", "Meena", "Single Room", "SR2", true);

        bookingHistory.addConfirmedReservation(reservation1);
        bookingHistory.addConfirmedReservation(reservation2);
        bookingHistory.addConfirmedReservation(reservation3);
        bookingHistory.addConfirmedReservation(reservation4);

        System.out.println("Book My Stay App - UC8 Booking History & Reporting");
        System.out.println();

        bookingHistory.displayBookingHistory();
        reportService.generateSummaryReport(bookingHistory.getConfirmedBookings());
    }
}