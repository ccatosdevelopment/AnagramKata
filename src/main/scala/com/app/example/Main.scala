package com.app.example

import scala.io.{BufferedSource, Source}

object Main {
  def main(args: Array[String]): Unit = {

    // (1) Read in wordlist.txt and store all words in a Vector[String]
    val wordListFilepath: String = "src/main/resources/word_list.txt"
    val wordListFile: BufferedSource = Source.fromFile(wordListFilepath)

    val readWordsFromFile = (file: BufferedSource) => {
      for {
        linesInFile <- file.getLines().toVector
        wordsInLine <- linesInFile.split(" +")
      } yield wordsInLine
    }

    val allWords: Vector[String] = readWordsFromFile(wordListFile)

//--------------------------------------------------------------------------------------------------------------------//

    // (2) Filter potential valid words:
    val inputAnagram: String = "documenting"

    val filterPotentialValidWords = (wordList: Vector[String], fullAnagram: String) => {
      for (word <- wordList if word.intersect(fullAnagram).equals(word)) yield word
    }

    val validWords: Vector[String] = filterPotentialValidWords(allWords, inputAnagram)

//--------------------------------------------------------------------------------------------------------------------//

    // (3) Check all possible combinations of potential valid words
    val determineAnagrams = (wordList: Vector[String], fullAnagram: String) => {
      for {
        word <- wordList
        comparisonWord <- wordList.slice(wordList.indexOf(word) + 1, wordList.size)
        if fullAnagram.diff(word).sorted.equals(comparisonWord.sorted)
      } yield (word, comparisonWord)
    }

    val validAnagrams: Vector[(String, String)] = determineAnagrams(validWords, inputAnagram)

//--------------------------------------------------------------------------------------------------------------------//

    // (4) Output all valid word combinations
    printf("Word to solve anagrams for: \"%s\"\n", inputAnagram)
    printf("Word list to generate solutions from: \"%s\"\n", wordListFilepath)
    printf("Calculating valid 2-word anagrams: \n")
    for (tuple <- validAnagrams) printf("-> Solution found: (\"%s\", \"%s\")\n", tuple._1, tuple._2)
    printf("End of solutions")

//--------------------------------------------------------------------------------------------------------------------//

    // (5) Close any open resources
      wordListFile.close

//--------------------------------------------------------------------------------------------------------------------//

    // Potential improvements:
    // Improve the performance (e.g. using word length to filter invalid combinations quicker?)
    // Implement a Try/Success/Failure idiom for file handling (both opening and reading)

    //TODO: Remove all comments to self!
  }
}
