package com.example.educheck.Controleur.Dashboard;

public class MessageLayout
{
    private String text;
    private boolean belongsToCurrentUser;

    private String name;

    public MessageLayout(String text, boolean belongsToCurrentUser,String name) {
        this.text = text;
        this.belongsToCurrentUser = belongsToCurrentUser;
        this.name=name;
    }

    public String getText() {
        return text;
    }

    public String getName() { return name;}

    public boolean isBelongsToCurrentUser() {
        return belongsToCurrentUser;
    }
}
