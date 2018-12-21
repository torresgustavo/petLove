package Entities;

import java.util.HashMap;
import java.util.Map;

public class Animals {

    private String name, id, weight, age, race, photoPerfil;
    public Map<String, Boolean> animals = new HashMap<>();

    public Animals() {
    }

    public Animals(String name, String id, String weight, String age, String race, String photoPerfil) {
        this.name = name;
        this.id = id;
        this.weight = weight;
        this.age = age;
        this.race = race;
        this.photoPerfil = photoPerfil;
    }

    public Map<String, Object> toMap(){
        HashMap<String, Object> hashMapAnimals = new HashMap<>();

        hashMapAnimals.put("id", getId());
        hashMapAnimals.put("photoPerfil", getPhotoPerfil());
        hashMapAnimals.put("name", getName());
        hashMapAnimals.put("age", getAge());
        hashMapAnimals.put("race", getRace());
        hashMapAnimals.put("weigth", getWeight());

        return hashMapAnimals;
    }

    public String getPhotoPerfil() {
        return photoPerfil;
    }

    public void setPhotoPerfil(String photoPerfil) {
        this.photoPerfil = photoPerfil;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getRace() {
        return race;
    }

    public void setRace(String race) {
        this.race = race;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
