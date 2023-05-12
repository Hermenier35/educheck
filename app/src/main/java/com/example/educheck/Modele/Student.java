package com.example.educheck.Modele;

public class Student {
    private String firstName;
    private String lastName;
    private String mail;
    private int ine;

    public Student(String firstName, String lastName, String mail, int ine) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.mail = mail;
        this.ine = ine;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public int getIne() {
        return ine;
    }

    public void setIne(int ine) {
        this.ine = ine;
    }
}
