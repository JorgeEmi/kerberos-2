/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kerbeross;

import javax.crypto.SecretKey;
import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author jorge
 */
public class NewTicket implements Serializable {
    private final ArrayList<Ticket> tickets;

    /**
     * Method to initialize the arraylist for a new UTicket.
     */
    public NewTicket() {
        tickets = new ArrayList<>();
    }

    public ArrayList<Ticket> getTickets() {
        return tickets;
    }

    public Ticket searchTicket(String id) {
        for (Ticket i : tickets) {
            if (i.getIdTicket().equals(id)) {
                return i;
            }
        }
        return null;
    }

    public void addTicket(Ticket ticket) {
        tickets.add(ticket);
    }

    /**
     * This ticket will be the one that the user sends to the AS at the time of asking for a service.
     * In other words, this should be the first ticket sent in the network.
     */
    public void generateRequest(String userID, String serviceID, String requestedLifetime) {
        Ticket request = new Ticket();
        request.setIdTicket("solicitud");
        request.setFirstId(userID);
        request.setSecondId(serviceID);
        request.setLifetime(requestedLifetime);
        addTicket(request);
    }

    public void generateResponse4User(String firstId, String timeStamp, String lifetime, String Secretkey) {
        Ticket response = new Ticket();
        response.setIdTicket("respuesta al Client");
        response.setFirstId(firstId);
        response.setTimeStamp(timeStamp);
        response.setLifetime(lifetime);
        response.setSecretKey(Secretkey);
        addTicket(response);
    }

    public void generateTicket(String nameOfTicket, String firstID, String secondID, String timeStamp, String addressIP,
                               String lifetime, String key) {
        addTicket(
                new Ticket(nameOfTicket, firstID, secondID, addressIP, lifetime, timeStamp, key)
        );
    }

    public void request4TGS(String serviceID) {
        Ticket request = new Ticket();
        request.setIdTicket("solicitud al TGS");
        request.setFirstId(serviceID);
        addTicket(request);
    }

    public void addAuthenticator(String firstID, String addressIP, String timeStamp) {
        Ticket auth = new Ticket();
        auth.setIdTicket("autentificación");
        auth.setFirstId(firstID);
        auth.setAddressIP(addressIP);
        auth.setTimeStamp(timeStamp);
        addTicket(auth);
    }


    public boolean[] getFilled(Ticket ticket) {
        boolean[] existingFields = new boolean[6];
        existingFields[0] = ticket.isFilledFirstId();
        existingFields[1] = ticket.isFilledSecondId();
        existingFields[2] = ticket.isFilledAddressIP();
        existingFields[3] = ticket.isFilledLifetime();
        existingFields[4] = ticket.isFilledTimeStamp();
        existingFields[5] = ticket.isFilledSecretKey();
        return existingFields;
    }

    public boolean encryptTicket(SecretKey key, String id) {
        try {
            
            Ticket toEncrypt = searchTicket(id);

            if (toEncrypt == null)
                return false;

            boolean[] existingFields = getFilled(toEncrypt);
            if (existingFields[0])
                toEncrypt.setFirstId(Encryptor.symmetricEncrypt(key, toEncrypt.getFirstId()));
            if (existingFields[1])
                toEncrypt.setSecondId(Encryptor.symmetricEncrypt(key, toEncrypt.getSecondId()));
            if (existingFields[2])
                toEncrypt.setAddressIP(Encryptor.symmetricEncrypt(key, toEncrypt.getAddressIP()));
            if (existingFields[3])
                toEncrypt.setLifetime(Encryptor.symmetricEncrypt(key, toEncrypt.getLifetime()));
            if (existingFields[4])
                toEncrypt.setTimeStamp(Encryptor.symmetricEncrypt(key, toEncrypt.getTimeStamp()));
            if (existingFields[5])
                toEncrypt.setSecretKey(Encryptor.symmetricEncrypt(key, toEncrypt.getSecretKey()));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean decryptTicket(SecretKey key, String id) {
        try {
            
            Ticket toDecrypt = searchTicket(id);

            if (toDecrypt == null)
                return false;

            boolean[] existingFields = getFilled(toDecrypt);
            if (existingFields[0]) {
                toDecrypt.setFirstId(Encryptor.symmetricDecrypt(key, toDecrypt.getFirstId()));
            }
            if (existingFields[1]) {
                toDecrypt.setSecondId(Encryptor.symmetricDecrypt(key, toDecrypt.getSecondId()));
            }
            if (existingFields[2]) {
                toDecrypt.setAddressIP(Encryptor.symmetricDecrypt(key, toDecrypt.getAddressIP()));
            }
            if (existingFields[3]) {
                toDecrypt.setLifetime(Encryptor.symmetricDecrypt(key, toDecrypt.getLifetime()));
            }
            if (existingFields[4]) {
                toDecrypt.setTimeStamp(Encryptor.symmetricDecrypt(key, toDecrypt.getTimeStamp()));
            }
            if (existingFields[5]) {
                toDecrypt.setSecretKey(Encryptor.symmetricDecrypt(key, toDecrypt.getSecretKey()));
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void printTicket(NewTicket newTicket) {
        for (Ticket i : newTicket.getTickets()) {
            printTicket(newTicket, i.getIdTicket());
        }
    }

    public void printTicket(NewTicket newTicket, String ticketId) {
        Ticket ticket = newTicket.searchTicket(ticketId);
        if (ticket != null) {
            boolean[] filled = newTicket.getFilled(ticket);
            System.out.println("idTicket: " + ticket.getIdTicket());
            if (filled[0]) {
                System.out.println("firstId: " + ticket.getFirstId());
            }
            if (filled[1]) {
                System.out.println("secondId: " + ticket.getSecondId());
            }
            if (filled[2]) {
                System.out.println("addressIP: " + ticket.getAddressIP());
            }
            if (filled[3]) {
                System.out.println("lifetime: " + ticket.getLifetime());
            }
            if (filled[4]) {
                System.out.println("timeStamp: " + ticket.getTimeStamp());
            }
            if (filled[5]) {
                System.out.println("key: " + ticket.getSecretKey());
            }
        }
    }

}
