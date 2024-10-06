package dev.buddly.flow_sync.repository;

import dev.buddly.flow_sync.model.Report;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReportRepository extends JpaRepository<Report,Integer> {
    List<Report> findAllByReportNameContaining(String reportName);
    List<Report> findAllByDescriptionContaining(String description);
}
