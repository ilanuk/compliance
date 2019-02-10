package com.company.compliance.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

@RequiredArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
@Entity
public class ComplianceAttempt {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="attempt_id")
    private final Integer id;

    @Column(nullable=false)
    private final String region;

    @Column(nullable=false)
    private final Long applicationId;

    @Column(nullable=false)
    private final Long changeRecordId;

    @Column(nullable=false)
    private final boolean result;

    @Column(nullable = false)
    private final Date timeAttempted;

    // Empty constructor for JSON/JPA
    ComplianceAttempt() {
        id = -1;
        region = null;
        applicationId = -1l;
        changeRecordId = -1l;
        result = false;
        timeAttempted = null;
    }
}
