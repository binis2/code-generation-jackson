package net.binis.codegen.jackson.serialize;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import net.binis.codegen.objects.base.enumeration.CodeEnum;

import java.io.IOException;

public class CodeEnumStringSerializer extends StdSerializer<CodeEnum> {

    public CodeEnumStringSerializer() {
        super(CodeEnum.class);
    }

    @Override
    public void serialize(CodeEnum codeEnum, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeString(codeEnum.name());
    }
}
