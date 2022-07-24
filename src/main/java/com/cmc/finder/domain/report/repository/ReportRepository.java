package com.cmc.finder.domain.report.repository;

import com.cmc.finder.domain.model.ServiceType;
import com.cmc.finder.domain.report.entity.Report;
import com.cmc.finder.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReportRepository extends JpaRepository<Report, Long> {

    Boolean existsByFromAndServiceTypeAndServiceId(User from, ServiceType serviceType, Long serviceId);

    Boolean existsByFromAndTo(User from, User to);

    List<Report> findAllByFromAndServiceType(User from, ServiceType serviceType);

}
