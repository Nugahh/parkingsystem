package com.parkit.parkingsystem.integration.model;

import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.model.ParkingSpot;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ParkingSpotTest {

    private ParkingSpot parkingSpot = new ParkingSpot(10, ParkingType.CAR, true);

    @Test
    public void setIdTest() {
        parkingSpot.setId(1);
        assertEquals(1, parkingSpot.getId());
    }

    @Test
    public void setParkingTypeTest() {
        parkingSpot.setParkingType(ParkingType.CAR);
        assertEquals(ParkingType.CAR, parkingSpot.getParkingType());
    }

    @Test
    public void hashCodeTest() {

        parkingSpot.setId(4);
        assertEquals(4, parkingSpot.hashCode());
    }
    @Test
    public void equalsTrueTest(){

        assertEquals(parkingSpot, parkingSpot);
    }

    @Test
    public void equalsNotEqualsWhenAnotherInstance(){

        assertNotEquals(parkingSpot, new ParkingSpot(1, ParkingType.CAR, false));
    }

    @Test
    public void equalsFalseWhenNullTest(){


        assertNotEquals(null, parkingSpot);
    }

    @Test
    public void equalsFalseWhenAnotherClassTest(){

        assertNotEquals(parkingSpot, "");
    }

}

