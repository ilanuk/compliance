package com.company.compliance.client.dto;

import com.company.compliance.client.dto.deserializer.ApplicationCatalogDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
@JsonDeserialize(using = ApplicationCatalogDeserializer.class)
public final class ApplicationCatalog  {
    private final long applicationId;
    private final String applicationName;
    ApplicationCatalog() {
        applicationId = -1l;
        applicationName = null;
    }
}
