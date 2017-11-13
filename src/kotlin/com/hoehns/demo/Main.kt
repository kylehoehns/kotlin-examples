package com.hoehns.demo

data class Race(val distance: Double = 3.1, val name: String?)
data class City(val name: String, val state: String, val races: List<Race>)

fun main(args: Array<String>) {

  // Normal Hello, World!
  println("Hello, World!")

  // String interpolation && Type inference
  val name = "Kyle"
  println("Hello, $name!")

  // Data Classes
  val damToDam = Race(12.5, "Dam to Dam")
  val desMoinesMarathon = Race(26.2, "Des Moines Marathon")
  val loopTheLake = Race(4.0, "Loop the Lake")

  // Immutable
  val races = listOf(damToDam, desMoinesMarathon, loopTheLake)

  // Mutable
  val mutableRaces = mutableListOf(damToDam, desMoinesMarathon, loopTheLake)

  races.forEach({ println(it.name) })

  // Immutability
  // "name" cannot be reassigned
  //loopTheLake.name = "Not Loop the Lake"

  // Compilation Error
  //races.add(Race(10.0, "Capital Pursuit"))
  mutableRaces.add(Race(10.0, "Capital Pursuit"))

  // Want another 4 mile race? Copy it from an existing 4 miler.
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

  val fiveKCities = cities
      .filter({ it.races.hasRaceWithDistance(3.1) })
      .map { it.name }

  println(fiveKCities)

  // More extension and infix functions
  println(damToDam.plus(loopTheLake))

  // Operator functions
  println(damToDam + loopTheLake)


  // Type casting
  val livingHistoryFarms: Any = Race(7.0, "Living History Farms")
  if (livingHistoryFarms is Race) {
    println(livingHistoryFarms.distance.kilometers)
  }
}

// TOOD : Example of infix function?

operator fun Race.plus(race: Race): Race {
  return Race(this.distance.plus(race.distance), "${this.name} and ${race.name}")
}

// Extension function
fun List<Race>.hasRaceWithDistance(distance: Double): Boolean {
  return this.any { it.distance == distance }
}

// Extension Property
val Double.kilometers: Double
  get() = this.times(1.60934)
