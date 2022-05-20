package com.mindera.main;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class GuessTheWord {

  public enum Status {
    WON, LOSE, RUNNING
  }

  private static final Logger LOGGER = LoggerFactory.getLogger(GuessTheWord.class);

  private static final Set<String> WORDS_INTERNAL_CACHE_LIST = Set.of("apple", "dog", "cat", "book");

  private final Player player;

  private final Set<Character> usedLetters;

  private StringBuilder visibleWord;

  private String wordToGuess;

  public GuessTheWord(Player player) {
    this(player, WORDS_INTERNAL_CACHE_LIST.stream().findAny().orElse("apple"));
  }

  public GuessTheWord(Player player, String wordToGuess) {
    this.player = player;
    this.usedLetters = new HashSet<>();
    this.wordToGuess = wordToGuess;
    this.visibleWord = new StringBuilder(IntStream
        .range(0, wordToGuess.length())
        .mapToObj(index -> "*")
        .collect(Collectors.joining()));
  }

  //TODO: Implement reset game, later
  public void resetGame() {
    resetGame(WORDS_INTERNAL_CACHE_LIST.stream().findAny().orElse("apple"));
  }

  //TODO: Implement reset game, later
  public void resetGame(String wordToGuess) {
    this.player.resetLife();
    this.usedLetters.clear();
    this.wordToGuess = wordToGuess;
    this.visibleWord = new StringBuilder(IntStream
        .range(0, wordToGuess.length())
        .mapToObj(index -> "*")
        .collect(Collectors.joining()));
  }

  public Status guessWord(char letter) {

    if (isLetterAlreadyUsed(letter)) {
      LOGGER.info("You have already tried this letter");
    } else {

      int[] indexes = SearchAlgorithm.charMatchPosition(wordToGuess, letter);

      if (indexes.length == 0) {

        if (isPlayerLostGame()) {
          LOGGER.info("You have lost the game. The word was {}. Please reset the game", this.wordToGuess);
          return Status.LOSE;
        }

        this.player.loseLife();

        final var remindingLife = this.player.getLife();
        final var visibleWordToShow = this.visibleWord.toString();

        LOGGER.info("Incorrect 1 life lose. {} remaining. The current word is {}", remindingLife, visibleWordToShow);
      } else {
        replaceAsterisks(indexes, letter);

        final var visibleWordToShow = this.visibleWord.toString();

        if (!visibleWordToShow.contains("*")) {
          LOGGER.info("You have won the game. The word was '{}'", visibleWordToShow);
          return Status.WON;
        } else {
          LOGGER.info("Correct. {}", visibleWordToShow);
        }
      }

      this.usedLetters.add(letter);
    }

    return Status.RUNNING;
  }

  private boolean isLetterAlreadyUsed(char letter) {
    return this.usedLetters.stream().anyMatch(it -> it.equals(letter));
  }

  private void replaceAsterisks(int[] indexes, char letter) {
    IntStream.range(0, indexes.length)
        .forEach(index -> this.visibleWord.setCharAt(indexes[index], letter));
  }

  private boolean isPlayerLostGame() {
    return this.player.getLife() == 0;
  }

  public String getVisibleWord() {
    return this.visibleWord.toString();
  }

}
