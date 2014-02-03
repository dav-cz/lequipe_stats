package davcz.java.lequipe;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Class that fetches a given lequipe's result page line by line.
 */
public class PageFetcher {
  private static final String URL_BASE =
      "http://www.aso.fr/massevents/resultats/ajax.php?course=dke13&langue=fr&version=2&action=search&fields=&limiter%5Bnumpage%5D=";

  PageFetcher() {}

  public List<String> fetch(int pageNumber) {
    List<String> strings = new ArrayList<String>();
    // Build full page address.
    String address = URL_BASE + pageNumber;
    URL url;
    HttpURLConnection conn;
    BufferedReader rd;
    String line;

    // Fetch the data from the website and read it line by line.
    try {
       url = new URL(address);
       conn = (HttpURLConnection) url.openConnection();
       conn.setRequestMethod("GET");
       rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
       while ((line = rd.readLine()) != null) {
         strings.add(line);
       }
       rd.close();
    } catch (Exception e) {
       e.printStackTrace();
    }
    return strings;
  }
}
