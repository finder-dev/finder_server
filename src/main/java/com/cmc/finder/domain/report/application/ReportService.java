package com.cmc.finder.domain.report.application;

import com.cmc.finder.domain.report.entity.Report;
import com.cmc.finder.domain.report.repository.ReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReportService {

    private final ReportRepository reportRepository;

    @Transactional
    public void create(Report report) {
        reportRepository.save(report);
    }

    public Boolean alreadyReceivedReport(Report report) {
        return reportRepository.existsByFromAndServiceTypeAndServiceId(report.getFrom(), report.getServiceType(), report.getServiceId());

    }
}
