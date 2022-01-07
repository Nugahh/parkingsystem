package com.parkit.parkingsystem.integration.dao;

import com.parkit.parkingsystem.constants.DBConstants;
import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.dao.ParkingSpotDAO;
import com.parkit.parkingsystem.integration.config.DataBaseTestConfig;
import com.parkit.parkingsystem.model.ParkingSpot;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

    @ExtendWith(MockitoExtension.class)
    class ParkingSpotDAOTest {

        private static ParkingSpotDAO parkingSpotDAO;

        @Mock
        private static DataBaseTestConfig dataBaseTestConfig;
        @Mock
        private static Connection con;
        @Mock
        private static PreparedStatement ps;
        @Mock
        private static ResultSet rs;

        @BeforeEach
        private void setUp() throws Exception {

            parkingSpotDAO = new ParkingSpotDAO();
            parkingSpotDAO.dataBaseConfig = dataBaseTestConfig;

            when(dataBaseTestConfig.getConnection()).thenReturn(con);
        }

        @Test
        public void getNextAvailableSlotTest() throws Exception {
            when(con.prepareStatement(DBConstants.GET_NEXT_PARKING_SPOT)).thenReturn(ps);
            when(ps.executeQuery()).thenReturn(rs);
            when(rs.next()).thenReturn(true);
            when(rs.getInt(1)).thenReturn(3);

            assertEquals(parkingSpotDAO.getNextAvailableSlot(ParkingType.CAR), 3);
        }

        @Test
        public void getNextAvailableSlotFalseTest() throws Exception {
            when(con.prepareStatement(DBConstants.GET_NEXT_PARKING_SPOT)).thenReturn(ps);
            when(ps.executeQuery()).thenReturn(rs);
            when(rs.next()).thenReturn(false);

            assertEquals(parkingSpotDAO.getNextAvailableSlot(ParkingType.CAR), -1);
        }

        @Test
        public void updateParkingTest() throws Exception {
            ParkingSpot parkingSpot = new ParkingSpot(2, ParkingType.CAR, true);
            when(con.prepareStatement(DBConstants.UPDATE_PARKING_SPOT)).thenReturn(ps);
            when(ps.executeUpdate()).thenReturn(1);

            assertTrue(parkingSpotDAO.updateParking(parkingSpot));
        }

        @Test
        public void updateParkingFalseTest() throws Exception {
            ParkingSpot parkingSpot = new ParkingSpot(2, ParkingType.CAR, true);
            when(con.prepareStatement(DBConstants.UPDATE_PARKING_SPOT)).thenReturn(ps);
            when(ps.executeUpdate()).thenReturn(0);

            assertFalse(parkingSpotDAO.updateParking(parkingSpot));
        }
    }

