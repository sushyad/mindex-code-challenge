package com.mindex.challenge.service.impl;

import com.mindex.challenge.data.Employee;
import com.mindex.challenge.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private static final Logger LOG = LoggerFactory.getLogger(EmployeeServiceImpl.class);

    @Override
    public Employee create(Employee employee) {
        LOG.debug("Creating employee [{}]", employee);

        return employee;
    }

    @Override
    public Employee read(String id) {
        LOG.debug("Creating employee with id [{}]", id);

        return new Employee();
    }

    @Override
    public Employee update(String id, Employee employee) {
        LOG.debug("Updating employee [{}]", employee);

        return employee;
    }
}
