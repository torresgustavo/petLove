package Entities;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import fireBaseConfiguration.FireBaseConfig;

public class Users {
    private String id ,name, lastname, password, email, address, city ,district, number, cep, states;

    public Users() {
    }

    public void save (){
        DatabaseReference reference = FireBaseConfig.getFireBase();
        reference.child("usuarios").child(String.valueOf(getId())).setValue(this);
    }

    public static String generateHashPassword (String userPassword) throws NoSuchAlgorithmException, UnsupportedEncodingException {

        MessageDigest algorithm = MessageDigest.getInstance("SHA-256");
        byte messageDigestPasswordUser[] = algorithm.digest(userPassword.getBytes("UTF-8"));

        StringBuilder hexStringUserPassword = new StringBuilder();
        for (byte b : messageDigestPasswordUser) {
            hexStringUserPassword.append(String.format("%02X", 0xFF & b));
        }

        return hexStringUserPassword.toString();

    }


    public Map<String, Object> toMap(){
        HashMap<String, Object> hashMapUser = new HashMap<>();

        hashMapUser.put("id", getId());
        hashMapUser.put("name", getName());
        hashMapUser.put("lastname", getLastname());
        hashMapUser.put("email", getEmail());
        hashMapUser.put("password", getPassword());
        hashMapUser.put("address", getAddress());
        hashMapUser.put("district", getDistrict());
        hashMapUser.put("number", getNumber());
        hashMapUser.put("cep", getCep());
        hashMapUser.put("city", getCity());
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

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
