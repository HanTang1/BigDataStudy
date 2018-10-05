package cn.edu360.day7

import org.apache.spark.sql.{DataFrame, SparkSession}

/**
  * Created by zx on 2017/9/18.
  */
object ParquetDataSource {

  def main(args: Array[String]): Unit = {

    val spark = SparkSession.builder().appName("ParquetDataSource")
      .master("local[*]")
      .getOrCreate()



//    val peopleDF =spark.read
//      .textFile("examples/src/main/resources/people.txt")
//      .map(_.split(","))
//      .map(attributes => Person(attributes(0), attributes(1).trim.toInt))
//      .toDF()
    //指定以后读取json类型的数据
    val parquetLine: DataFrame = spark.read.parquet("/Users/zx/Desktop/parquet")
    //val parquetLine: DataFrame = spark.read.format("parquet").load("/Users/zx/Desktop/pq")

    parquetLine.printSchema()

    //show是Action
    parquetLine.show()

    spark.stop()


  }
}
