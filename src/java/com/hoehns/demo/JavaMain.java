package com.hoehns.demo;

import java.util.*;
import java.util.stream.Collectors;

public class JavaMain {

  public static void main(String[] args) {

    // Hello, World!
    System.out.println("Hello, World!");

    // Variable
    String name = "Kyle";
    System.out.println("Hello, " + name + "!");

    // Collections/Object creation
    final JavaRace damToDam = new JavaRace(12.4, "Dam to Dam");
    JavaRace desMoinesMarathon = new JavaRace(26.2, "Des Moines Marathon");
    JavaRace loopTheLake = new JavaRace(5.0, "Loop the Lake");

    // Mutable by default
    List<JavaRace> races = Arrays.asList(damToDam, desMoinesMarathon, loopTheLake);

    // Takes some work to get immutable collection
    List<JavaRace> immutableRaces = Collections.unmodifiableList(
      Arrays.asList(damToDam, desMoinesMarathon, loopTheLake)
    );

    races.forEach(race -> System.out.println(race.getName()));

    // UnsupportedOperationException
    // immutableRaces.add(new JavaRace(10.0, "Capital Pursuit"));

    // Want another 4 mile race? Make a whole new one.
    JavaRace a4Miler = new JavaRace(4.0, "Another 4 Miler");

    // Constructor Overloads
    JavaRace some5K = new JavaRace("Some 5k");

    // Null Safety
    JavaCity desMoines = new JavaCity("Des Moines", "IA", races);

    // Get first race and print it - uppercase
    List<JavaRace> desMoinesRaces = desMoines.getRaces();
    if (desMoinesRaces != null && !desMoinesRaces.isEmpty()) {
      Optional.ofNullable(desMoinesRaces.iterator().next())
        .ifPresent(race -> {
          if (race.getName() != null) {
            System.out.println(race.getName().toUpperCase());
          }
        });
    }

    JavaCity ankeny = new JavaCity("Ankeny", "IA", Arrays.asList(new JavaRace(3.1, "Mayor's 5k")));
    JavaCity urbandale = new JavaCity("Urbandale", "IA", Arrays.asList(new JavaRace(3.1, "Some 5k")));

    List<JavaCity> cities = Arrays.asList(desMoines, ankeny, urbandale);

    // City names of all cities that have a 5k
    List<String> cityNames = cities.stream()
      .filter(city -> city.getRaces().stream()
        .filter(race -> race.getDistance() == 3.1)
        .count() > 0
      ).map(JavaCity::getName)
      .collect(Collectors.toList());

    System.out.println(cityNames);

    // Print out all races in all cities in descending order by distance
    cities.stream()
      .flatMap(city -> city.getRaces().stream())
      .sorted(Comparator.comparing(JavaRace::getDistance).reversed())
      .forEach(race -> System.out.println(race.getDistance() + " mile race named " + race.getName()));

    // Use Kotlin classes in Java - demonstrate @JvmOverload functionality to allow
    // default values to work on constructors
    Race kotlinRace = new Race(13.1, "Kotlin Half Marathon");
    Race kotlin5K = new Race("Kotlin 5k");

    System.out.println("---------");
    System.out.println(kotlinRace);
    System.out.println(kotlin5K);
  }
}
