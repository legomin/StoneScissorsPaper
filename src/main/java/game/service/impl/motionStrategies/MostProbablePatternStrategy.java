package game.service.impl.motionStrategies;

import java.util.List;

import game.Motion;
import game.historyStorage.Statistic;

/**
 * Strategy, when next motion produced as the most probable from all patterns
 * if there are no any stats it behaves as `RandomStrategy`
 */
public class MostProbablePatternStrategy extends RandomStrategy {

  @Override
  public Motion apply(List<Statistic> statistics) {
    Motion mostProbableMotion = super.apply(statistics);
    if (statistics == null || statistics.isEmpty()) {
      return mostProbableMotion;
    }

    double probability = 0.0;
    for (final Statistic statistic : statistics) {
      final double newProbability = statistic.getSuggestedMotionProbability();
      if (newProbability > probability) {
        probability = newProbability;
        mostProbableMotion = statistic.getSuggestedMotion();
      }
    }
    return Motion.getWinner(mostProbableMotion);
  }
}
