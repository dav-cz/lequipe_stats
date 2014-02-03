package davcz.java.lequipe;

import java.io.PrintWriter;
import java.util.List;

/**
 * Entry point for the application.
 * Outputs the time distribution from the 10km Lequipe race, Paris June 23rd 2013.
 */
public class ResultAnalyzer {
  // Number of seconds in histogram bins.
  private static final int HISTO_BIN_SECS = 30;

  // Number of result pages to browse.
  private static final int NUM_RESULT_PAGES = 277;

  // Path to output file. By default, saved to current working directory.
  private static final String RESULT_OUTPUT_FILE = "results.csv";

  // Save result string to file.
  static private void saveToFile(String filename, String result) {
    try {
      PrintWriter out = new PrintWriter(filename);
      out.print(result);
      out.close();
    } catch (Exception e) {
    }
  }

  public static void main(String [] args) {
    PageFetcher fetcher = new PageFetcher();
    PageParser parser = new PageParser();
    TimeHistogram timeHisto = new TimeHistogram(HISTO_BIN_SECS);

    System.out.println("start");

    // Go over all the result pages.
    // TODO: do this across multi threads to speed things up.
    for (int i = 1; i <= NUM_RESULT_PAGES; ++i) {
      System.out.println("page " + i);

      // Fetch the page and parse its RunnerInfos.
      List<RunnerInfo> infos = parser.parse(fetcher.fetch(i));

      // Add all the RunnerInfos to our time histogram.
      timeHisto.addRunnerInfos(infos);
    }

    // Output the time histogram to a CSV file.
    saveToFile(RESULT_OUTPUT_FILE, timeHisto.printCSV());
  }
}
