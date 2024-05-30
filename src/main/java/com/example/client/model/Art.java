package com.example.client.model;


import java.util.Date;
import java.util.HashSet;
import java.util.Set;


public class Art {
    public Art() {
        // Пустой конструктор
    }
    public Art(String name, Genre genre, ArtMovement artMovement, Technique technique, Artist artist, Date creationDate, Long size, String tags, String imageName) {
        this.name = name;
        this.genre = genre;
        this.artMovement = artMovement;
        this.technique = technique;
        this.artist = artist ;
        this.creationDate = creationDate;
        this.size = size;
//        this.tags = tags;
        this.imagePath = imageName;
    }

    private Long id;


    private String name;

    private Genre genre;


    private ArtMovement artMovement;


    private Technique technique;


    private Artist artist;


    private Date creationDate;

    private Long size;


    private Set<Tag> tags = new HashSet<>();



    private String imagePath;


    private Integer price;


    private Integer popularity;


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

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public ArtMovement getArtMovement() {
        return artMovement;
    }

    public void setArtMovement(ArtMovement artMovement) {
        this.artMovement = artMovement;
    }

    public Technique getTechnique() {
        return technique;
    }

    public void setTechnique(Technique technique) {
        this.technique = technique;
    }

    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getPopularity() {
        return popularity;
    }

    public void setPopularity(Integer popularity) {
        this.popularity = popularity;
    }


//    @ManyToOne
////    @JoinColumn(name = "Сollection")
////    private Collection collection;

    // Getters and setters
}
