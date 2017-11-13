package com.hoehns.demo;

public class JavaRace {

  private double distance;
  private String name;

  public JavaRace(String name) {
    this(3.1, name);
  }

  public JavaRace(double distance, String name) {
    this.distance = distance;
    this.name = name;
  }

  public double getDistance() {
    return distance;
  }

  public String getName() {
    return name;
  }

  public void setDistance(double distance) {
    this.distance = distance;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    JavaRace race = (JavaRace) o;

    if (Double.compare(race.distance, distance) != 0) return false;
    return name != null ? name.equals(race.name) : race.name == null;
  }

  @Override
  public int hashCode() {
    int result;
    long temp;
    temp = Double.doubleToLongBits(distance);
    result = (int) (temp ^ (temp >>> 32));
    result = 31 * result + (name != null ? name.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "JavaRace{" +
        "distance=" + distance +
        ", name='" + name + '\'' +
        '}';
  }
}
