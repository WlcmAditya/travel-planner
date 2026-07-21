package com.travelplanner.employee.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.travelplanner.employee.dto.EmployeeRequest;
import com.travelplanner.employee.dto.EmployeeResponse;
import com.travelplanner.employee.exception.DuplicateResourceException;
import com.travelplanner.employee.exception.ResourceNotFoundException;
import java.math.BigDecimal;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;

class EmployeeServiceTest {

    private final EmployeeService employeeService = new EmployeeService();

    @Test
    void createsAndReadsEmployee() {
        EmployeeResponse created = employeeService.create(request("ada@example.com"));

        assertThat(created.id()).isEqualTo(1L);
        assertThat(employeeService.findById(created.id()).email()).isEqualTo("ada@example.com");
        assertThat(employeeService.findAll()).hasSize(1);
    }

    @Test
    void updatesEmployee() {
        EmployeeResponse created = employeeService.create(request("ada@example.com"));

        EmployeeResponse updated = employeeService.update(created.id(), request("grace@example.com"));

        assertThat(updated.id()).isEqualTo(created.id());
        assertThat(updated.email()).isEqualTo("grace@example.com");
    }

    @Test
    void deletesEmployee() {
        EmployeeResponse created = employeeService.create(request("ada@example.com"));

        employeeService.delete(created.id());

        assertThatThrownBy(() -> employeeService.findById(created.id()))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void rejectsDuplicateEmail() {
        employeeService.create(request("ada@example.com"));

        assertThatThrownBy(() -> employeeService.create(request("ADA@example.com")))
                .isInstanceOf(DuplicateResourceException.class);
    }

    private EmployeeRequest request(String email) {
        return new EmployeeRequest(
                "Ada",
                "Lovelace",
                email,
                "Engineer",
                "Technology",
                BigDecimal.valueOf(125000),
                LocalDate.of(2024, 1, 15));
    }
}
