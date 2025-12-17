package lsh

object Config {
  val mongoUri = "mongodb://localhost:27017"
  val db = "bigdata"

  val studentsCollection = "students"
  val opportunitiesCollection = "opportunities"
  val matchesCollection = "matches"

  val similarityThreshold = 0.3   // = 70% similarity
}
