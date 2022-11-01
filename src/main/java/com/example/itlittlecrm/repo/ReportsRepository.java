package com.example.itlittlecrm.repo;

import com.example.itlittlecrm.models.Reports;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ReportsRepository extends CrudRepository<Reports, Long> {
    List<Reports> findByReportNameContains(String reportName);
    Reports findByReportName(String reportName);
}
