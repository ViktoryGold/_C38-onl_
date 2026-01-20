package com.example;

import com.google.gson.Gson;

public class GsonExample {
    public static void main( String[] args ) {
        Person person = new Person("Alice", 25, "Moskow");

        Gson gson = new Gson();
        String json = gson.toJson(person);

        System.out.println("JSON: " + json);

        Person decoded = gson.fromJson(json, Person.class);
        System.out.println("DECODED object: " + decoded.getName());
    }
}
class Person {
    private final String name;
    private final int age;
    private final String city;

    public Person(String name, int age, String city) {
        this.name = name;
        this.age = age;
        this.city = city;
    }

    public String getName() { return name; }
    public int  getAge() { return age; }
    public String getCity() { return city; }
}