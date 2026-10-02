package org.example.service;

import org.example.model.Show;

import java.util.List;

public interface ShowService {

    boolean insertShow(Show show);

    boolean saveShow(Show show);

    Show fetchShow(int showId);

    boolean adjustShow(Show show);

    boolean removeShow(int showId);

    List<Show> getAllShows();
}