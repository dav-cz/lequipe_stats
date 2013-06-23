package dcoz.java.lequipe;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PageParser {
  private final Pattern CATEGORY_PATTERN = Pattern.compile(
      "<td class=\"td5\">(.*)</td>");

  private final Pattern TIME_PATTERN = Pattern.compile(
      "<td class=\"td6\">(\\d\\d):(\\d\\d):(\\d\\d)</td>");

  public PageParser() {
  }

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
    for (String line : strings) {
      Matcher categoryMatcher = CATEGORY_PATTERN.matcher(line);
      if (categoryMatcher.find()) {
        info.category = stringToCat(categoryMatcher.group(1));
      }

      Matcher timeMatcher = TIME_PATTERN.matcher(line);
      if (timeMatcher.find()) {
        assert timeMatcher.groupCount() == 4;

        int hours = Integer.parseInt(timeMatcher.group(1));
        int minutes = Integer.parseInt(timeMatcher.group(2));
        int seconds = Integer.parseInt(timeMatcher.group(3));
        info.timeSec = hours * 3600 + minutes * 60 + seconds;

        infos.add(info);
        info = new RunnerInfo();
      }
    }
    return infos;
  }
}
