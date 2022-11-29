/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kerbeross;

import java.io.Serializable;

/**
 * @author jorgeemi
 */

public class Ticket implements Serializable {

    private String idTicket;
    private String firstId;
    private String secondId;
    private String addressIP;
    private String lifetime;
    private String timeStamp;
    private String secretkey;

    public Ticket() {
    }

    public Ticket(String idTicket, String firstId, String secondId, String addressIP, String lifetime,
                  String timeStamp, String Secretkey) {
        this.idTicket = idTicket;
        this.firstId = firstId;
        this.secondId = secondId;
        this.addressIP = addressIP;
        this.lifetime = lifetime;
        this.timeStamp = timeStamp;
        this.secretkey = Secretkey;
    }

    public String getIdTicket() {
        return idTicket;
    }

    public void setIdTicket(String idTicket) {
        this.idTicket = idTicket;
    }

    public String getFirstId() {
        return firstId;
    }

    public void setFirstId(String firstId) {
        this.firstId = firstId;
    }

    public String getSecondId() {
        return secondId;
    }

    public void setSecondId(String secondId) {
        this.secondId = secondId;
    }

    public String getAddressIP() {
        return addressIP;
    }

    public void setAddressIP(String addressIP) {
        this.addressIP = addressIP;
    }

    public String getLifetime() {
        return lifetime;
    }

    public void setLifetime(String lifetime) {
        this.lifetime = lifetime;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getSecretKey() {
        return secretkey;
    }

    public void setSecretKey(String Secretkey) {
        this.secretkey = Secretkey;
    }

    public boolean isFilledFirstId() {
        return getFirstId() != null;
    }

    public boolean isFilledSecondId() {
        return getSecondId() != null;
    }

    public boolean isFilledAddressIP() {
        return getAddressIP() != null;
    }

    public boolean isFilledLifetime() {
        return getLifetime() != null;
    }

    public boolean isFilledTimeStamp() {
        return getTimeStamp() != null;
    }

    public boolean isFilledSecretKey() {
        return getSecretKey() != null;
    }
}
