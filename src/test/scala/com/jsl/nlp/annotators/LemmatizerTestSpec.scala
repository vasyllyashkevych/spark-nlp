package com.jsl.nlp.annotators

import com.jsl.nlp.util.ResourceHelper
import com.jsl.nlp.{ContentProvider, DataBuilder}
import org.apache.spark.sql.{Dataset, Row}
import org.scalatest._

/**
  * Created by saif on 02/05/17.
  */
class LemmatizerTestSpec extends FlatSpec with LemmatizerBehaviors {

  val lemmatizer = new Lemmatizer
  "a lemmatizer" should s"be of type ${Lemmatizer.aType}" in {
    assert(lemmatizer.aType == Lemmatizer.aType)
  }

  val latinBodyData: Dataset[Row] = DataBuilder.basicDataBuild(ContentProvider.latinBody)

  "A full Normalizer pipeline with latin content" should behave like fullLemmatizerPipeline(latinBodyData)

  "A lemmatizer" should "be readable and writable" in {
    val lemmatizer = new Lemmatizer().setLemmaDict(ResourceHelper.retrieveLemmaDict)
    val path = "./test-output-tmp/lemmatizer"
    lemmatizer.write.overwrite.save(path)
    val lemmatizerRead = Lemmatizer.read.load(path)
    assert(lemmatizer.getLemmaDict.head == lemmatizerRead.getLemmaDict.head)
  }

}
