package com.example.arithmeticgame;

import java.io.Serializable;

public class Account implements Serializable {
    public String mail,user,pass,confirm;

    public Account(String mail, String user, String pass, String confirm) {
        this.mail = mail;
        this.user = user;
        this.pass = pass;
        this.confirm = confirm;
    }

    public Account() {
    }
}
