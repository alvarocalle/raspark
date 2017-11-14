package com.datio.raspark

import org.apache.spark.sql.{DataFrame, SparkSession}

object RasparkPostgre {
  def main(args: Array[String]) {
    val spark = SparkSession
      .builder
      .appName("RapSpark PostgreSQL")
      .getOrCreate()
    val jdbcDF = spark.read.format("jdbc").options(
      Map("url" ->  "jdbc:postgresql://172.16.15.150:5432/canada?user=luis&password=luis",
        "dbtable" -> "public.\"CENSUS_1881\"",
        "partitionColumn" -> "\"BIRTHMO\"", "lowerBound" -> "0", "upperBound" -> "13", "numPartitions" -> "4",
        "fetchSize" -> "10000"
      )).load()
    jdbcDF.createOrReplaceTempView("census")

    println("Runnin Query")
    time {
      val sqlDF = spark.sql("SELECT BIRTHMO, count(ID) FROM global_temp.census GROUP BY BIRTHMO")
      sqlDF.show()
    }

    println("Query Finished")
    spark.stop()

  }

  def time[R](block: => R): R = {
    val t0 = System.nanoTime()
    val result = block    // call-by-name
    val t1 = System.nanoTime()
    println("Elapsed time: " + (t1 - t0)/1000000000 + "seconds")
    result
  }

}
