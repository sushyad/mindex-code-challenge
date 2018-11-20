package com.mindex.challenge.service.impl;

import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.service.EmployeeService;
import com.mindex.challenge.service.ReportingStructureService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReportingStructureServiceImpl implements ReportingStructureService {

    @Resource
    private EmployeeService employeeService;

    @Override
    public ReportingStructure getReportingStructure(String employeeId) {
        Employee employee = employeeService.read(employeeId);
        ReportingStructure reportingStructure = new ReportingStructure();
        reportingStructure.setEmployee(employee);
        reportingStructure.setNumberOfReports(this.getReportCount(employee));

        return reportingStructure;
    }

    private int getReportCount(Employee employee) {
        int numberOfReports = 0;
        if (employee != null && employee.getDirectReports() != null) {
            List<Employee> directReports = new ArrayList();
            for (Employee directReport : employee.getDirectReports()) {
                numberOfReports++;
                Employee directReportEmployee = employeeService.read(directReport.getEmployeeId());
                numberOfReports += this.getReportCount(directReportEmployee);
                directReports.add(directReportEmployee);
            }

            employee.setDirectReports(directReports);
        }

        return numberOfReports;
    }
}
