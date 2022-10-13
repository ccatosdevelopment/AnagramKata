package com.app.example

import scala.io.{BufferedSource, Source}

object Main {

  def readWordsFromFile: BufferedSource => Vector[String] = (sourceFile: BufferedSource) => {
    for {
      linesInFile <- sourceFile.getLines().toVector
      wordsInLine <- linesInFile.split(" +")
    } yield wordsInLine
  }

  def filterWordsWithValidChars: (Vector[String], String) => Vector[String]  = (wordList: Vector[String], targetWord: String) => {
    for {
      word <- wordList
      if word.intersect(targetWord).equals(word)
    } yield word
  }

  def filterValidTwoWordAnagrams: (Vector[String], String) => Vector[(String, String)] = (wordList: Vector[String], targetWord: String) => {
    for {
      firstWord <- wordList
      secondWord <- wordList.slice(wordList.indexOf(firstWord) + 1, wordList.size)
      if targetWord.diff(firstWord).sorted.equals(secondWord.sorted)
    } yield (firstWord, secondWord)
  }

  def main(args: Array[String]): Unit = {

    val targetAnagram: String = "documenting"
    val wordListFilepath: String = "src/main/resources/word_list.txt"
    val wordListSourceFile: BufferedSource = Source.fromFile(wordListFilepath)

    val wordsReadFromFile: Vector[String] = readWordsFromFile(wordListSourceFile)

    val wordsWithValidChars: Vector[String] = filterWordsWithValidChars(wordsReadFromFile, targetAnagram)

    val validTwoWordAnagrams: Vector[(String, String)] = filterValidTwoWordAnagrams(wordsWithValidChars, targetAnagram)

    printf("Target word to generate 2-word anagrams for: \"%s\"\n", targetAnagram)
    printf("Word list to generate solutions from: \"%s\"\n", wordListFilepath)
    printf("Generating valid 2-word anagrams: \n")
    for (tuple <- validTwoWordAnagrams) printf("-> Solution found: (\"%s\", \"%s\")\n", tuple._1, tuple._2)
    printf("End of solutions")

    wordListSourceFile.close
  }
}
