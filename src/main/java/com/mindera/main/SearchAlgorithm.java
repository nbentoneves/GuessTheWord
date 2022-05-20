package com.mindera.main;

import java.util.HashSet;
import java.util.Set;

public final class SearchAlgorithm {

  private SearchAlgorithm() {
  }

  public static int[] charMatchPosition(String word, char letter) {
    return charMatchPosition(word, letter, 0, new HashSet<>());
  }

  //TODO: Check if Boyer Moore Search Algorithm is more efficient
  private static int[] charMatchPosition(String word, char letter, int firstIndex, Set<Integer> storeIndexesMatch) {

    if (word.length() == 0) {
      return new int[0];
    }

    if (firstIndex == word.length() - 1) {
      return storeIndexesMatch
          .stream()
          .mapToInt(Integer::intValue)
          .toArray();
    }

    int result = word.indexOf(letter, firstIndex);
    int newLastIndex = firstIndex + 1;

    if (result != -1) {
      storeIndexesMatch.add(result);
    }

    return charMatchPosition(word, letter, newLastIndex, storeIndexesMatch);
  }

}
