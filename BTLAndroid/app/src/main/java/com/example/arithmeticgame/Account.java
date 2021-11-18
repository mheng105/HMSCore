package com.example.arithmeticgame;

import java.io.Serializable;

public class Account implements Serializable {
    public String mail,user,pass;

    public Account() {
    }

    public Account(String mail, String user, String pass) {
        this.mail = mail;
        this.user = user;
        this.pass = pass;
    }

}
