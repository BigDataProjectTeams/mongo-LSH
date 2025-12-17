package lsh
import org.apache.spark.sql.{DataFrame, SaveMode}

object MongoMatchesWriter {
  def write(df: DataFrame, uri: String, db: String, col: String): Unit =
    df.write.format("mongodb")
      .mode(SaveMode.Overwrite) // أو Append حسب كيف بدكم
      .option("spark.mongodb.write.connection.uri", uri)
      .option("spark.mongodb.write.database", db)
      .option("spark.mongodb.write.collection", col)
      .save()
}
