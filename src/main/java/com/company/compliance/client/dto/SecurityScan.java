package com.company.compliance.client.dto;

import com.company.compliance.client.dto.deserializer.SecurityScanDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
@JsonDeserialize(using = SecurityScanDeserializer.class)
public class SecurityScan {
    private final long applicationId;
    private final boolean securityStatusValid;

    SecurityScan() {
        applicationId = -1l;
        securityStatusValid = false;
    }
}
