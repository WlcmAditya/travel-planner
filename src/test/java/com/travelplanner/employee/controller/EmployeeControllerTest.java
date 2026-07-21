package com.travelplanner.employee.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.travelplanner.employee.dto.EmployeeRequest;
import com.travelplanner.employee.dto.EmployeeResponse;
import com.travelplanner.employee.exception.ResourceNotFoundException;
import com.travelplanner.employee.service.EmployeeService;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(EmployeeController.class)
class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private EmployeeService employeeService;

    @Test
    void listsEmployees() throws Exception {
        when(employeeService.findAll()).thenReturn(List.of(response()));

        mockMvc.perform(get("/api/employees"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].email").value("ada@example.com"));
    }

    @Test
    void createsEmployee() throws Exception {
        when(employeeService.create(any(EmployeeRequest.class))).thenReturn(response());

        mockMvc.perform(post("/api/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request())))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "http://localhost/api/employees/1"))
                .andExpect(jsonPath("$.email").value("ada@example.com"));
    }

    @Test
    void rejectsInvalidEmployee() throws Exception {
        EmployeeRequest invalidRequest = new EmployeeRequest("", "", "invalid", "", "", BigDecimal.ZERO, LocalDate.now());

        mockMvc.perform(post("/api/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Validation failed"));
    }

    @Test
    void returnsNotFound() throws Exception {
        when(employeeService.findById(99L)).thenThrow(new ResourceNotFoundException("Employee not found with id 99"));

        mockMvc.perform(get("/api/employees/99"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Employee not found with id 99"));
    }

    @Test
    void deletesEmployee() throws Exception {
        mockMvc.perform(delete("/api/employees/1"))
                .andExpect(status().isNoContent());

        verify(employeeService).delete(1L);
    }

    private EmployeeRequest request() {
        return new EmployeeRequest("Ada", "Lovelace", "ada@example.com", "Engineer", "Technology", BigDecimal.valueOf(125000), LocalDate.of(2024, 1, 15));
    }

    private EmployeeResponse response() {
        return new EmployeeResponse(1L, "Ada", "Lovelace", "ada@example.com", "Engineer", "Technology", BigDecimal.valueOf(125000), LocalDate.of(2024, 1, 15));
    }
}
