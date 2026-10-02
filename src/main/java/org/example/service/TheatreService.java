package org.example.service;

import org.example.model.Theatre;

import java.util.List;

public interface TheatreService {

    boolean addTheatre(Theatre theatre);

    Theatre findTheatreById(int theatreId);

    boolean reviseTheatre(Theatre theatre);

    boolean removeTheatre(int theatreId);

    List<Theatre> viewAllTheatres();
}