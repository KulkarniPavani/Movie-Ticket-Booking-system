package org.example.dao;

import org.example.model.Theatre;

import java.util.List;

public interface TheatreDAO {

    boolean addTheatre(Theatre theatre);

    Theatre findTheatreById(int theatreId);

    boolean reviseTheatre(Theatre theatre);

    boolean removeTheatre(int theatreId);

    List<Theatre> viewAllTheatres();
}