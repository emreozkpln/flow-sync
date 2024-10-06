package dev.buddly.flow_sync.controller;

import dev.buddly.flow_sync.dto.ReportDto;
import dev.buddly.flow_sync.dto.ResponseHandler;
import dev.buddly.flow_sync.service.ReportService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/report")
@Tag(name = "Report")
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping
    public ResponseEntity<List<ReportDto>> getAllReports(){
        List<ReportDto> reports = reportService.findAllReports();
        return ResponseEntity.ok(reports);
    }
    @GetMapping("/id/{id}")
    public ResponseEntity<ReportDto> getReportById(@PathVariable Integer id){
        ReportDto report = reportService.findByReportId(id);
        return ResponseEntity.ok(report);
    }
    @GetMapping("/reportName/{name}")
    public ResponseEntity<List<ReportDto>> getReportByName(@PathVariable String name){
        List<ReportDto> report = reportService.findByReportName(name);
        return ResponseEntity.ok(report);
    }
    @GetMapping("/reportDes/{description}")
    public ResponseEntity<List<ReportDto>> getReportByDescription(@PathVariable String description){
        List<ReportDto> report = reportService.findByDescription(description);
        return ResponseEntity.ok(report);
    }
    @PostMapping
    public ResponseEntity<Object> addReports(@RequestBody ReportDto reportDto){
        ReportDto report = reportService.addReport(reportDto);
        return ResponseHandler.responseBuilder("Report added successfully",HttpStatus.CREATED,report);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteReport(@PathVariable Integer id){
        reportService.deleteReport(id);
        return ResponseEntity.ok("Report deleted successfully");
    }
}
