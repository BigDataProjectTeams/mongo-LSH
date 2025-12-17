package lsh
import org.apache.spark.sql.{DataFrame, SparkSession}

object MongoBatchReader {
  def readCollection(spark: SparkSession, uri: String, db: String, col: String): DataFrame =
    spark.read.format("mongodb")
      .option("spark.mongodb.read.connection.uri", uri)
      .option("spark.mongodb.read.database", db)
      .option("spark.mongodb.read.collection", col)
      .load()
}
