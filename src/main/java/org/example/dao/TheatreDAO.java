package org.example.dao;

import org.example.model.Theatre;

public interface TheatreDAO {

    boolean addTheatre(Theatre theatre);

    Theatre findTheatreById(int theatreId);

    boolean reviseTheatre(Theatre theatre);

    boolean removeTheatre(int theatreId);
}