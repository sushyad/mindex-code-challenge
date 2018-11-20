package com.mindex.challenge.data;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CompensationNotFoundException extends RuntimeException {
    private String employeeId;

    public CompensationNotFoundException(String employeeId) {
        this.employeeId = employeeId;
    }

    @Override
    public String getMessage() {
        return "No compensation record exists for employeeId: " + employeeId;
    }

    public String getEmployeeId() {
        return employeeId;
    }
}
