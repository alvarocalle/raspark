package com.datio.raspark

import org.apache.spark.sql.{DataFrame, SparkSession}

object RasparkHDFS {
  def main(args: Array[String]) {
    val spark = SparkSession
      .builder
      .appName("RapSpark HDFS")
      .getOrCreate()


    val hdfs: DataFrame = spark.read
      .option("delimiter",",").
      option("header","true").
      csv("hdfs:///user/lcisneros/canada/CENSUS_1881.csv")

    hdfs.createGlobalTempView("census")

    println("Runnin Query")
    time {
      spark.sql("SELECT BIRTHMO, count(ID) FROM global_temp.census GROUP BY BIRTHMO").show()
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
