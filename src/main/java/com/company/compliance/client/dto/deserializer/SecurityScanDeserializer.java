package com.company.compliance.client.dto.deserializer;

import com.company.compliance.client.dto.SecurityScan;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;

public class SecurityScanDeserializer extends JsonDeserializer<SecurityScan> {
    @Override
    public SecurityScan deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
            throws IOException, JsonProcessingException {
        ObjectCodec oc = jsonParser.getCodec();
        JsonNode node = oc.readTree(jsonParser);
        return new SecurityScan(node.get("applicationId").asLong(),
                node.get("securityscan").get("securityStatus").asBoolean());
    }
}
