/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package jp.gihyo.spark.ch06

import org.apache.spark.SparkConf
import org.apache.spark.streaming.{StreamingContext, Seconds}
import org.apache.spark.streaming.dstream.InputDStream


object gihyo_6_3_countByValue {
  def main(args: Array[String]) {
    if (args.length != 2) {
      new IllegalArgumentException("Invalid arguments")
      System.exit(1)
    }
    val targetHost = args(0)
    val targetHostPort = args(1).toInt

    val conf = new SparkConf().setAppName("NetworkWordCount")
    val ssc = new StreamingContext(conf, Seconds(5))
    val lines = ssc.socketTextStream(targetHost, targetHostPort)
    run(lines)

    ssc.start
    ssc.awaitTermination
  }

  def run(stream: InputDStream[String]) {
    val countValue = stream.countByValue()
    countValue.print
  }
}
