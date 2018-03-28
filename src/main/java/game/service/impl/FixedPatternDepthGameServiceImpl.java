package game.service.impl;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;

import game.Motion;
import game.historyStorage.Lap;
import game.historyStorage.LapHistory;
import game.historyStorage.Statistic;
import game.service.GameService;

/**
 * Implementation, when for each game state previous N laps analyzed for producing next motion
 *
 * saving laps:
 *   for each state previous lap stat saved, prev + before prev, .. , prev + .. + N laps ago
 *
 * analyzing:
 *   analyzing all depth patterns from 1 to N by `motionStrategy` implementations
 *
 */
public class FixedPatternDepthGameServiceImpl implements GameService {

  private static final int DEFAULT_DEPTH = 5;

  private final int depth;
  private final Function<List<Statistic>, Motion> motionStartegy;
  private final LapHistory history;

  //state variable
  private List<Lap> currentPattern;

  public FixedPatternDepthGameServiceImpl(int depth, Function<List<Statistic>, Motion> motionStartegy,
    final LapHistory history) {
    this.depth = depth == 0 ? DEFAULT_DEPTH : depth;
    this.motionStartegy = requireNonNull(motionStartegy);
    this.history = requireNonNull(history);
    this.currentPattern = new LinkedList<>();
  }

  @Override
  public Motion getNextMotion(Motion userMotion) {
    List<Statistic> statisticsBySubPatterns = getStatisticsBySubPatterns();
    saveStatisticsBySubPatterns(userMotion);

    final Motion computerMotion = motionStartegy.apply(statisticsBySubPatterns);
    updateCurrentPattern(new Lap(userMotion, computerMotion));

    return computerMotion;
  }

  @Override
  public void initialize() {
    history.clear();
    currentPattern = new LinkedList<>();
  }

  private List<Statistic> getStatisticsBySubPatterns() {
    List<Lap> subPattern = new ArrayList<>();
    List<Statistic> result = new ArrayList<>();
    for (final Lap lap : currentPattern) {
      subPattern.add(lap);
      result.add(history.getStatistics(subPattern));
    }
    return result;
  }

  private void saveStatisticsBySubPatterns(final Motion userMotion) {
    List<Lap> subPattern = new ArrayList<>();
    for (final Lap lap : currentPattern) {
      subPattern.add(lap);
      history.saveLap(subPattern, userMotion);
    }
  }

  private void updateCurrentPattern(final Lap lap) {
    currentPattern.add(0, lap);
    if (currentPattern.size() >= depth) {
      currentPattern.remove(currentPattern.size() - 1);
    }
  }
}
