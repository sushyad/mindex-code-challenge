package com.mindex.challenge.controller;

import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.service.CompensationService;
import com.mindex.challenge.service.EmployeeService;
import com.mindex.challenge.service.ReportingStructureService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.ZonedDateTime;
import java.util.List;

@RestController
@RequestMapping("/employee/{id}/compensation")
public class CompensationController {
    private static final Logger LOG = LoggerFactory.getLogger(CompensationController.class);

    @Autowired
    private CompensationService compensationService;

    @GetMapping
    public List<Compensation> getCompensation(@PathVariable String id) {
        LOG.debug("Received compensation read request for id [{}]", id);

        return compensationService.read(id);
    }

    /*
     * Sample json request: {"salary": "80000", "effectiveDate": "2016-11-20T05:00:00Z"}
     */
    @PostMapping
    public Compensation create(@RequestBody Compensation compensation, @PathVariable String id) {
        LOG.debug("Received compensation create request for [{}]", compensation);
        if (compensation != null && compensation.getEmployee() == null) {
            Employee employee = new Employee();
            employee.setEmployeeId(id);
            compensation.setEmployee(employee);
        }

        if (compensation.getEffectiveDate() == null) {
            compensation.setEffectiveDate(ZonedDateTime.now());
        }

        return compensationService.create(compensation);
    }
}
