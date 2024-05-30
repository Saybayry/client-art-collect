package com.example.client.model;



public class ArtMovement {

    private Long id;
    private String name;


    public ArtMovement() {
    }

    public ArtMovement(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return  name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
