package com.mindera.main;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class PlayerTest {

  @Test
  void testPlayerLoseLife() {
    Player player = new Player(3);
    player.loseLife();
    player.loseLife();

    assertEquals(1, player.getLife());

  }

  @Test
  void testPlayerCanNotLoseLifeIfLifeIsZero() {

    Player player = new Player(0);
    player.loseLife();

    assertEquals(0, player.getLife());

  }

  @Test
  void testPlayerResetLife() {

    Player player = new Player(5);
    player.loseLife();
    player.resetLife();

    assertEquals(5, player.getLife());

  }

}
