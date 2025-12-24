package net.binis.codegen.jackson.serialize;

/*-
 * #%L
 * code-generator-jackson
 * %%
 * Copyright (C) 2021 - 2024 Binis Belev
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import net.binis.codegen.objects.base.enumeration.CodeEnum;
import tools.jackson.core.JsonGenerator;
import tools.jackson.databind.SerializationContext;
import tools.jackson.databind.ser.std.StdSerializer;

public class CodeEnumOrdinalSerializer extends StdSerializer<CodeEnum> {

    public CodeEnumOrdinalSerializer() {
        super(CodeEnum.class);
    }

    @Override
    public void serialize(CodeEnum codeEnum, JsonGenerator jsonGenerator, SerializationContext provider) {
        jsonGenerator.writeString(Integer.toString(codeEnum.ordinal()));
    }
}
