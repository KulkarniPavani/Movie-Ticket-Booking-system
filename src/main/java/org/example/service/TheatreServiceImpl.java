package org.example.service;

import org.example.dao.TheatreDAO;
import org.example.dao.TheatreDAOImpl;
import org.example.model.Theatre;

import java.util.List;

public class TheatreServiceImpl implements TheatreService {

    private final TheatreDAO theatreDAO;

    public TheatreServiceImpl() {
        this.theatreDAO = new TheatreDAOImpl();
    }

    public TheatreServiceImpl(TheatreDAO theatreDAO) {
        this.theatreDAO = theatreDAO;
    }

    @Override
    public boolean addTheatre(Theatre theatre) {

        if (theatre == null ||
                theatre.getName() == null ||
                theatre.getName().isBlank() ||
                theatre.getCity() == null ||
                theatre.getCity().isBlank() ||
                theatre.getTotalSeats() <= 0) {

            return false;
        }

        return theatreDAO.addTheatre(theatre);
    }

    @Override
    public Theatre findTheatreById(int theatreId) {

        if (theatreId <= 0) {
            return null;
        }

        return theatreDAO.findTheatreById(theatreId);
    }

    @Override
    public boolean reviseTheatre(Theatre theatre) {

        if (theatre == null || theatre.getTheatreId() <= 0) {
            return false;
        }

        return theatreDAO.reviseTheatre(theatre);
    }

    @Override
    public boolean removeTheatre(int theatreId) {

        if (theatreId <= 0) {
            return false;
        }

        return theatreDAO.removeTheatre(theatreId);
    }

    @Override
    public List<Theatre> viewAllTheatres() {
        return theatreDAO.viewAllTheatres();
    }
}