package com.mindera.main;

import java.util.Scanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GuessTheWordApplication implements CommandLineRunner {

  private static final Logger LOGGER = LoggerFactory.getLogger(GuessTheWordApplication.class);

  public static void main(String[] args) {
    SpringApplication.run(GuessTheWordApplication.class, args);
  }

  @Override
  public void run(String... args) {

    Scanner scanner = new Scanner(System.in);
    var running = true;


    Player player = new Player(5);
    GuessTheWord guessTheWord = new GuessTheWord(player);

    LOGGER.info("Guess the word: {}", guessTheWord.getVisibleWord());

    do {
      LOGGER.info("Enter a letter: ");
      String input = scanner.next();

      if (input.length() == 1) {
        var status = guessTheWord.guessWord(input.charAt(0));

        if (GuessTheWord.Status.WON.equals(status) || GuessTheWord.Status.LOSE.equals(status)) {
          running = false;
        }

      } else {
        LOGGER.info("Please enter a valid input. E.g: l");
      }

    } while (running);

  }
}
