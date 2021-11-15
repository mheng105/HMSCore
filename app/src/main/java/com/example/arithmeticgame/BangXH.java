package com.example.arithmeticgame;

public class BangXH {
    private int ID;
    private String Nickname;
    private int Score;
    private int Time;
    private int Level;
    public BangXH(String nickname, int score, int time,int level) {
        Nickname = nickname;
        Score = score;
        Time = time;
        Level=level;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getNickname() {
        return Nickname;
    }

    public void setNickname(String nickname) {
        Nickname = nickname;
    }

    public int getLevel() {
        return Level;
    }

    public void setLevel(int level) {
        Level = level;
    }

    public int getScore() {
        return Score;
    }

    public void setScore(int score) {
        Score = score;
    }

    public int getTime() {
        return Time;
    }

    public void setTime(int time) {
        Time = time;
    }

    @Override
    public String toString() {
        return "BangXH{" +

                ", Nickname='" + Nickname + '\'' +
                ", Score=" + Score +
                ", Time=" + Time +
                ", Level=" + Level +
                '}';
    }
}
