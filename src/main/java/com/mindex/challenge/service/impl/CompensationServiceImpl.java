package com.mindex.challenge.service.impl;

import com.mindex.challenge.dao.CompensationRepository;
import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.data.CompensationNotFoundException;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.service.CompensationService;
import com.mindex.challenge.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@Service
public class CompensationServiceImpl implements CompensationService {

    private static final Logger LOG = LoggerFactory.getLogger(CompensationServiceImpl.class);

    @Autowired
    private CompensationRepository repository;

    @Autowired
    private EmployeeService employeeService;

    @Override
    public Compensation create(Compensation compensation) {
        LOG.debug("Creating compensation [{}]", compensation);

        Employee employee = employeeService.read(compensation.getEmployee().getEmployeeId());
        //if denormalization is desired for document-based storage, uncomment the following line
        //compensation.setEmployee(employee);

        repository.insert(compensation);

        return compensation;
    }

    @Override
    public List<Compensation> read(String id) {
        LOG.debug("Reading employee with id [{}]", id);

        List<Compensation> compensation = repository.findByEmployeeEmployeeId(id);

        if (compensation == null) {
            throw new CompensationNotFoundException(id);
        }

        return compensation;
    }
}
