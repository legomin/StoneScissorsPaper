package game.historyStorage.inMemoryImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import game.Motion;
import game.historyStorage.Lap;
import game.historyStorage.LapHistory;
import game.historyStorage.Statistic;

/**
 * Just-In-HashMap `LapHistory` implementation
 *
 * !!NOT FOR CONCURRENT USAGE!!
 */
public class InMemoryLapHistoryImpl implements LapHistory {
  private Map<List<Lap>, Statistic> history = new HashMap<>();

  @Override
  public void saveLap(final List<Lap> pattern, final Motion userMotion) {
    if (history.containsKey(pattern)) {
      history.get(pattern).updateStat(userMotion);
    } else {
      Statistic stat = new Statistic();
      stat.updateStat(userMotion);
      history.put(pattern, stat);
    }
  }

 @Override
  public Statistic getStatistics(List<Lap> pattern) {
    Statistic statistic = history.get(pattern);
    if (statistic == null) {
      return new Statistic();
    } else {
      return statistic;
    }
  }

  @Override
  public void clear() {
    history = new HashMap<>();
  }
}
