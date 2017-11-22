package com.hoehns.demo

data class Race @JvmOverloads constructor(val distance: Double = 3.1, val name: String)
data class City(val name: String, val state: String, val races: List<Race>)

fun main(args: Array<String>) {

  // Normal Hello, World!
  println("Hello, World!")

  // String interpolation && Type inference
  val name = "Kyle"
  println("Hello, $name!")

  // Data Classes
  val damToDam = Race(12.4, "Dam to Dam")
  val desMoinesMarathon = Race(26.2, "Des Moines Marathon")
  val loopTheLake = Race(5.0, "Loop the Lake")

  // Immutable
  val races = listOf(damToDam, desMoinesMarathon, loopTheLake)

  // Mutable
  val mutableRaces = mutableListOf(damToDam, desMoinesMarathon, loopTheLake)

  races.forEach { println(it.name) }

  // Immutability
  // "name" cannot be reassigned
  //loopTheLake.name = "Not Loop the Lake"

  // Compilation Error
  //races.add(Race(10.0, "Capital Pursuit"))
  mutableRaces.add(Race(10.0, "Capital Pursuit"))

  // Want another race? Copy it from another similar one.
  // Named parameters
  val desMoinesHalfMarathon = desMoinesMarathon.copy(distance = 13.1)
  println(desMoinesHalfMarathon)

  // Constructor Overloading - only need to include name if not the first parameter
  val some5k = Race(name = "Some 5k")
  println(some5k)

  // Null Safety
  // Can't do this
  //  val desMoines = City("Des Moines", null, races)
  val desMoines = City("Des Moines", "IA", races)

  // Might be helpful to make name nullable and show example first
  // println(damToDam.name?.toUpperCase())

  // In Java Style - will get warnings from compiler
  val desMoinesRaces = desMoines.races
  if (desMoinesRaces != null && desMoinesRaces.isNotEmpty()) {
    val firstRace = desMoinesRaces.first()
    if (firstRace != null && firstRace.name != null) {
      println(firstRace.name.toUpperCase())
    }
  }

  // First, show nullability checks - then add Elvis operator to show "NO RACES"
  println(desMoines.races.firstOrNull()?.name?.toUpperCase() ?: "NO RACES")

  // Other nice things on Collections
  // All cities that have a 5k
  val ankeny = City("Ankeny", "IA", listOf(Race(name = "Mayor's 5k")))
  val urbandale = City("Urbandale", "IA", listOf(Race(name = "Urbandale 5k")))

  val cities = listOf(desMoines, ankeny, urbandale)

  // Print out all race names sorted longest to shortest by distance
  cities
    .flatMap(City::races)
    .sortedByDescending(Race::distance)
    .forEach { println("${it.distance} mile long race named ${it.name}") }

//  val fiveKCities = cities.filter({ city ->
//      city.races.any { race -> race.distance == 3.1 }
//    }).map { it.name }

  // clean it up with an extension function on List<Race>
  val fiveKCities = cities
    .filter { it.races.has5k() }
    .map(City::name)

  println("Cities with a 5K are $fiveKCities")

  // More extension and infix functions
  println(damToDam.plus(loopTheLake))

  // Operator functions
  println(damToDam + loopTheLake)

  // Type casting (Any == Object in Java)
  val livingHistoryFarms: Any = Race(7.0, "Living History Farms")
  if (livingHistoryFarms is Race) {
    println(livingHistoryFarms.distance.kilometers)
  }

  // Type aliases
  // map of city -> list of 5k races in that city
  val raceMap = cities.associate { Pair(it, it.races.all5Ks()) }

  // Only get the Ankeny races
  println(raceMap.filter("Ankeny").values)

  // TODO : Couroutines


}

// First, build this filter extension method like this
//fun Map<City, List<Race>>.filter(cityName: String) : Map<City, List<Race>>
//  = this.filter { it.key.name == cityName }

// Then, show how we can use typealiases to shorten things up and make them more readable
typealias RaceMap = Map<City, List<Race>>
fun RaceMap.filter(cityName: String) : RaceMap = this.filter{ it.key.name === cityName }

// Operator function plus one-line functions
operator fun Race.plus(race: Race): Race =
  Race(this.distance.plus(race.distance), "${this.name} and ${race.name}")

// Extension function and one-line function
fun List<Race>.has5k(): Boolean = this.any { it.distance == 3.1 }

fun List<Race>.all5Ks() : List<Race> = this.filter { it.distance == 3.1 }

// Extension Property - add a virtual property to any data type without explicit extension
val Double.kilometers: Double
  get() = this.times(1.60934)
