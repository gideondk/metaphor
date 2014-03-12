# Metaphor

## Overview
Metaphor is a small library which leverages the parsing and generating capabilities  of [spray-json](https://github.com/spray/spray-json) into a solution for the generation and extraction of [Shapeless Records](https://github.com/milessabin/shapeless/wiki/Feature-overview:-shapeless-2.0.0#extensible-records):

	Shapeless provides an implementation of extensible records modelled as HLists of values tagged with the singleton types of their keys. This means that there is no concrete representation needed at all for the keys. Amongst other things this will allow subsequent work on Generic to map case classes directly to records with their member names encoded in their element types.

## Usage
Usage of Metaphor is very straight forward. Generation is done by using the `generate` function, supplying the record and the appropriate fields to be serialised:

```scala
val person =
	("name" ->> "Barraco Barner") ::
	("occupation" ->> "President") ::
	("country" ->> "UK") ::
	("family" ->> List("Michello Barner, Miley Barner", "Sushi Barner")) ::
	HNil

val json = Metaphor.generate(person, "name", "occupation", "country", "family")
``` 

Depending on supplied record, the compiler will automagically detect if the fields are available in the record or not. 

Extraction is done in a similar manner: 

```scala
val js = """{"title": "Types and Programming Languages", "id": 262162091, "price": 44.11}""".asJson
val book = Metaphor.extract[String, Long, Float](js, "title", "id", "price")
```

Extracting the appropriate types from the supplied JSON and generating a record with the same supplied field names. 

## Installation
Start by adding the repo:
```scala
"gideondk-repo" at "https://raw.github.com/gideondk/gideondk-mvn-repo/master"
```

to your SBT configuration and adding the package to your library dependencies:

```scala
libraryDependencies ++= Seq(
	"nl.gideondk" %% "metaphor" % "0.1.0"
)
```

# License
Copyright © 2014 Gideon de Kok

Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
