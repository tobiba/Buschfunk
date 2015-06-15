package buschfunkpackage;

// FRAGEN: --> Rückgabewert bei public String onMessage richtig? Warum klappt das Erstellen eines ClientThreads hier nicht? Wie kann man die Werte aus dem ClientThread hier wieder zurückgeben?

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

//import buschfunkpackage.Server2.ClientThread;

@ServerEndpoint("/atpendpoint")
public class ATPWSEndpoint {
	
	int id;
	// the Username of the Client
	String username;
	// the date I connect
	String date;
	double longitude;
	double latitude;
	String ip;
	Connection c; // zum testen
	String message;
	
	//public static String nameofharry = "Harry";
	//public static String ipofharry = "192.168.0.4";
	//public static double longitudeofharry = 45.374635;
	//public static double latitudeofharry = 89.928374;
	//public static String ipofharry2 = "192.168.0.80";
	//public static double longitudeofharry2 = 8.534478;
	//public static double  latitudeofharry2 = 49.474235;
	
	public static boolean logoff;

    private static final Logger LOG = Logger.getLogger(ATPWSEndpoint.class.getName());
    private static Set<Session> peers = Collections.synchronizedSet(new HashSet<Session>());
    
    public static void main(String[] args){
    	atpendpoint.publish("http://localhost:1500");
    }

    
 // Das Frontend ruft die "onMessage" Methode auf und übergibt die Message sowie die Userdaten
    @OnMessage // FRAGE: Rückgabewert public List<String> richtig? Habe ich angenommen, da wir ja eine Liste zurückgeben wollen
                 // --> ANTWORT: Sollte String sein, da ein JSON-Objekt (unser RŸckgabeobjekt) vom Typ String ist
    
    public String onMessage(String message, String nameofharry, String ipofharry, double longitudeofharry, double latitudeofharry) throws SQLException, IOException {
        if (message != null) {
            	
        	//hier Funktionen von Denis aufrufen
        	if(message=="Connect") {
				
				// boolean checkit = checkname(nameofharry, c);
				//boolean checkit = true; // zum testen
				
				//if(checkit == true){
				//harrysliste = dbconnectTobi.create(nameofharry, ipofharry, latitudeofharry, longitudeofharry, c);
				
        		//harrysliste als JSON-Objekt Testweise erstellen
        		
        		return harrysliste; // Hier geben wir die Userliste aus, muss allerdings noch konkret an Frontend gesendet werden
				}
				
			} else if( message=="Update") {
				
				//harrysliste2 = dbconnectTobi.update(nameofharry, ipofharry2, latitudeofharry2, longitudeofharry2, c);
				
        		//harrysliste2 als JSON-Objekt Testweise erstellen
				
				return harrysliste2;
				
			} else if( message=="Logoff") {
				
				logoff = dbconnectTobi.logoff(nameofharry, c);
				System.out.println(logoff);
				
			}
       
        return "";
    }

    @OnOpen
    public void onOpen(Session peer) {
        LOG.info("Connection opened ...");
        peers.add(peer);
        //Username schon gleich mitnehmen und prŸfen --> checkit funktion in DB
    }

    @OnClose
    public void onClose(Session peer) {
        LOG.info("Connection closed ...");
        peers.remove(peer);
    }
}
