package buschfunkpackage;

// weitere, uns bekannte, To-Do's --> Befehle von Webseite empfangen
// Fragen: --> Warum speichert die Update-Funktion die Daten in eine zweite Liste (harrysliste2)?


import java.io.*;
import java.net.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.text.SimpleDateFormat;
import java.util.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.List;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;


/*
 * The server that can be run both as a console application or a GUI
 */
public class Server2 {
	

	//private static int uniqueId; // a unique ID for each connection threat
	public static String nameofharry = "Harry";
	public static String ipofharry = "192.168.0.4";
	public static double longitudeofharry = 45.374635;
	public static double latitudeofharry = 89.928374;
	public static String ipofharry2 = "192.168.0.80";
	public static double longitudeofharry2 = 8.534478;
	public static double  latitudeofharry2 = 49.474235;
	public static List<String> harrysliste = new ArrayList<String>();
	public static List<String> harrysliste2 = new ArrayList<String>();
	public static boolean logoff;
	public Connection c;
	
	// a unique ID for each connection
	private static int uniqueId;
	// an ArrayList to keep the list of the Client
	//private ArrayList<ClientThread> al;
	// to display time
	private SimpleDateFormat sdf;
	// the port number to listen for connection
	private int port;
	// the boolean that will be turned of to stop the server
	private boolean keepGoing;
	

	/*
	 *  server constructor that receive the port to listen to for connection as parameter
	 *  in console
	 */


	public static void main(String[] args) throws SQLException, NullPointerException, IOException {
		
		 Connection c = null;
	       
	       c = DriverManager
	               .getConnection("jdbc:postgresql://localhost:5432/Buschfunk",
	               "postgres", "postgres");	
	
		// start server on port 1500 unless a PortNumber is specified 
		int portNumber = 1500;
		switch(args.length) {
			case 1:
				try {
					portNumber = Integer.parseInt(args[0]);
				}
				catch(Exception e) {
					System.out.println("Invalid port number.");
					System.out.println("Usage is: > java Server [portNumber]");
					return;
				}
			case 0:
				break;
			default:
				System.out.println("Usage is: > java Server [portNumber]");
				return;
				
		}
				
		/**harrysliste.add("Marie");
	    harrysliste.add("192.168.0.4");
	    harrysliste.add("289");
	    harrysliste2.add("Hubert");
	    harrysliste2.add("192.168.0.3");
	    harrysliste2.add("488"); */
	       
		// create a server object and start it
		Server2 server = new Server2(portNumber);
		server.start();
	}
	

	public void start() throws IOException, SQLException {
		keepGoing = true;
		/* create socket server and wait for connection requests */
		try 
		{
			// the socket used by the server
			ServerSocket serverSocket = new ServerSocket(port);

			// infinite loop to wait for connections
			while(keepGoing) 
			{	
				Socket socket = serverSocket.accept();  	// accept connection
				// if I was asked to stop
				if(!keepGoing)
					break;
				
				// !!!!! Hier müssen wir nun irgendwie sicherstellen, dass auf Funktion onMessage gewartet wird
				
				
			//	ClientThread t = new ClientThread(socket, nameofharry, ipofharry, longitudeofharry, latitudeofharry, c);  //muss noch auf generische Bezeichnungen geändert werden (z.B. username statt nameofharry)
				// getAl().add(t);									// save it in the ArrayList
				// t.start();									// startet den Threat
			}
			// falls gestoppt, werden alle Threats geschlossen
			try {											
				serverSocket.close();
				}
				/**for(int i = 0; i < getAl().size(); ++i) {
					ClientThread tc = getAl().get(i);
					try {
					tc.sInput.close();
					tc.sOutput.close();
					tc.socket.close();
					}*/
					catch(IOException ioE) {
						// not much I can do
					}
				}
			
			catch(Exception e) {
				System.out.println("Exception closing the server and clients: " + e);
				}
			
			finally {
				System.out.println("Ansonsten Fehler, aber warum?");
			
				
			}
		
	}		
	
	


	/** One instance of this thread will run for each client */
	/**class ClientThread extends Thread {
		// the socket where to listen/talk
		Socket socket;
		ObjectInputStream sInput;
		ObjectOutputStream sOutput;
		// my unique id (easier for deconnection)
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

		// Konstruktor
		List<String> ClientThread(String message, String username, String ipofharry, double longitudeofharry, double latitudeofharry) throws SQLException, IOException {
			// a unique id
			id = ++uniqueId;
			//this.socket = socket;
			this.longitude = longitudeofharry;
			this.latitude = latitudeofharry;
			this.ip = ipofharry;
			this.message = message;
			//this.c = c;
			
			if(message=="Connect") {
				
				// boolean checkit = checkname(nameofharry, c);
				boolean checkit = true; // zum testen
				
				if(checkit == true){
				harrysliste = dbconnectTobi.create(nameofharry, ipofharry, latitudeofharry, longitudeofharry, c);
				return harrysliste; // Hier geben wir die Userliste aus, muss allerdings noch konkret an Frontend gesendet werden
				}
				
			} else if( message=="Update") {
				
				harrysliste2 = dbconnectTobi.update(nameofharry, ipofharry2, latitudeofharry2, longitudeofharry2, c);
				return harrysliste2;
				
			} else if( message=="Logoff") {
				
				logoff = dbconnectTobi.logoff(nameofharry, c);
				System.out.println(logoff);
				
			} 
			return null;
			
		
		     }
		} // Ende von Konstruktor ClientThread
		
		
		
		// try to close everything
		private void close() {
			// try to close the connection
			try {
				if(sOutput != null) sOutput.close();
			}
			catch(Exception e) {}
			try {
				if(sInput != null) sInput.close();
			}
			catch(Exception e) {};
			try {
				if(socket != null) socket.close();
			}
			catch (Exception e) {}
		}



		public static ArrayList<ClientThread> getAl() {
			return al;
		}



		public void setAl(ArrayList<ClientThread> al) {
			this.al = al;
		}*/
		
		
}


