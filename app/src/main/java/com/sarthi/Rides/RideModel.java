package com.sarthi.Rides;

import java.io.Serializable;

/**
 * Created by Vihas on 28-03-2017.
 */

public class RideModel implements Serializable{
    String toAddress;
    String fromAddress;
    String personName;
    String time;
    String fair;
    boolean join = false;
    boolean rideStarted = false;

    public String getToAddress() {
        return toAddress;
    }

    public void setToAddress(String toAddress) {
        this.toAddress = toAddress;
    }

    public String getFromAddress() {
        return fromAddress;
    }

    public void setFromAddress(String fromAddress) {
        this.fromAddress = fromAddress;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getFair() {
        return fair;
    }

    public void setFair(String fair) {
        this.fair = fair;
    }

    public boolean isJoin() {
        return join;
    }

    public void setJoin(boolean join) {
        this.join = join;
    }

    public boolean isRideStarted() {
        return rideStarted;
    }

    public void setRideStarted(boolean rideStarted) {
        this.rideStarted = rideStarted;
    }
}
