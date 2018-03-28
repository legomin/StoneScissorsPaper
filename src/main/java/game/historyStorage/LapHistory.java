package game.historyStorage;

import java.util.List;

import game.Motion;

/**
 * Interface for storing laps history by patterns
 *
 * history consist of pairs <pattern, statistic>, where:
 *
 * pattern - List<Lap> - history depth, where
 *  0 - List index - previous lap, 1 - before previous and so on
 *  Lap - just pair of userMotion & comp motion
 *
 * statistic - history, what was next after pattern situation, how user behaved
 *
 */
public interface LapHistory {

  /**
   * Saves new game lap motion to history
   *
   * @param pattern - pattern
   * @param userMotion - user motion
   */
  void saveLap(List<Lap> pattern, Motion userMotion);

  /**
   * Get pattern history
   *
   * @return - List of statistics to current game state
   */
  Statistic getStatistics(List<Lap> pattern);

  /**
   * Clears current history
   */
  void clear();

}
