package org.example.service;

import org.example.dao.ShowDAO;
import org.example.model.Movie;
import org.example.model.Show;
import org.example.model.Theatre;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ShowServiceImplTest {

    private ShowDAO showDAO;
    private ShowServiceImpl showService;

    @BeforeEach
    void setUp() {
        showDAO = Mockito.mock(ShowDAO.class);
        showService = new ShowServiceImpl(showDAO);
    }

    @Test
    void testInsertShow() {
        Theatre theatre = new Theatre();
        theatre.setTheatreId(1);

        Movie movie = new Movie();
        movie.setMovieId(1);

        Show show = new Show();
        show.setTheatre(theatre);
        show.setMovie(movie);
        show.setShowDate(LocalDate.of(2026, 9, 27));
        show.setStartTime(LocalTime.of(10, 0));
        show.setEndTime(LocalTime.of(13, 0));

        when(showDAO.insertShow(show)).thenReturn(true);

        boolean result = showService.insertShow(show);

        assertTrue(result);
        verify(showDAO).insertShow(show);
    }

    @Test
    void testFetchShow() {
        Show show = new Show();
        show.setShowId(1);

        when(showDAO.fetchShow(1)).thenReturn(show);

        Show result = showService.fetchShow(1);

        assertNotNull(result);
        assertEquals(1, result.getShowId());
        verify(showDAO).fetchShow(1);
    }

    @Test
    void testAdjustShow() {
        Theatre theatre = new Theatre();
        theatre.setTheatreId(1);

        Movie movie = new Movie();
        movie.setMovieId(1);

        Show show = new Show();
        show.setShowId(1);
        show.setTheatre(theatre);
        show.setMovie(movie);
        show.setShowDate(LocalDate.of(2026, 9, 27));
        show.setStartTime(LocalTime.of(10, 0));
        show.setEndTime(LocalTime.of(13, 0));

        when(showDAO.adjustShow(show)).thenReturn(true);

        boolean result = showService.adjustShow(show);

        assertTrue(result);
        verify(showDAO).adjustShow(show);
    }

    @Test
    void testRemoveShow() {
        when(showDAO.removeShow(1)).thenReturn(true);

        boolean result = showService.removeShow(1);

        assertTrue(result);
        verify(showDAO).removeShow(1);
    }

    @Test
    void testGetAllShows() {
        Show show = new Show();
        show.setShowId(1);

        List<Show> shows = List.of(show);

        when(showDAO.getAllShows()).thenReturn(shows);

        List<Show> result = showService.getAllShows();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(1, result.get(0).getShowId());
        verify(showDAO).getAllShows();
    }
}