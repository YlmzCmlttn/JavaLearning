package com.flybetter.flightscheduling.controller;

import com.flybetter.flightscheduling.entity.Flight;
import com.flybetter.flightscheduling.repository.FlightRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class FlightControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private FlightRepository flightRepository;

    @BeforeEach
    public void setup() {
        flightRepository.deleteAll(); // Clear existing data
        flightRepository.saveAll(Arrays.asList(
                new Flight("Flight 101", "On Time"),
                new Flight("Flight 202", "Delayed"),
                new Flight("Flight 303", "Canceled")
        ));
    }

    @Test
    public void testGetFlights() throws Exception {
        mockMvc.perform(get("/flights"))
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"flightNumber\":\"Flight 101\",\"status\":\"On Time\"}," +
                        "{\"flightNumber\":\"Flight 202\",\"status\":\"Delayed\"}," +
                        "{\"flightNumber\":\"Flight 303\",\"status\":\"Canceled\"}]"));
    }

    @Test
    public void testCreateFlight() throws Exception {
        mockMvc.perform(post("/flights")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"flightNumber\": \"Flight 404\", \"status\": \"Scheduled\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.flightNumber").value("Flight 404"))
                .andExpect(jsonPath("$.status").value("Scheduled"));
    }

    @Test
    public void testUpdateFlight() throws Exception {
        Flight flight = flightRepository.save(new Flight("Flight 505", "Scheduled"));

        mockMvc.perform(put("/flights/" + flight.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"flightNumber\": \"Flight 505\", \"status\": \"Delayed\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.flightNumber").value("Flight 505"))
                .andExpect(jsonPath("$.status").value("Delayed"));
    }
} 