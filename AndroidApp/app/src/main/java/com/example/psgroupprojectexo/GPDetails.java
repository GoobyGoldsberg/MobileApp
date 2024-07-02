package com.example.psgroupprojectexo;

public class GPDetails {
    public String id;
    public String name;
    public String practiceName;
    public String address;
    public String phoneNumber;

    public GPDetails() {
    }

    public GPDetails(String id, String name, String practiceName, String address, String phoneNumber) {
        this.id = id;
        this.name = name;
        this.practiceName = practiceName;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPracticeName() {
        return practiceName;
    }

    public String getAddress() {
        return address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }


}
