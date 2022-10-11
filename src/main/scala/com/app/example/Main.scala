package com.app.example

import scala.io.{BufferedSource, Source}

object Main {
  def main(args: Array[String]): Unit = {
    // (1) Read in wordlist.txt and store all words in a Vector[String]
    val wordListFilepath: String = "src/main/resources/word_list.txt"
    val wordListSource: BufferedSource = Source.fromFile(wordListFilepath)

    val readWordsFromFile: () => Vector[String] = () => {
      for {
        linesInFile <- wordListSource.getLines().toVector
        wordsInLine <- linesInFile.split(" +")
      } yield wordsInLine
    }

    val words: Vector[String] = readWordsFromFile()
//    for (word <- words) println(word)


    // TODO: (2) Filter out all invalid words















    // TODO: (3) Check all possible combinations of remaining words


    // TODO: (4) Output all valid word combinations


    // (5) Close any open resources
      wordListSource.close

    // Potential improvements:
    // Improve the performance (e.g. using word length to filter invalid combinations quicker?)
    // Implement a Try/Success/Failure idiom for file handling (both opening and reading)
    // Could Regex improve things here? Read in word list and apply Regex to filter out invalid words?

  }
}

//TODO: Remove all comments to self!