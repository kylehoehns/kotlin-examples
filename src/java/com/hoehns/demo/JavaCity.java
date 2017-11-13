package com.hoehns.demo;

import java.util.List;

public class JavaCity {

  private final String name;
  private final String state;
  private final List<JavaRace> races;

  public JavaCity(String name, String state, List<JavaRace> races) {
    this.name = name;
    this.state = state;
    this.races = races;
  }

  public String getName() {
    return name;
  }

  public String getState() {
    return state;
  }

  public List<JavaRace> getRaces() {
    return races;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    JavaCity city = (JavaCity) o;

    if (name != null ? !name.equals(city.name) : city.name != null) return false;
    if (state != null ? !state.equals(city.state) : city.state != null) return false;
    return races != null ? races.equals(city.races) : city.races == null;
  }

  @Override
  public int hashCode() {
    int result = name != null ? name.hashCode() : 0;
    result = 31 * result + (state != null ? state.hashCode() : 0);
    result = 31 * result + (races != null ? races.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "JavaCity{" +
        "name='" + name + '\'' +
        ", state='" + state + '\'' +
        ", races=" + races +
        '}';
  }
}
