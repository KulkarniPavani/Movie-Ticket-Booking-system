package org.example.controller;

import org.example.model.Show;
import org.example.service.ShowService;
import org.example.service.ShowServiceImpl;

import java.util.List;

public class ShowController {

    private final ShowService showService;

    public ShowController() {
        this.showService = new ShowServiceImpl();
    }

    public ShowController(ShowService showService) {
        this.showService = showService;
    }

    public boolean insertShow(Show show) {
        return showService.insertShow(show);
    }

    public Show fetchShow(int showId) {
        return showService.fetchShow(showId);
    }

    public boolean updateShow(Show show) {
        return showService.adjustShow(show);
    }

    public boolean deleteShow(int showId) {
        return showService.removeShow(showId);
    }

    public List<Show> getAllShows() {
        return showService.getAllShows();
    }
}