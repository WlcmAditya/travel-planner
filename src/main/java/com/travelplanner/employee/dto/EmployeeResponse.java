package com.travelplanner.employee.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record EmployeeResponse(
        Long id,
        String firstName,
        String lastName,
        String email,
        String jobTitle,
        String department,
        BigDecimal salary,
        LocalDate hireDate) {
}
