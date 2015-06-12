package buschfunkpackage;

// FRAGEN: --> R�ckgabewert bei public String onMessage richtig? Warum klappt das Erstellen eines ClientThreads hier nicht? Wie kann man die Werte aus dem ClientThread hier wieder zur�ckgeben?

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
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

import buschfunkpackage.Server2.ClientThread;

@ServerEndpoint("/atpendpoint")
public class ATPWSEndpoint {

    private static final Logger LOG = Logger.getLogger(ATPWSEndpoint.class.getName());
    private static Set<Session> peers = Collections.synchronizedSet(new HashSet<Session>());

    
 // Das Frontend ruft die "onMessage" Methode auf und �bergibt die Message sowie die Userdaten
    @OnMessage // FRAGE: R�ckgabewert public List<String> richtig? Habe ich angenommen, da wir ja eine Liste zur�ckgeben wollen
    
    public List<String> onMessage(String message, String nameofharry, String ipofharry, double longitudeofharry, double latitudeofharry) throws SQLException, IOException {
        if (message != null) {
            
        	
            	ClientThread t = new ClientThread(message, nameofharry, ipofharry, longitudeofharry, latitudeofharry);  //muss noch auf generische Bezeichnungen ge�ndert werden (z.B. username statt nameofharry)
				Server2.getAl().add(t);						// save it in the ArrayList
				t.start();									// startet den Threat
            	return t; //irgendwas m�ssen wir aus dem ClientThread wieder zur�ckgeben, aber wie?
            	
            
            
        }
        return "";
    }

    @OnOpen
    public void onOpen(Session peer) {
        LOG.info("Connection opened ...");
        peers.add(peer);
    }

    @OnClose
    public void onClose(Session peer) {
        LOG.info("Connection closed ...");
        peers.remove(peer);
    }
}
