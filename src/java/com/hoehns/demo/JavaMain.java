package com.hoehns.demo;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class JavaMain {

  public static void main(String[] args) {

    // Hello, World!
    System.out.println("Hello, World!");

    // Variable
    String name = "Kyle";
    System.out.println("Hello, " + name + "!");

    // Collections/Object creation
    final JavaRace damToDam = new JavaRace(12.5, "Dam to Dam");
    JavaRace desMoinesMarathon = new JavaRace(26.2, "Des Moines Marathon");
    JavaRace loopTheLake = new JavaRace(4.0, "Loop the Lake");

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


  }
}
