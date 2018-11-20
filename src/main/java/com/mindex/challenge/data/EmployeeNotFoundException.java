package com.mindex.challenge.data;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class EmployeeNotFoundException extends RuntimeException {
    private String employeeId;

    public EmployeeNotFoundException(String employeeId) {
        this.employeeId = employeeId;
    }

    @Override
    public String getMessage() {
        return "No employee record exists for employeeId: " + employeeId;
    }

    public String getEmployeeId() {
        return employeeId;
    }
}
