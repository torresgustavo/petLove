package Entities;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

import fireBaseConfiguration.fireBaseConfig;

public class Users {
    private String id ,name, password, email, address, district, number, cep, states;

    public Users() {
    }

    public void save (){
        DatabaseReference reference = fireBaseConfig.getFireBase();
        reference.child("user").child(String.valueOf(getId())).setValue(this);
    }

    @Exclude
    public Map<String, Object> toMap(){
        HashMap<String, Object> hashMapUser = new HashMap<>();

        hashMapUser.put("id", getId());
        hashMapUser.put("name", getName());
        hashMapUser.put("email", getEmail());
        hashMapUser.put("password", getPassword());
        hashMapUser.put("address", getAddress());
        hashMapUser.put("district", getDistrict());
        hashMapUser.put("number", getNumber());
        hashMapUser.put("cep", getCep());
        hashMapUser.put("states", getStates());

        return hashMapUser;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getStates() {
        return states;
    }

    public void setStates(String states) {
        this.states = states;
    }

}
