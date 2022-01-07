package com.parkit.parkingsystem.integration.dao;

import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.dao.TicketDAO;
import com.parkit.parkingsystem.integration.config.DataBaseTestConfig;
import com.parkit.parkingsystem.integration.service.DataBasePrepareService;
import com.parkit.parkingsystem.model.ParkingSpot;
import com.parkit.parkingsystem.model.Ticket;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class TicketDAOTest {

    private static DataBaseTestConfig dataBaseTestConfig = new DataBaseTestConfig();
    private static TicketDAO ticketDAO;
    private static DataBasePrepareService dataBasePrepareService;

    @BeforeAll
    static void setUpBeforeClass() {
        ticketDAO = new TicketDAO();
        ticketDAO.dataBaseConfig = dataBaseTestConfig;
        dataBasePrepareService = new DataBasePrepareService();
    }


    private static void tearDown() {
        dataBasePrepareService.clearDataBaseEntries();
    }

    @Test
    @DisplayName("Vérifie que le ticket se sauvegarde bien et donc qu'on arrive à récupérer le ticket avec getTicket")
    public void saveTicketTest() {
        //GIVEN
        Ticket ticket = new Ticket();
        LocalDateTime inTime = LocalDateTime.now().minusHours(1);
        ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR, false);
        ticket.setParkingSpot(parkingSpot);
        ticket.setVehicleRegNumber("ABCDEF");
        ticket.setPrice(0);
        ticket.setInTime(inTime);
        ticket.setOutTime(null);

        //WHEN
        ticketDAO.saveTicket(ticket);

        //THEN
        assertFalse(ticketDAO.saveTicket(ticket));
        assertNotNull(ticketDAO.getTicket("ABCDEF"));
    }

    @Test
    public void saveTicketOutTimeNotNullTest() {
        //GIVEN
        Ticket ticket = new Ticket();
        LocalDateTime inTime = LocalDateTime.now().minusHours(1);
        LocalDateTime outTime = LocalDateTime.now();
        ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR, false);
        ticket.setParkingSpot(parkingSpot);
        ticket.setVehicleRegNumber("ABCDEF");
        ticket.setPrice(0);
        ticket.setInTime(inTime);
        ticket.setOutTime(outTime);

        //WHEN
        ticketDAO.saveTicket(ticket);

        //THEN
        assertFalse(ticketDAO.saveTicket(ticket));
        assertNotNull(ticketDAO.getTicket("ABCDEF"));
    }

    @Test
    @DisplayName("Vérifie que le ticket est bien mis à jour donc que outTime != null et prix != 0")
    public void updateTicketTest() {
        //GIVEN
        Ticket ticket = new Ticket();
        LocalDateTime inTime = LocalDateTime.now().minusHours(1);
        ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR, false);
        ticket.setParkingSpot(parkingSpot);
        ticket.setVehicleRegNumber("ABCDEF");
        ticket.setPrice(0);
        ticket.setInTime(inTime);
        ticketDAO.saveTicket(ticket);

        //WHEN
        LocalDateTime outTime = LocalDateTime.now();
        ticket.setOutTime(outTime);
        ticket.setPrice(1.5);
        ticketDAO.updateTicket(ticket);

        //THEN
        assertNotNull(ticket.getOutTime());
        assertNotEquals(ticket.getPrice(), 0);
        assertTrue(ticketDAO.updateTicket(ticket));
    }

    @Test
    @DisplayName("Vérifie que le ticket est bien mis à jour donc que outTime = null et prix != 0")
    public void updateTicketNullTest() {
        //GIVEN
        Ticket ticket = new Ticket();
        LocalDateTime inTime = LocalDateTime.now().minusHours(1);
        ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR, false);
        ticket.setParkingSpot(parkingSpot);
        ticket.setVehicleRegNumber("ABCDEF");
        ticket.setPrice(0);
        ticket.setInTime(inTime);
        ticketDAO.saveTicket(ticket);

        //WHEN
        ticket.setOutTime(null);
        ticket.setPrice(1.5);
        ticketDAO.updateTicket(ticket);

        //THEN
        assertNull(ticket.getOutTime());
        assertNotEquals(ticket.getPrice(), 0);
        assertTrue(ticketDAO.updateTicket(ticket));
    }

    @Test
    public void getTicketTest() {

        saveTicketTest();
        Ticket ticket = ticketDAO.getTicket("ABCDEF");
        assertNotNull(ticket);
    }

    @Test
    public void isVehicleInsideTest() {

        saveTicketTest();
        ticketDAO.isVehicleInside("ABCDEF");

        assertTrue(ticketDAO.isVehicleInside("ABCDEF"));
    }

    @Test
    public void countVehicleNbVisitTest() {

        saveTicketTest();
        ticketDAO.countVehicleNbVisit("ABCDEF");

        assertTrue(ticketDAO.countVehicleNbVisit("ABCDEF"));

    }
}