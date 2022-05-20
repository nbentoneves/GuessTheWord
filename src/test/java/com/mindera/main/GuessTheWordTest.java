package com.mindera.main;

import static org.junit.jupiter.api.Assertions.assertEquals;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;

class GuessTheWordTest {

  private final Logger guessTheWordLogger = (Logger) LoggerFactory.getLogger(GuessTheWord.class);

  private final ListAppender<ILoggingEvent> listAppender = new ListAppender<>();

  @BeforeEach
  void setUp() {
    listAppender.start();
    guessTheWordLogger.addAppender(listAppender);
  }

  @Test
  @DisplayName("Test player starts the game with zero life")
  void testPlayerStartsWithZeroLife() {

    Player player = new Player(0);
    GuessTheWord guessTheWord = new GuessTheWord(player, "cat");
    guessTheWord.guessWord('p');

    assertEquals("You have lost the game. The word was cat. Please reset the game",
        listAppender.list.get(0).getFormattedMessage());

  }

  @Test
  @DisplayName("When a player won the game but loses two lives, and it starts with four lives")
  void testPlayerWonTheGameButWrongTwoTimes() {

    Player player = new Player(4);
    GuessTheWord guessTheWord = new GuessTheWord(player, "apple");
    guessTheWord.guessWord('a');
    guessTheWord.guessWord('d');
    guessTheWord.guessWord('z');
    guessTheWord.guessWord('p');
    guessTheWord.guessWord('l');
    guessTheWord.guessWord('e');

    assertEquals("Correct. a****",
        listAppender.list.get(0).getFormattedMessage());
    assertEquals("Incorrect 1 life lose. 3 remaining. The current word is a****",
        listAppender.list.get(1).getFormattedMessage());
    assertEquals("Incorrect 1 life lose. 2 remaining. The current word is a****",
        listAppender.list.get(2).getFormattedMessage());
    assertEquals("Correct. app**",
        listAppender.list.get(3).getFormattedMessage());
    assertEquals("Correct. appl*",
        listAppender.list.get(4).getFormattedMessage());
    assertEquals("You have won the game. The word was 'apple'",
        listAppender.list.get(5).getFormattedMessage());

  }

  @Test
  @DisplayName("When a player lost the game")
  void testPlayerLostTheGame() {

    Player player = new Player(2);
    GuessTheWord guessTheWord = new GuessTheWord(player, "game");
    guessTheWord.guessWord('a');
    guessTheWord.guessWord('p');
    guessTheWord.guessWord('h');
    guessTheWord.guessWord('z');

    assertEquals("Correct. *a**",
        listAppender.list.get(0).getFormattedMessage());
    assertEquals("Incorrect 1 life lose. 1 remaining. The current word is *a**",
        listAppender.list.get(1).getFormattedMessage());
    assertEquals("Incorrect 1 life lose. 0 remaining. The current word is *a**",
        listAppender.list.get(2).getFormattedMessage());
    assertEquals("You have lost the game. The word was game. Please reset the game",
        listAppender.list.get(3).getFormattedMessage());

  }

  @Test
  @DisplayName("When a player won the game but loses one life, and inputs a repeated letter, it starts with five lives")
  void testPlayerWonTheGameButWrongOneTimesAndUseRepeatedLetters() {

    Player player = new Player(5);
    GuessTheWord guessTheWord = new GuessTheWord(player, "book");
    guessTheWord.guessWord('z');
    guessTheWord.guessWord('o');
    guessTheWord.guessWord('k');
    guessTheWord.guessWord('k');
    guessTheWord.guessWord('b');

    assertEquals("Incorrect 1 life lose. 4 remaining. The current word is ****",
        listAppender.list.get(0).getFormattedMessage());
    assertEquals("Correct. *oo*",
        listAppender.list.get(1).getFormattedMessage());
    assertEquals("Correct. *ook",
        listAppender.list.get(2).getFormattedMessage());
    assertEquals("You have already tried this letter",
        listAppender.list.get(3).getFormattedMessage());
    assertEquals("You have won the game. The word was 'book'",
        listAppender.list.get(4).getFormattedMessage());

  }

  @Test
  @DisplayName("When a player won the game but loses all lives, and inputs a repeated letter at it's last chance, it starts with 1 life")
  void testPlayerWonTheGameButWrongOneTimeAndUseRepeatedLettersLastChanceToWin() {

    Player player = new Player(1);
    GuessTheWord guessTheWord = new GuessTheWord(player, "cat");
    guessTheWord.guessWord('z');
    guessTheWord.guessWord('c');
    guessTheWord.guessWord('a');
    guessTheWord.guessWord('a');
    guessTheWord.guessWord('t');

    assertEquals("Incorrect 1 life lose. 0 remaining. The current word is ***",
        listAppender.list.get(0).getFormattedMessage());
    assertEquals("Correct. c**",
        listAppender.list.get(1).getFormattedMessage());
    assertEquals("Correct. ca*",
        listAppender.list.get(2).getFormattedMessage());
    assertEquals("You have already tried this letter",
        listAppender.list.get(3).getFormattedMessage());
    assertEquals("You have won the game. The word was 'cat'",
        listAppender.list.get(4).getFormattedMessage());

  }

}
