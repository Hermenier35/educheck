package com.example.educheck.Controleur.Dashboard;

public  class Pair {
    private String mail;
    private String received;

    public Pair(String mail, String received) {
        this.mail = mail;
        this.received = received;
    }

    public String getMail() {
        return mail;
    }

    public String getReceived() {
        return received;
    }
}