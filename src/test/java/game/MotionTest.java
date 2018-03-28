package game;

import static game.Motion.PAPER;
import static game.Motion.SCISSORS;
import static game.Motion.STONE;
import static game.Motion.compare;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class MotionTest {

  @Test
  public void testCompare() {
    assertEquals("Unexpected result", 0, compare(STONE, STONE));
    assertEquals("Unexpected result", 1, compare(STONE, SCISSORS));
    assertEquals("Unexpected result", -1, compare(STONE, PAPER));

    assertEquals("Unexpected result", -1, compare(SCISSORS, STONE));
    assertEquals("Unexpected result", 0, compare(SCISSORS, SCISSORS));
    assertEquals("Unexpected result", 1, compare(SCISSORS, PAPER));

    assertEquals("Unexpected result", 1, compare(PAPER, STONE));
    assertEquals("Unexpected result", -1, compare(PAPER, SCISSORS));
    assertEquals("Unexpected result", 0, compare(PAPER, PAPER));
  }

}