package org.example.service;

import org.example.dao.ShowDAO;
import org.example.dao.ShowDAOImpl;
import org.example.model.Show;

import java.util.List;

public class ShowServiceImpl implements ShowService {

    private final ShowDAO showDAO;

    public ShowServiceImpl() {
        this.showDAO = new ShowDAOImpl();
    }

    public ShowServiceImpl(ShowDAO showDAO) {
        this.showDAO = showDAO;
    }

    @Override
    public boolean insertShow(Show show) {

        if (show == null ||
                show.getMovie() == null ||
                show.getMovie().getMovieId() <= 0 ||
                show.getShowDate() == null ||
                show.getStartTime() == null ||
                show.getEndTime() == null) {

            return false;
        }

        return showDAO.insertShow(show);
    }

    @Override
    public boolean saveShow(Show show) {
        return insertShow(show);
    }

    @Override
    public Show fetchShow(int showId) {

        if (showId <= 0) {
            return null;
        }

        return showDAO.fetchShow(showId);
    }

    @Override
    public boolean adjustShow(Show show) {

        if (show == null ||
                show.getShowId() <= 0 ||
                show.getMovie() == null ||
                show.getMovie().getMovieId() <= 0 ||
                show.getShowDate() == null ||
                show.getStartTime() == null ||
                show.getEndTime() == null) {

            return false;
        }

        return showDAO.adjustShow(show);
    }

    @Override
    public boolean removeShow(int showId) {

        if (showId <= 0) {
            return false;
        }

        return showDAO.removeShow(showId);
    }

    @Override
    public List<Show> getAllShows() {
        return showDAO.getAllShows();
    }
}