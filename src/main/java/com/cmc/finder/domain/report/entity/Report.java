package com.cmc.finder.domain.report.entity;

import com.cmc.finder.domain.base.BaseTimeEntity;
import com.cmc.finder.domain.model.ServiceType;
import com.cmc.finder.domain.user.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "report")
@Getter
@NoArgsConstructor
public class Report extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reportId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private ServiceType serviceType;

    @ManyToOne(
            fetch = FetchType.LAZY
    )
    @JoinColumn(name = "from_user_id", nullable = false)
    private User from;

    @ManyToOne(
            fetch = FetchType.LAZY
    )
    @JoinColumn(name = "to_user_id", nullable = false)
    private User to;

    @Column(nullable = false)
    private Long serviceId;

    @Builder
    public Report(ServiceType serviceType, User from, User to, Long serviceId) {
        this.serviceType = serviceType;
        this.from = from;
        this.to = to;
        this.serviceId = serviceId;
    }

    public static Report createReport(ServiceType serviceType, User from, User to, Long serviceId) {

        return Report.builder()
                .serviceType(serviceType)
                .from(from)
                .to(to)
                .serviceId(serviceId)
                .build();
    }

}
