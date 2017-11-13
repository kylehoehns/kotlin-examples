package com.hoehns.demo

// Add @JvmOverloads and constructor when talking about interoperablility
// Do not include default value for distance until later
data class Race @JvmOverloads constructor(val distance: Double = 3.1, val name: String)

// Don't add City until after constructor and method overloading
data class City(val name: String, val state: String?, val races: List<Race>)

fun main(args: Array<String>) {

  // Normal Hello, World!
  println("Hello, World!")

  // ========= String Interpolation and Type Inference =========
  val name = "Kyle"
  println("Hello, $name!")

  // ========= Data Classes =========
  // Start with Java classes
  // Compare with Java here
  val damToDam = Race(12.4, "Dam to Dam")
  val desMoinesMarathon = Race(26.2, "Des Moines Marathon")
  val loopTheLake = Race(5.0, "Loop the Lake")

  // Show what this prints out
  println("Dam to Dam $damToDam")

  // ========= Built-In Immutability =========

  // First, show difference between val and var on properties
  //  damToDam.name = "Dam 2 Dam"
  // Change to 'var' and you're good to go

  // Mutable vs Immutable collections -- show Java side of this first
  val races = listOf(damToDam, desMoinesMarathon, loopTheLake)
  println("Races are $races")

  // Let's try to add another race to our original list
  val capitalPursuit = Race(10.0, "Capital Pursuit")
  // races.add(capitalPursuit)

  // How to fix this? Must specify that collection is supposed to be mutable
  val mutableRaces = mutableListOf(damToDam, desMoinesMarathon, loopTheLake)
  mutableRaces.add(capitalPursuit)
  println("Mutable Races are $mutableRaces")

  // ========= More on Data Classes and Named Parameters =========

  // Want another race? Copy it from another similar one.
  val desMoinesHalfMarathon = desMoinesMarathon.copy(distance = 13.1)
  println("Des Moines Marathon copy is $desMoinesHalfMarathon")

  // ========= Constructor/Method Overloading and Default Parameters =========

  // Constructor Overloading - only need to include name if not the first parameter
  val some5k = Race(name = "Some 5k")
  println("${some5k.name} is a ${some5k.distance} mile long race")

  // ========= Null Safety Built in to the Type System =========
//  var grandBlueMile = Race(1.0, "Grand Blue Mile")
//  grandBlueMile.distance = null

  // Add City data class - try to set State to null during construction
  val desMoines = City("Des Moines", "IA", races)

  // Make state nullable in the Data Class
  println("Des Moines is in the state of ${desMoines.state?.toLowerCase()}")

  // Get the first race in Des Moines and print it out Uppercase, otherwise, print out "No Races"
  // Show example of scenario where things aren't null, but in the Java world, you'd make all of these checks
  // In Java Style - will get warnings from compiler
  val desMoinesRaces = desMoines.races
  if (desMoinesRaces != null && desMoinesRaces.isNotEmpty()) {
    val firstRace = desMoinesRaces.first()
    if (firstRace != null && firstRace.name != null) {
      println("The first race's name is ${firstRace.name.toUpperCase()}")
    } else {
      println("No Races")
    }
  } else {
    println("No Races")
  }

  // First, show nullability checks - then add Elvis operator to show "No Races"
  // Afterwards, show bang operator (!!)
  println(desMoinesRaces.firstOrNull()?.name?.toUpperCase() ?: "No Races")

  // ========= Collections =========

  val ankeny = City("Ankeny", "IA", listOf(Race(name = "Mayor's 5k")))
  val urbandale = City("Urbandale", "IA", listOf(Race(name = "Urbandale 5k")))

  val cities = listOf(desMoines, ankeny, urbandale)

  // All cities that have a 5k

  // Print out all race names sorted longest to shortest by distance (compare to Java version)
  cities
      .flatMap(City::races)
      .sortedByDescending(Race::distance)
      .forEach { println("${it.distance} mile long race named ${it.name}") }

  // Get a list of all cities that have a 5k
  // First, do this example - kind of ugly
//  val fiveKCities = cities.filter({ city ->
//    city.races.any { it.distance == 3.1 }}
//  ).map(City::name)

  // We could pull the filter logic out into a method... this is a little better
//  val fiveKCities = cities
//      .filter{ has5k(it) }
//      .map(City::name)

  // Ideally, we can clean it up by introducing extension functions on List<Race>
  val fiveKCities = cities
      .filter { it.races.has5K() }
      .map(City::name)

  println("Cities with a 5K are $fiveKCities")

  // ========= Extension Functions =========

  // Another example of extension functions (add greaterThan extension function first)
  println(damToDam.greaterThan(loopTheLake))

  // That's neat, but what if we want to make it a little easier to read? Operator functions
  println(damToDam > loopTheLake)

  // ========= Smart Casting and Extension Properties =========
  // Want to determine how many kilometers each race has

  // declare this as an "Any" type so we can cast it
  val livingHistoryFarms: Any = Race(7.0, "Living History Farms")

  // first, add a "toKilometers" function - then turn it into an extension property off of Double

  if (livingHistoryFarms is Race) {
//    println("Living History Farms is ${toKilometers(livingHistoryFarms.distance)} kilometers long race")
    println("Living History Farms is a ${livingHistoryFarms.distance.kilometers} kilometers long race")
  }

  // Type aliases
  // map of city -> list of 5k races in that city
  val boston = City("Boston", "MA", listOf())
  // Do something with a Map<String, List<City>>
  val allCities = listOf(desMoines, ankeny, urbandale, boston)
  val raceMap = allCities.groupBy(City::state)

  // Only get the MA cities
  println(raceMap.filter("MA").entries)

  // ========= Java Interoperability =========
  // Show @NotNull or @Nullable annotation on constructor and getters
  val jRace = JavaRace(78.0, "Market to Market Relay")
  val jCity = JavaCity("Jefferson", "IA", listOf(jRace))

  println(jCity.name.length)
}

// First, build this filter extension method like this
//fun Map<String, List<City>>.filter(state: String) : Map<String, List<City>>
//  = this.filter { it.key.name == cityName }

// Then, show how we can use typealiases to shorten things up and make them more readable
typealias RaceMap = Map<String?, List<City>>
fun RaceMap.filter(state: String): RaceMap = this.filter { it.key == state }

fun Race.greaterThan(other: Race): Boolean = this.distance > other.distance
operator fun Race.compareTo(other: Race): Int = this.distance.compareTo(other.distance)

// Extension function and one-line function
fun List<Race>.has5K(): Boolean = this.any { it.distance == 3.1 }

fun List<Race>.only5Ks(): List<Race> = this.filter { it.distance == 3.1 }

// Extension Property - add a virtual property to any data type without explicit extension
//  fun toKilometers(miles : Double) : Double = miles.times(1.60934)
val Double.kilometers: Double
  get() = this.times(1.60934)
