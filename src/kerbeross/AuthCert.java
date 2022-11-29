package kerbeross;

import java.io.IOException;
import java.net.ServerSocket;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.SecretKey;

public class AuthCert {

    public static void main(String[] args) throws NoSuchAlgorithmException {
        int AUTH_PORT = 5000;

        SecretKey secretTGS = new KeyManager().generateSecretKey();
        SecretKey secretCTGS = new KeyManager().generateSecretKey();
        SecretKey secretCV = new KeyManager().generateSecretKey();
        SecretKey secretV = new KeyManager().generateSecretKey();
        SecretKey secretC = new KeyManager().generateSecretKey();
        try {

            KeyDelivery kd = new KeyDelivery();

            //Clave del Client al Client        si
            ServerSocket ssC_C = new ServerSocket(AUTH_PORT);
            System.out.println("Esperando al Cliente...");
            kd.sendSecretKey(secretC, ssC_C);
            System.out.println("Clave enviada al Cliente");
            
            //Clave del Client al AS            si
            ServerSocket ssC_AS = new ServerSocket(AUTH_PORT);
            System.out.println("Esperando al AS...");
            kd.sendSecretKey(secretC, ssC_AS);
            
            //Clave del Client/TGS al AS        si
            ServerSocket ssCTGS_AS = new ServerSocket(AUTH_PORT);
            kd.sendSecretKey(secretCTGS, ssCTGS_AS); 
            
            //Clave del TGS al AS               si
            ServerSocket ssTGS_AS = new ServerSocket(AUTH_PORT);
            kd.sendSecretKey(secretTGS, ssTGS_AS);
            System.out.println("Clave enviada al AS");                      

            //Clave del TGS al TGS               si
            ServerSocket ssTGS_TGS = new ServerSocket(AUTH_PORT);
            System.out.println("Esperando al TGS...");
            kd.sendSecretKey(secretTGS, ssTGS_TGS);
            
            //Clave del Client/TGS al TGS        no, se manda despues dentro del ticket tgs el cual va codificado con su llave
            ServerSocket ssCTGS_TGS = new ServerSocket(AUTH_PORT);
            kd.sendSecretKey(secretCTGS, ssCTGS_TGS);   
            
            //Clave del Servidor al TGS           si
            ServerSocket ssV_TGS = new ServerSocket(AUTH_PORT);
            kd.sendSecretKey(secretV, ssV_TGS);     
            
            //Clave del Client/Servidor al TGS      si
            ServerSocket ssCV_TGS = new ServerSocket(AUTH_PORT);
            kd.sendSecretKey(secretCV, ssCV_TGS);
            System.out.println("Clave enviada al TGS");  
            
            //Clave del Client/Servidor al V        se manda despues dentro del ticket servidor el cual va codificado con su llave
            ServerSocket ssCV_V = new ServerSocket(AUTH_PORT);
            System.out.println("Esperando al Servidor...");
            kd.sendSecretKey(secretCV, ssCV_V);

            //Clave del Servidor al V            si
            ServerSocket ssV_V = new ServerSocket(AUTH_PORT);
            kd.sendSecretKey(secretCV, ssV_V);
            System.out.println("Clave enviada al Servidor");       
            

        } 
        catch (IOException ex) {
            System.out.println("IOException: "+ex);
        }

    }

}
