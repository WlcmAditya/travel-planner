package com.travelplanner.employee.service;

import com.travelplanner.employee.dto.EmployeeRequest;
import com.travelplanner.employee.dto.EmployeeResponse;
import com.travelplanner.employee.exception.DuplicateResourceException;
import com.travelplanner.employee.exception.ResourceNotFoundException;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {

    private final Map<Long, EmployeeResponse> employees = new ConcurrentHashMap<>();
    private final AtomicLong idSequence = new AtomicLong(1);

    public List<EmployeeResponse> findAll() {
        return employees.values().stream()
                .sorted(Comparator.comparing(EmployeeResponse::id))
                .toList();
    }

    public EmployeeResponse findById(Long id) {
        return findEmployee(id);
    }

    public EmployeeResponse create(EmployeeRequest request) {
        ensureEmailAvailable(request.email(), null);
        Long id = idSequence.getAndIncrement();
        EmployeeResponse employee = toResponse(id, request);
        employees.put(id, employee);
        return employee;
    }

    public EmployeeResponse update(Long id, EmployeeRequest request) {
        findEmployee(id);
        ensureEmailAvailable(request.email(), id);
        EmployeeResponse employee = toResponse(id, request);
        employees.put(id, employee);
        return employee;
    }

    public void delete(Long id) {
        if (employees.remove(id) == null) {
            throw new ResourceNotFoundException("Employee not found with id " + id);
        }
    }

    private EmployeeResponse findEmployee(Long id) {
        EmployeeResponse employee = employees.get(id);
        if (employee == null) {
            throw new ResourceNotFoundException("Employee not found with id " + id);
        }
        return employee;
    }

    private void ensureEmailAvailable(String email, Long currentEmployeeId) {
        boolean emailExists = employees.values().stream()
                .anyMatch(employee -> employee.email().equalsIgnoreCase(email)
                        && !employee.id().equals(currentEmployeeId));
        if (emailExists) {
            throw new DuplicateResourceException("Employee email already exists");
        }
    }

    private EmployeeResponse toResponse(Long id, EmployeeRequest request) {
        return new EmployeeResponse(
                id,
                request.firstName(),
                request.lastName(),
                request.email(),
                request.jobTitle(),
                request.department(),
                request.salary(),
                request.hireDate());
    }
}
