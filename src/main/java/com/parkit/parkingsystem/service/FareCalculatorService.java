package com.parkit.parkingsystem.service;

import com.parkit.parkingsystem.constants.Fare;
import com.parkit.parkingsystem.model.Ticket;

import java.time.Duration;

public class FareCalculatorService {

    private static final long freeBelowThirtyMinutes = 30;
    private static final double discountForRecurringUsers = 0.95;

    public void calculateFare(Ticket ticket) {
        if ((ticket.getOutTime() == null) || (ticket.getOutTime().isBefore(ticket.getInTime()))) {
            throw new IllegalArgumentException("Out time provided is incorrect:" + ticket.getOutTime().toString());
        }

        long duration = Duration.between(ticket.getInTime(), ticket.getOutTime()).toMinutes();
        //TODO: Some tests are failing here. Need to check if this logic is correct


        if (isFree(Duration.ofMinutes(duration))) { // Si duration <= 30 minutes, duration = 0 sinon duration = duration
            return;
        }

        double discount = getDiscount(ticket); // Si isRecurrentUser alors discount = 0.95 sinon = 1

        switch (ticket.getParkingSpot().getParkingType()) {
            case CAR: {
                ticket.setPrice(((duration) * (Fare.CAR_RATE_PER_HOUR / 60)) * discount);
                break;
            }
            case BIKE: {
                ticket.setPrice(((duration) * (Fare.BIKE_RATE_PER_HOUR / 60)) * discount);
                break;
            }
            default:
                throw new IllegalArgumentException("Unknown Parking Type");
        }
    }

    private boolean isFree(Duration duration) { //
        return duration.toMinutes() <= freeBelowThirtyMinutes;
    }

    private double getDiscount (Ticket ticket) {
        return (ticket.isRecurrentUser()) ? discountForRecurringUsers : 1;
    }
}

