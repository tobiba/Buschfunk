package buschfunkpackage;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.List;

public class dbconnect {
	
   public static void main(String args[]) throws SQLException, NullPointerException{
         
	   
       Connection c = null;
       
       c = DriverManager
               .getConnection("jdbc:postgresql://localhost:5432/Buschfunk",
               "postgres", "postgres");
       
       String nameofharry = "Harry";
       String ipofharry = "192.168.0.4";
       double longitudeofharry = 45.374635;
       double latitudeofharry = 89.928374;
       String ipofharry2 = "192.168.0.80";
       double longitudeofharry2 = 8.534478;
       double  latitudeofharry2 = 49.474235;
       List<String> harrysliste = new ArrayList<String>();
       List<String> harrysliste2 = new ArrayList<String>();
       boolean logoff;
       
       boolean checkit = checkname(nameofharry, c);
       
       if(checkit == true){
    	   harrysliste = create(nameofharry, ipofharry, latitudeofharry, longitudeofharry, c);
    	   System.out.println(harrysliste);
    	   
    	   
    	   System.out.println("Harry bewegt sich - So ein Depp");
    	   
    	   harrysliste2 = update(nameofharry, ipofharry2, latitudeofharry2, longitudeofharry2, c);
    	   System.out.println(harrysliste2);
    	   
    	   logoff = logoff(nameofharry, c);
    	   System.out.println(logoff);
       }

       
       c.close();

   }
   
   
	public static boolean checkname (String username, Connection c) throws SQLException{
        Statement stmtcheckname = null;
        String namecheckname = null;
        
        stmtcheckname = c.createStatement();
       
        ResultSet rscheckname = stmtcheckname.executeQuery( "SELECT name FROM benutzer WHERE name LIKE '"+ username +"';" );
        
        while ( rscheckname.next() ) {
        	namecheckname = rscheckname.getString("name");
        }
        
        rscheckname.close();
        stmtcheckname.close();
        
        if(namecheckname == null){
        	return true;
        }else{
        	return false;
        }
        
	}
	
    public static Boolean logoff(String username, Connection c) throws SQLException{
        Statement stmtlogoff = null;
        String namelogoff = null;
        
        stmtlogoff = c.createStatement();
        
        String sqllogoff = "DELETE FROM BENUTZER WHERE name LIKE '"+ username + "';" ;
        stmtlogoff.executeUpdate(sqllogoff);
 
        ResultSet rslogoff = stmtlogoff.executeQuery( "SELECT name FROM BENUTZER WHERE name LIKE '"+ username +"';" );
        
        while ( rslogoff.next() ) {
        	namelogoff = rslogoff.getString("name");
        }
        
        rslogoff.close();
        stmtlogoff.close();
        
        if(namelogoff == null){
        	return true;
        }else{
        	return false;
        }
    }
    
    public static List<String> create (String username, String ip, double latitude, double longitude, Connection c) throws SQLException{
        Statement stmtcreate = null;
    	String namecreate = null;
    	String ipcreate = null;
    	double longitudecreate = 0;
    	double latitudecreate = 0;
    	List<String> userlistcreate = new ArrayList<String>();
    	
    	stmtcreate = c.createStatement();
    	
        String sqlcreate = "INSERT INTO benutzer(name, ip, latitude, longitude)" +
                "VALUES ('" + username + "', '" + ip + "', " + latitude + ", " + longitude + ");";
        stmtcreate.executeUpdate(sqlcreate);
        
        ResultSet rscreate = stmtcreate.executeQuery( "SELECT name, ip, latitude, longitude FROM BENUTZER WHERE name NOT LIKE '" + username + "';" );
    
        while ( rscreate.next() ) {
        	namecreate = rscreate.getString("name");
        	ipcreate = rscreate.getString("ip");
        	latitudecreate = rscreate.getDouble("latitude");
        	longitudecreate = rscreate.getDouble("longitude");
        	
        	if(userlistcreate.size() < 60){
    		    int radius = 6371;

    		    double lat = Math.toRadians(latitudecreate - latitude);
    		    double lon = Math.toRadians(longitudecreate- longitude);

    		    double a = Math.sin(lat / 2) * Math.sin(lat / 2) + Math.cos(Math.toRadians(latitude)) * Math.cos(Math.toRadians(latitudecreate)) * Math.sin(lon / 2) * Math.sin(lon / 2);
    		    double c1 = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
    		    double d = radius * c1;
    		    d = d * 1000; // KM in M
    		    
    		    if(d <= 500){
    		    	String distance= new Double(d).toString();
    		    	
    		    	userlistcreate.add(namecreate);
    		    	userlistcreate.add(ipcreate);
    		    	userlistcreate.add(distance);
    		    }
        	}
        }
        
        rscreate.close();
        stmtcreate.close();
        
        return userlistcreate;
        
        //Hier noch anstatt der Prüfung ob eingefügt die Berechnung machen und dann Liste ausgeben - DZ
    }
    
    public static List<String> update(String username, String ip, double latitude, double longitude, Connection c) throws SQLException, NullPointerException{
        Statement stmtupdate = null;
        String nameupdate;
        String ipupdate = null;
        double longitudeupdate = 0;
        double latitudeupdate = 0;
        List<String> userlistupdate = new ArrayList<String>();
        
        stmtupdate = c.createStatement();
        
        String sql = "UPDATE benutzer SET ip = '" + ip + "', latitude = " + latitude + ", longitude = " + longitude + " WHERE name LIKE '" + username + "';";
        stmtupdate.executeUpdate(sql);
        
        ResultSet rsupdate = stmtupdate.executeQuery( "SELECT name, ip, latitude, longitude FROM BENUTZER WHERE name NOT LIKE '" + username + "';" );
        
        while ( rsupdate.next() ) {
        	nameupdate = rsupdate.getString("name");
        	ipupdate = rsupdate.getString("ip");
        	latitudeupdate = rsupdate.getDouble("latitude");
        	longitudeupdate = rsupdate.getDouble("longitude");
        	
        	if(userlistupdate.size() < 60){
    		    int radius = 6371;

    		    double lat = Math.toRadians(latitudeupdate - latitude);
    		    double lon = Math.toRadians(longitudeupdate- longitude);

    		    double a = Math.sin(lat / 2) * Math.sin(lat / 2) + Math.cos(Math.toRadians(latitude)) * Math.cos(Math.toRadians(latitudeupdate)) * Math.sin(lon / 2) * Math.sin(lon / 2);
    		    double c1 = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
    		    double d = radius * c1;
    		    d = d * 1000; // KM in M
    		    
    		    if(d <= 500){
    		    	String distanceupdate = new Double(d).toString();
    		    	
    		    	userlistupdate.add(nameupdate);
    		    	userlistupdate.add(ipupdate);
    		    	userlistupdate.add(distanceupdate);
    		    }
        	}
        }
        
        rsupdate.close();
        stmtupdate.close();
        
        return userlistupdate;
    }
	
}
