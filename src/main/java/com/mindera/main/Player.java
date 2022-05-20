package com.mindera.main;

import java.util.StringJoiner;

public final class Player {

  private final int initLife;

  private int life;

  public Player(int initLife) {
    this.initLife = initLife;
    this.life = initLife;
  }

  public void loseLife() {
    if (this.life > 0) {
      this.life--;
    }
  }

  public void resetLife() {
    this.life = this.initLife;
  }

  public int getLife() {
    return life;
  }

  @Override
  public String toString() {
    return new StringJoiner(", ", Player.class.getSimpleName() + "[", "]")
        .add("life=" + life)
        .toString();
  }
}
