package com.mazindere.university_reviews_app.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class University {
    private String name;
    private String heroImage;
    private String description;
    private List<String> courses;
    private String funFact;
    private String website;
    private String mapsLink;


}
