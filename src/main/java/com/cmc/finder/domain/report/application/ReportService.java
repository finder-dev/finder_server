package com.cmc.finder.domain.report.application;

import com.cmc.finder.domain.model.ServiceType;
import com.cmc.finder.domain.report.entity.Report;
import com.cmc.finder.domain.report.repository.ReportRepository;
import com.cmc.finder.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.cmc.finder.domain.report.entity.QReport.report;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReportService {

    private final ReportRepository reportRepository;

    @Transactional
    public void create(Report report) {
        reportRepository.save(report);
    }

    public Boolean alreadyReportedUser(Report report) {
        return reportRepository.existsByFromAndTo(report.getFrom(), report.getTo());
    }

    public Boolean alreadyReceivedReport(Report report) {
        return reportRepository.existsByFromAndServiceTypeAndServiceId(report.getFrom(), report.getServiceType(), report.getServiceId());
    }

    public List<Long> getReportsByUser(User user, ServiceType serviceType) {
        return reportRepository.findAllByFromAndServiceType(user, serviceType).stream().map(Report::getServiceId).collect(Collectors.toList());
    }



}
