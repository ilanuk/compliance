package com.company.compliance.client.dto;

import com.company.compliance.client.dto.deserializer.ChangeRecordDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
@JsonDeserialize(using = ChangeRecordDeserializer.class)
public class ChangeRecord {
    private final long chanegRecordId;
    private final boolean changeRecordValid;
    ChangeRecord() {
        chanegRecordId = -1l;
        changeRecordValid = false;
    }
}
