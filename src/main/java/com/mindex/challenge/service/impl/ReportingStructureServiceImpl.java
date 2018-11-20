package com.mindex.challenge.service.impl;

import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.service.EmployeeService;
import com.mindex.challenge.service.ReportingStructureService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ReportingStructureServiceImpl implements ReportingStructureService {

    @Resource
    private EmployeeService employeeService;

    @Override
    public ReportingStructure getReportingStructure(String employeeId) {
        ReportingStructure reportingStructure = new ReportingStructure();
        reportingStructure.setEmployee(employeeService.read(employeeId));
        reportingStructure.setNumberOfReports(this.getReportCount(employeeId));

        return reportingStructure;
    }

    private int getReportCount(String employeeId) {
        Employee employee = employeeService.read(employeeId);
        int numberOfReports = 0;
        if (employee != null && employee.getDirectReports() != null) {
            for (Employee directReport : employee.getDirectReports()) {
                numberOfReports++;
                numberOfReports += this.getReportCount(directReport.getEmployeeId());
            }
        }

        return numberOfReports;
    }
}
