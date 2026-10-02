package org.example.service;

import org.example.dao.TheatreDAO;
import org.example.model.Theatre;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TheatreServiceImplTest {

    private TheatreDAO theatreDAO;
    private TheatreServiceImpl theatreService;

    @BeforeEach
    void setUp() {
        theatreDAO = Mockito.mock(TheatreDAO.class);
        theatreService = new TheatreServiceImpl(theatreDAO);
    }

    @Test
    void testAddTheatre() {

        Theatre theatre = new Theatre();
        theatre.setName("PVR Cinemas");
        theatre.setCity("Hyderabad");
        theatre.setTotalSeats(200);

        when(theatreDAO.addTheatre(theatre)).thenReturn(true);

        boolean result = theatreService.addTheatre(theatre);

        assertTrue(result);

        verify(theatreDAO).addTheatre(theatre);
    }

    @Test
    void testFindTheatreById() {

        Theatre theatre = new Theatre();
        theatre.setTheatreId(1);
        theatre.setName("PVR Cinemas");

        when(theatreDAO.findTheatreById(1)).thenReturn(theatre);

        Theatre result = theatreService.findTheatreById(1);

        assertNotNull(result);
        assertEquals("PVR Cinemas", result.getName());

        verify(theatreDAO).findTheatreById(1);
    }

    @Test
    void testReviseTheatre() {

        Theatre theatre = new Theatre();
        theatre.setTheatreId(1);
        theatre.setName("PVR Cinemas");
        theatre.setCity("Hyderabad");
        theatre.setTotalSeats(200);

        when(theatreDAO.reviseTheatre(theatre)).thenReturn(true);

        boolean result = theatreService.reviseTheatre(theatre);

        assertTrue(result);

        verify(theatreDAO).reviseTheatre(theatre);
    }

    @Test
    void testRemoveTheatre() {

        when(theatreDAO.removeTheatre(1)).thenReturn(true);

        boolean result = theatreService.removeTheatre(1);

        assertTrue(result);

        verify(theatreDAO).removeTheatre(1);
    }
}