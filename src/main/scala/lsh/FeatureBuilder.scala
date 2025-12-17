package lsh
import org.apache.spark.sql.DataFrame
import org.apache.spark.sql.functions._

object FeatureBuilder {
  def withTokens(df: DataFrame): DataFrame =
    df.withColumn("skills_safe", coalesce(col("skills"), array()))
      .withColumn("courses_safe", coalesce(col("courses"), array()))
      .withColumn("tokens", array_distinct(concat(col("skills_safe"), col("courses_safe"))))
}
