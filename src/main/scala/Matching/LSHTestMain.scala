package Matching

import org.apache.spark.sql.SparkSession

object LSHTestMain {

  def main(args: Array[String]): Unit = {

    val spark = SparkSession.builder()
      .appName("LSH Test")
      .master("local[*]")
      .getOrCreate()

    spark.sparkContext.setLogLevel("ERROR")

    val opportunity = LSHMatcher.OpportunityReq(
      skills = Seq("Financial Accounting", "Payroll", "Financial Reporting"),
      courses = Seq("Financial Analysis Mastery", "Auditing Principles"),
      major = "Accounting",
      minGpa = Some(1)
    )

    val result = LSHMatcher.top5ForOpportunity(
      spark = spark,
      mongoUri = "mongodb://localhost:27017",
      req = opportunity,
      minSimilarity = 0.70
    )

    println("\n===== TOP 5 MATCHED STUDENTS =====")
    result.show(truncate = false)

    spark.stop()
  }
}
