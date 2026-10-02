package org.example.service;

import org.example.dao.MovieDAO;
import org.example.model.Movie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MovieServiceImplTest {

    private MovieDAO movieDAO;
    private MovieServiceImpl movieService;

    @BeforeEach
    void setUp() {
        movieDAO = Mockito.mock(MovieDAO.class);
        movieService = new MovieServiceImpl(movieDAO);
    }

    @Test
    void testSaveMovie() {

        Movie movie = new Movie();
        movie.setTitle("RRR");
        movie.setLanguage("Telugu");
        movie.setGenre("Action");
        movie.setDuration(180);
        movie.setReleaseDate(LocalDate.of(2022, 3, 25));

        when(movieDAO.saveMovie(movie)).thenReturn(true);

        boolean result = movieService.saveMovie(movie);

        assertTrue(result);

        verify(movieDAO).saveMovie(movie);
    }

    @Test
    void testFindMovieById() {

        Movie movie = new Movie();
        movie.setMovieId(1);
        movie.setTitle("RRR");

        when(movieDAO.findMovieById(1)).thenReturn(movie);

        Movie result = movieService.findMovieById(1);

        assertNotNull(result);
        assertEquals("RRR", result.getTitle());

        verify(movieDAO).findMovieById(1);
    }

    @Test
    void testModifyMovie() {

        Movie movie = new Movie();
        movie.setMovieId(1);
        movie.setTitle("RRR");
        movie.setLanguage("Telugu");
        movie.setGenre("Action");
        movie.setDuration(180);
        movie.setReleaseDate(LocalDate.of(2022, 3, 25));

        when(movieDAO.modifyMovie(movie)).thenReturn(true);

        boolean result = movieService.modifyMovie(movie);

        assertTrue(result);

        verify(movieDAO).modifyMovie(movie);
    }

    @Test
    void testEraseMovie() {

        when(movieDAO.eraseMovie(1)).thenReturn(true);

        boolean result = movieService.eraseMovie(1);

        assertTrue(result);

        verify(movieDAO).eraseMovie(1);
    }
}