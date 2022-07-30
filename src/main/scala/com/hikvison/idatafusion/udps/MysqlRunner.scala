package com.hikvison.idatafusion.udps

import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession

import java.util.Properties

object MysqlRunner {

  def main(pro:Properties,url:String,table:String): Unit = {
    MysqlRunner.statisticQuantity(pro,url,table)
  }

  def statisticQuantity(pro:Properties,url:String,table:String): Long = {


    // 定义sparkSession
    val conf = new SparkConf().setMaster("local[*]").setAppName("mjp_sparksql")
    conf.set("spark.sql.shuffle.partitions","10")
    val session = SparkSession.builder().config(conf).getOrCreate()
    val df1 = session.read.jdbc(url, table, pro)
//    val session1 = SparkSession.builder().config(conf).getOrCreate()
////    val df1 = session1.read.format("jdbc").options(Map(
////      "url" -> "jdbc:mysql://localhost:3306/mujunpeng",
////      "driver" -> "com.mysql.jdbc.Driver",
////      "dbtable" -> "table1",
////      "user" -> "root",
////      "password" -> "113270"
////    )).load()

    val rows = df1.take(10)

    val count = df1.count()

    df1.show()

    session.close()

    count
    //Thread.sleep(10000000L)
  }

}
