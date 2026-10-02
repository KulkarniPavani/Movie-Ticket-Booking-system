package org.example.controller;

import org.example.model.Theatre;
import org.example.service.TheatreService;
import org.example.service.TheatreServiceImpl;

import java.util.List;

public class TheatreController {

    private final TheatreService theatreService =
            new TheatreServiceImpl();

    public boolean addTheatre(Theatre theatre) {
        return theatreService.addTheatre(theatre);
    }

    public Theatre findTheatreById(int theatreId) {
        return theatreService.findTheatreById(theatreId);
    }

    public boolean reviseTheatre(Theatre theatre) {
        return theatreService.reviseTheatre(theatre);
    }

    public boolean removeTheatre(int theatreId) {
        return theatreService.removeTheatre(theatreId);
    }

    public List<Theatre> viewAllTheatres() {
        return theatreService.viewAllTheatres();
    }
}