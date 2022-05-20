package com.mindera.main;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class SearchAlgorithmTest {

  @Test
  void testSearchLetterUsingEmptyWord() {

    assertEquals(0, SearchAlgorithm.charMatchPosition("", 'A').length);

  }

  @ParameterizedTest
  @CsvSource({
      "banana,a,1:3:5",
      "apple,p,1:2",
      "dog,g,2",
  })
  void testSearchLetter(String word, String letter, String result) {

    var resultList = Arrays.stream(result.split(":")).map(Integer::valueOf)
        .mapToInt(Integer::intValue)
        .toArray();

    assertArrayEquals(resultList, SearchAlgorithm.charMatchPosition(word, letter.charAt(0)),
        "expected index array not match");

  }

}
