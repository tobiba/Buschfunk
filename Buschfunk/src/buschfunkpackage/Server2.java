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
	private ArrayList<ClientThread> al;
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
	public Server2(int port) {
		this(port, null);
	}
	

	
	public Server2(int port2, Object object) {
		// TODO Auto-generated constructor stub
	}



	public void start() {
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
				
				// HIER wollen wir die Daten von der Webseite lesen
				//username= Eingabe von Webseite
				//ip = Eingabe von Webseite
				// usw.
				
				
				ClientThread t = new ClientThread(socket, nameofharry, ipofharry, longitudeofharry, latitudeofharry, c);  //muss noch auf generische Bezeichnungen geändert werden (z.B. username statt nameofharry)
				al.add(t);									// save it in the ArrayList
				t.start();									// startet den Threat
			}
			// I was asked to stop
			try {											// falls gestoppt, werden alle Threats geschlossen
				serverSocket.close();
				for(int i = 0; i < al.size(); ++i) {
					ClientThread tc = al.get(i);
					try {
					tc.sInput.close();
					tc.sOutput.close();
					tc.socket.close();
					}
					catch(IOException ioE) {
						// not much I can do
					}
				}
			}
			catch(Exception e) {
				System.out.println("Exception closing the server and clients: " + e);}
			
		
	}		
	
	
	public static void main(String[] args) throws SQLException, NullPointerException {
		
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
				
		harrysliste.add("Marie");
	    harrysliste.add("192.168.0.4");
	    harrysliste.add("289");
	    harrysliste2.add("Hubert");
	    harrysliste2.add("192.168.0.3");
	    harrysliste2.add("488"); 
	       
		// create a server object and start it
		Server2 server = new Server2(portNumber);
		server.start();
	}

	/** One instance of this thread will run for each client */
	class ClientThread extends Thread {
		// the socket where to listen/talk
		Socket socket;
		ObjectInputStream sInput;
		ObjectOutputStream sOutput;
		// my unique id (easier for deconnection)
		int id;
		// the Username of the Client
		String username;
		// the only type of message a will receive
		ChatMessage cm;
		// the date I connect
		String date;
		double longitude;
		double latitude;
		String ip;
		Connection c; // zum testen

		// Konstruktor
		ClientThread(Socket socket, String username, String ipofharry, double longitudeofharry, double latitudeofharry, Connection c) throws SQLException {
			// a unique id
			id = ++uniqueId;
			this.socket = socket;
			this.longitude = longitudeofharry;
			this.latitude = latitudeofharry;
			this.ip = ipofharry;
			this.c = c;
			
			
			
			// boolean checkit = checkname(nameofharry, c);
			boolean checkit = true; // zum testen
		       
		     if(checkit == true){
		  	   harrysliste = dbconnectTobi.create(nameofharry, ipofharry, latitudeofharry, longitudeofharry, c);
		   	   System.out.println(harrysliste); // Hier geben wir die Userliste aus
		     }
		     
		     while(true){
		    	   if("Frontend drückt Status Aktualisieren"){
			    	   harrysliste2 = dbconnectTobi.update(nameofharry, ipofharry2, latitudeofharry2, longitudeofharry2, c);
			    	   System.out.println(harrysliste2);
		    	   }	
	    	   
		    	   if("Frontend drückt Ausloggen oder schließt das Fenster etc"){
			    	   
			    	   logoff = dbconnectTobi.logoff(nameofharry, c);
			    	   System.out.println(logoff);
			    	   socket.close();
		    	   }
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
	}
}


