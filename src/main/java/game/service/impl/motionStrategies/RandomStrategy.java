package game.service.impl.motionStrategies;

import java.util.List;
import java.util.function.Function;

import game.Motion;
import game.historyStorage.Statistic;

/**
 * Strategy, when next motion produced just randomly
 */
public class RandomStrategy implements Function<List<Statistic>, Motion> {

  @Override
  public Motion apply(List<Statistic> statistics) {
    return Motion.values()[(int) (Math.random() * 2.9)];
  }
}
