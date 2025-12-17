package lsh

import org.apache.spark.sql.DataFrame
import org.apache.spark.sql.functions._

object OpportunityFeatureBuilder {

  def withTokens(df: DataFrame): DataFrame = {
    df.withColumn(
      "tokens",
      array_distinct(
        concat(
          coalesce(col("required_skills"), array()),
          coalesce(col("preferred_courses"), array()),
          array(lower(col("target_major"))),
          array(lower(col("training_type")))
        )
      )
    )
  }
}
