package com.starreport.models;


import java.util.List;

public class PeopleReport {
    private String name;
    private int height;
    private int mass;
    private String hair_color;
    private String skin_color;
    private String eye_color;
    private String gender;
    private String homeworldName;
    private List<Films> films;
    private Integer qttFilms;

    public String getName() {

        return name;
    }

    public void setName(String name) {

        this.name = name;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getMass() {
        return mass;
    }

    public void setMass(int mass) {
        this.mass = mass;
    }

    public String getHair_color() {
        return hair_color;
    }

    public void setHair_color(String hair_color) {
        this.hair_color = hair_color;
    }

    public String getSkin_color() {
        return skin_color;
    }

    public void setSkin_color(String skin_color) {
        this.skin_color = skin_color;
    }

    public String getEye_color() {
        return eye_color;
    }

    public void setEye_color(String eye_color) {
        this.eye_color = eye_color;
    }


    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getHomeworldName() {
        return homeworldName;
    }

    public void setHomeworldName(String homeworldName) {
        this.homeworldName = homeworldName;
    }

    public List<Films> getFilms() {
        return films;
    }

    public void setFilms(List<Films> films) {
        this.films = films;
    }

    public Integer getQttFilms() {
        return qttFilms;
    }

    public void setQttFilms(Integer qttFilms) {
        this.qttFilms = qttFilms;
    }
}


