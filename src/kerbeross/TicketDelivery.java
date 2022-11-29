/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kerbeross;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Objects;

/**
 *
 * @author jorge
 */
public class TicketDelivery {
    
    public static Socket socketInitializer(String receiverHost, int connectionPort) throws IOException {
        //  We indicate the destination of the Ticket, establishing the IP where it will be received and the
        //  "channel" or port where both all comms will be held.
        //  The socket indicated in here must be already running in the receiverHost, or the connection
        //  won't be established.
        return new Socket(receiverHost, connectionPort);
    }

    //  A server socket takes a request and can send a response without the need to start a second socket.
    public static ServerSocket serverSocketInitializer(int receiverPort) {

        try {
            return new ServerSocket(receiverPort);
        } catch (IOException e) {
            return null;
        }
    }

    public static ObjectOutputStream objectSenderInitializer(Socket socket) {
        try {
            //  We state that we are sending something through an outputStream.
            OutputStream outputStream = socket.getOutputStream();
            //  Now we clarify that we are sending an object through said stream.
            return new ObjectOutputStream(outputStream);
        } catch (Exception e){
            System.out.println("Error al obtener OutputStream del socket: " + socket.toString());
            return null;
        }
    }

    public static Socket requestAccepter(ServerSocket serverSocket) {

        try {
            //  Now we will accept incoming messages from the established channel.
            return serverSocket.accept();
        } catch (IOException e) {
            return null;
        }
    }

    public static NewTicket ticketSender(String receiverHost, int connectionPort, NewTicket ticket) {

        try {
            
            Socket socket = socketInitializer(receiverHost, connectionPort);

            //  Now we need to send the object through the connection.
            Objects.requireNonNull(objectSenderInitializer(socket)).writeObject(ticket);

            //  We show in the console what are we trying to send.
            System.out.print("\nTicket enviado:\n");
            ticket.printTicket(ticket);
            System.out.print("\ntermina ticket enviado.\n");

            //  So now we think it has been sent, but we need to be sure of it.
            //  We are going to be receiving information from the socket to confirm
            //  the reception of the object.
            InputStream inputStream = socket.getInputStream();
            //  The server will be returning a boolean, which is already serialized, so we can make
            //  use of the already existing methods for sending and receiving booleans.
            ObjectInputStream objectReceiver = new ObjectInputStream(inputStream);
            //  At this point, we are reading the information sent as a response for our request.
            NewTicket ticket1 = (NewTicket) objectReceiver.readObject();

            System.out.print("\nRecibido en red:\n");
            ticket1.printTicket(ticket1);
            System.out.print("\nTermina recibo en red\n");

            //  Now that we have a response we can close the communication channel.
            socket.close();

            return ticket1;
        } catch (Exception e) {
            System.out.print("\nError al recibir el ticket." +
                    "\nError:");
            e.printStackTrace();
            return null;
        }
    }
    
    public static NewTicket ticketAccepter(Socket socket) {

        try {
            //  Once accepted, we are going to need to read the information received.
            InputStream inputStream = socket.getInputStream();
            //  We specify that we will be reading an object from said stream.
            ObjectInputStream objectReceiver = new ObjectInputStream(inputStream);
            //  Now we need to read the Ticket.
            return (NewTicket) objectReceiver.readObject();
        } catch (Exception e) {
            System.out.println("No se ha podido recibir el ticket." +
                    "\nError: ");
            e.printStackTrace();
            return null;
        }
    }

    public static boolean booleanResponder(Socket socket, boolean response) {

        try {
            //  We send the response ticket.
            OutputStream outputStream = socket.getOutputStream();
            ObjectOutputStream objectSender = new ObjectOutputStream(outputStream);
            objectSender.writeBoolean(response);
            //  We can proceed to close the receiving socket.
            socket.close();
            return true;
        } catch (Exception e) {
            System.out.println("\nNo se ha podido enviar una respuesta (boolean responder)." +
                    "\nError: ");
            e.printStackTrace();
            return false;
        }
    }

    public static boolean ticketResponder(Socket socket, NewTicket ticketResponse) {

        try {
            //  We send the response ticket.
            OutputStream outputStream = socket.getOutputStream();
            ObjectOutputStream objectSender = new ObjectOutputStream(outputStream);
            objectSender.writeObject(ticketResponse);

            //  We print the ticket response.
            ticketResponse.printTicket(ticketResponse);
            System.out.println("\nEl ticket ha sido enviado exitosamente.");

            //  We can proceed to close the receiving socket.
            socket.close();
            return true;
        } catch (Exception e) {
            System.out.println("\nHa ocurrido un error al enviar el ticket.");
            System.out.println("Error: ");
            e.printStackTrace();
            return false;
        }
    }
}
