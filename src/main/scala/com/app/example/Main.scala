package com.app.example

import scala.io.{BufferedSource, Source}

object Main {

  def readWordsFromFile: BufferedSource => Vector[String] = (file: BufferedSource) => {
    for {
      linesInFile <- file.getLines().toVector
      wordsInLine <- linesInFile.split(" +")
    } yield wordsInLine
  }

  def filterPotentialValidWords: (Vector[String], String) => Vector[String]  = (wordList: Vector[String], fullAnagram: String) => {
    for (word <- wordList if word.intersect(fullAnagram).equals(word)) yield word
  }

  def determineAnagrams: (Vector[String], String) => Vector[(String, String)] = (wordList: Vector[String], fullAnagram: String) => {
    for {
      word <- wordList
      comparisonWord <- wordList.slice(wordList.indexOf(word) + 1, wordList.size)
      if fullAnagram.diff(word).sorted.equals(comparisonWord.sorted)
    } yield (word, comparisonWord)
  }

  def main(args: Array[String]): Unit = {

    val inputAnagram: String = "documenting"
    val wordListFilepath: String = "src/main/resources/word_list.txt"
    val wordListFile: BufferedSource = Source.fromFile(wordListFilepath)

    val allWords: Vector[String] = readWordsFromFile(wordListFile)

    val validWords: Vector[String] = filterPotentialValidWords(allWords, inputAnagram)

    val validAnagrams: Vector[(String, String)] = determineAnagrams(validWords, inputAnagram)

    printf("Word to solve anagrams for: \"%s\"\n", inputAnagram)
    printf("Word list to generate solutions from: \"%s\"\n", wordListFilepath)
    printf("Calculating valid 2-word anagrams: \n")
    for (tuple <- validAnagrams) printf("-> Solution found: (\"%s\", \"%s\")\n", tuple._1, tuple._2)
    printf("End of solutions")

    wordListFile.close
  }
}
