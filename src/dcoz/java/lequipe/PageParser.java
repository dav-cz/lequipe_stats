package dcoz.java.lequipe;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Parses a result page into a list of RunnerInfos.
 */
public class PageParser {
  // Pattern to extract a runner's category.
  private final Pattern CATEGORY_PATTERN = Pattern.compile(
      "<td class=\"td5\">(.*)</td>");

  // Pattern to extract a runner's real time (hours, minutes, seconds).
  private final Pattern TIME_PATTERN = Pattern.compile(
      "<td class=\"td6\">(\\d\\d):(\\d\\d):(\\d\\d)</td>");

  public PageParser() {}

  // Converts a category string as seen on the website into the relevant category enum.
  RunnerInfo.Category stringToCat(String catStr) {
    if (catStr.contains("Femme")) {
      return RunnerInfo.Category.WOMEN;
    } else {
      return RunnerInfo.Category.MEN;
    }
  }

  public List<RunnerInfo> parse(List<String> strings) {
    List<RunnerInfo> infos = new ArrayList<RunnerInfo>();

    RunnerInfo info = new RunnerInfo();

    // Browse the page line.
    for (String line : strings) {
      // Extract the category if possible.
      Matcher categoryMatcher = CATEGORY_PATTERN.matcher(line);
      if (categoryMatcher.find()) {
        info.category = stringToCat(categoryMatcher.group(1));
      }

      // Extract time.
      Matcher timeMatcher = TIME_PATTERN.matcher(line);
      if (timeMatcher.find()) {
        assert timeMatcher.groupCount() == 4;

        int hours = Integer.parseInt(timeMatcher.group(1));
        int minutes = Integer.parseInt(timeMatcher.group(2));
        int seconds = Integer.parseInt(timeMatcher.group(3));
        info.timeSec = hours * 3600 + minutes * 60 + seconds;

        // Here, we assume the category was set -- worse case we default to MEN.
        // We add that RunnerInfo to the list, and initialize a new one to work on.
        infos.add(info);
        info = new RunnerInfo();
      }
    }
    return infos;
  }
}
