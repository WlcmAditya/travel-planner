package com.travelplanner.employee.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDate;

public record EmployeeRequest(
        @NotBlank @Size(max = 100) String firstName,
        @NotBlank @Size(max = 100) String lastName,
        @NotBlank @Email @Size(max = 150) String email,
        @NotBlank @Size(max = 120) String jobTitle,
        @NotBlank @Size(max = 120) String department,
        @NotNull @DecimalMin(value = "0.0", inclusive = false) BigDecimal salary,
        @NotNull @PastOrPresent LocalDate hireDate) {
}
