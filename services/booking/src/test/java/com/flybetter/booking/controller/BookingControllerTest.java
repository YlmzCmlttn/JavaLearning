package com.flybetter.booking.controller;

import com.flybetter.booking.entity.Booking;
import com.flybetter.booking.repository.BookingRepository;
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
public class BookingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BookingRepository bookingRepository;

    @BeforeEach
    public void setup() {
        bookingRepository.deleteAll(); // Clear existing data
        bookingRepository.saveAll(Arrays.asList(
                new Booking("John Doe", "Flight 101"),
                new Booking("Jane Smith", "Flight 202")
        ));
    }

    @Test
    public void testGetBookings() throws Exception {
        mockMvc.perform(get("/bookings"))
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"passengerName\":\"John Doe\",\"flightNumber\":\"Flight 101\"}," +
                        "{\"passengerName\":\"Jane Smith\",\"flightNumber\":\"Flight 202\"}]"));
    }

    @Test
    public void testCreateBooking() throws Exception {
        mockMvc.perform(post("/bookings")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"passengerName\": \"Alice Johnson\", \"flightNumber\": \"Flight 303\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.passengerName").value("Alice Johnson"))
                .andExpect(jsonPath("$.flightNumber").value("Flight 303"));
    }

    @Test
    public void testUpdateBooking() throws Exception {
        Booking booking = bookingRepository.save(new Booking("Bob Brown", "Flight 404"));

        mockMvc.perform(put("/bookings/" + booking.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"passengerName\": \"Bob Brown\", \"flightNumber\": \"Flight 505\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.passengerName").value("Bob Brown"))
                .andExpect(jsonPath("$.flightNumber").value("Flight 505"));
    }

    @Test
    public void testDeleteBooking() throws Exception {
        Booking booking = bookingRepository.save(new Booking("Charlie Green", "Flight 606"));

        mockMvc.perform(delete("/bookings/" + booking.getId()))
                .andExpect(status().isOk());

        mockMvc.perform(get("/bookings/" + booking.getId()))
                .andExpect(status().isNotFound());
    }
} 