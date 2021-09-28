package com.tiktzuki.hyper_contacts;

public class ContactModel {
    public String id, phone, name;

    public ContactModel() {
    }

    public ContactModel(String name, String phone) {
        this.name = name;
        this.phone = phone;
    }

}
