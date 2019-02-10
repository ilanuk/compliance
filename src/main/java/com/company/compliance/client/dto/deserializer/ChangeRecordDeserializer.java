package com.company.compliance.client.dto.deserializer;

import com.company.compliance.client.dto.ChangeRecord;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;

public class ChangeRecordDeserializer extends JsonDeserializer<ChangeRecord> {
    @Override
    public ChangeRecord deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
            throws IOException, JsonProcessingException {
        ObjectCodec oc = jsonParser.getCodec();
        JsonNode node = oc.readTree(jsonParser);
        return new ChangeRecord(node.get("changeRecord").asLong(),
                node.get("changeRecord").get("validity").asBoolean());
    }
}
