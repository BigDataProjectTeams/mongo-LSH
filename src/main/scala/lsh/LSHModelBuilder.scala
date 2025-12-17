package lsh
import org.apache.spark.ml.feature.{HashingTF, MinHashLSH}

object LSHModelBuilder {
  def hashingTF(): HashingTF =
    new HashingTF()
      .setInputCol("tokens")
      .setOutputCol("tf")
      .setNumFeatures(Config.numFeatures)

  def minHash(): MinHashLSH =
    new MinHashLSH()
      .setInputCol("tf")
      .setOutputCol("hashes")
      .setNumHashTables(Config.numHashTables)
}
