package lsh
import org.apache.spark.sql.{DataFrame, SparkSession}
import org.apache.spark.sql.functions._
import org.apache.spark.ml.feature.MinHashLSHModel

object Matcher {
  def matchStudentsToJobs(model: MinHashLSHModel, studentsFe: DataFrame, jobsFe: DataFrame): DataFrame = {
    val joined = model.approxSimilarityJoin(studentsFe, jobsFe, 1.0 - Config.jaccardThreshold, "dist")

    joined.select(
      col("datasetA._id").as("student_id"),
      col("datasetB._id").as("job_id"),
      (lit(1.0) - col("dist")).as("similarity"),
      current_timestamp().as("created_at")
    ).orderBy(col("similarity").desc)
  }
}
