package lsh

import org.apache.spark.sql.{DataFrame}
import org.apache.spark.sql.functions._

object StudentFeatureBuilder {

  def withTokens(df: DataFrame): DataFrame = {
    df.withColumn(
      "tokens",
      array_distinct(
        concat(
          coalesce(col("skills"), array()),
          coalesce(col("courses"), array()),
          array(lower(col("major")))
        )
      )
    )
  }
}
