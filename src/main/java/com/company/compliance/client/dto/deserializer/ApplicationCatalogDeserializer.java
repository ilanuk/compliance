package com.company.compliance.client.dto.deserializer;

import com.company.compliance.client.dto.ApplicationCatalog;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;

public class ApplicationCatalogDeserializer extends JsonDeserializer<ApplicationCatalog> {
    @Override
    public ApplicationCatalog deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
            throws IOException, JsonProcessingException {
        ObjectCodec oc = jsonParser.getCodec();
        JsonNode node = oc.readTree(jsonParser);
        return new ApplicationCatalog(node.get("applicationId").asLong(),
                node.get("applicationCatalog").get("name").asText());
    }
}
