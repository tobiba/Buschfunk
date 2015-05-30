package buschfunkpackage;

public class BerechnungEntfernung {
		
		static double lat1 = 49.4746841;
		static double long1 = 8.534726599999999;
		static double lat2 = 49.495909;
		static double long2 = 8.420453;
		
		
		
		public static double distanceInKm(double lat1, double lon1, double lat2, double lon2) {
		    int radius = 6371;

		    double lat = Math.toRadians(lat2 - lat1);
		    double lon = Math.toRadians(lon2- lon1);

		    double a = Math.sin(lat / 2) * Math.sin(lat / 2) + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * Math.sin(lon / 2) * Math.sin(lon / 2);
		    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
		    double d = radius * c;

		    return Math.abs(d);
		}

		public static void main(String[] args) {
			// TODO Auto-generated method stub

			if(distanceInKm(lat1, long1, lat2, long2)*1000 < 10000)
				System.out.println(distanceInKm(lat1, long1, lat2, long2)*1000);
			else System.out.println("Außer Reichweite");
		}


}
