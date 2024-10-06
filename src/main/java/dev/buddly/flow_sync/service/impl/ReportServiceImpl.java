package dev.buddly.flow_sync.service.impl;

import dev.buddly.flow_sync.dto.ReportDto;
import dev.buddly.flow_sync.exception.NotFoundException;
import dev.buddly.flow_sync.model.Report;
import dev.buddly.flow_sync.repository.ReportRepository;
import dev.buddly.flow_sync.service.ReportService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReportServiceImpl implements ReportService {
    private final ReportRepository reportRepository;
    private final ModelMapper modelMapper;

    public ReportServiceImpl(ReportRepository reportRepository, ModelMapper modelMapper) {
        this.reportRepository = reportRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<ReportDto> findAllReports() {
        List<Report> reports = reportRepository.findAll();
        return reports
                .stream()
                .map(report-> modelMapper.map(report,ReportDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public ReportDto findByReportId(Integer reportId) {
        Report report = reportRepository.findById(reportId)
                .orElseThrow(()->new NotFoundException("ID not found"));
        return modelMapper.map(report,ReportDto.class);
    }

    @Override
    public List<ReportDto> findByReportName(String reportName) {
        List<Report> reports = reportRepository.findAllByReportNameContaining(reportName);
        return reports.stream()
                .filter(report->report.getReportName().contains(reportName))
                .map(report->modelMapper.map(report,ReportDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<ReportDto> findByDescription(String description) {
        List<Report> reports = reportRepository.findAllByDescriptionContaining(description);
        return reports.stream()
                .filter(report->report.getDescription().contains(description))
                .map(report->modelMapper.map(report,ReportDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public ReportDto addReport(ReportDto reportDto) {
        Report report = modelMapper.map(reportDto,Report.class);
        Report savedReport = reportRepository.save(report);
        return modelMapper.map(savedReport,ReportDto.class);
    }

    @Override
    public void deleteReport(Integer id) {
        Report report = reportRepository.findById(id)
                .orElseThrow(()->new NotFoundException("Id not found"));
        reportRepository.delete(report);
    }
}
