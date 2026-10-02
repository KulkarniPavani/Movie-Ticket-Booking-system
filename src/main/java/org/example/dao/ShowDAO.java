package org.example.dao;

import org.example.model.Show;

import java.util.List;

public interface ShowDAO {

    boolean insertShow(Show show);

    Show fetchShow(int showId);

    boolean adjustShow(Show show);

    boolean removeShow(int showId);

    List<Show> getAllShows();
}