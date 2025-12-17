package lsh

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._

object LSHJobMain {

  def main(args: Array[String]): Unit = {

    val spark = SparkSession.builder()
      .appName("Student-Opportunity LSH")
      .master("local[*]")
      .getOrCreate()

    spark.sparkContext.setLogLevel("WARN")

    val studentsRaw = MongoBatchReader.readCollection(
      spark,
      Config.mongoUri,
      Config.db,
      Config.studentsCollection
    )


    val oppRaw = MongoBatchReader.readCollection(
      spark,
      Config.mongoUri,
      Config.db,
      Config.opportunitiesCollection
    )

    // 3) Feature engineering
    val studentsFe = StudentFeatureBuilder.withTokens(studentsRaw)
    val oppFe = OpportunityFeatureBuilder.withTokens(oppRaw)

    // 4) HashingTF
    val ht = LSHModelBuilder.hashingTF()
    val studentsTf = ht.transform(studentsFe)
    val oppTf = ht.transform(oppFe)

    // 5) MinHash LSH
    val mh = LSHModelBuilder.minHash()
    val model = mh.fit(studentsTf)

    // 6) Matching
    val matches = model
      .approxSimilarityJoin(studentsTf, oppTf, 0.6, "dist")
      .select(
        col("datasetA._id").alias("student_id"),
        col("datasetB._id").alias("opportunity_id"),
        (lit(1) - col("dist")).alias("similarity")
      )
      .orderBy(col("similarity").desc)

    // 7) Save results
    MongoMatchesWriter.write(
      matches,
      Config.mongoUri,
      Config.db,
      Config.matchesCollection
    )

    spark.stop()
  }
}
