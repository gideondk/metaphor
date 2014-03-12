package nl.gideondk.metaphor

import spray.json._
import shapeless._
import syntax._
import syntax.singleton._

import org.specs2.mutable.Specification

import record._
import scala.util.Try
import shapeless.ops.record.Selector

class MetaphorSpec extends Specification with DefaultJsonProtocol {
  "A record" should {
    "be correctly parsed from json" in {
      val js = """{"title": "Types and Programming Languages", "id": 262162091, "price": 44.11}""".asJson
      val book = Metaphor.extract[String, Long, Float](js, "title", "id", "price")
      book.get("id") == 262162091
    }

    "throw a error if a incorrect field is parsed from Json" in {
      val js = """{"title": "Types and Programming Languages", "id": 262162091, "price": 44.11}""".asJson
      val book = Metaphor.extract[String, String, Float](js, "title", "id", "price")
      book.get must throwA[DeserializationException]
    }

    "be generated correctly to json" in {
      val book =
        ("author" ->> "Benjamin Pierce") ::
          ("title" ->> "Types and Programming Languages") ::
          ("id" ->> 262162091) ::
          ("price" ->> 44.11) ::
          HNil

      val json = Metaphor.generate(book, "author", "title", "id", "price")
      json == """{"author":"Benjamin Pierce","title":"Types and Programming Languages","id":262162091,"price":44.11}""".asJson
    }

    "should correctly generate and parse into the same value" in {
      val person =
        ("name" ->> "Barraco Barner") ::
          ("occupation" ->> "President") ::
          ("country" ->> "UK") ::
          ("family" ->> List("Michello Barner, Miley Barner", "Sushi Barner")) ::
          HNil

      val json = Metaphor.generate(person, "name", "occupation", "country", "family")
      val parsed = Metaphor.extract[String, String, String, List[String]](json, "name", "occupation", "country", "family")

      parsed.get == person
    }

  }
}
