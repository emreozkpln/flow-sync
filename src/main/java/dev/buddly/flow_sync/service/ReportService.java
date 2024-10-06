package dev.buddly.flow_sync.service;

import dev.buddly.flow_sync.dto.ReportDto;

import java.util.List;

public interface ReportService {
    List<ReportDto> findAllReports();
    ReportDto findByReportId(Integer reportId);
    List<ReportDto> findByReportName(String reportName);
    List<ReportDto> findByDescription(String description);
    ReportDto addReport(ReportDto reportDto);
    void deleteReport(Integer id);
}
