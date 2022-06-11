package net.binis.codegen.jackson;

/*-
 * #%L
 * code-generator-jackson
 * %%
 * Copyright (C) 2021 - 2022 Binis Belev
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

import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.BeanDeserializer;
import com.fasterxml.jackson.databind.deser.BeanDeserializerModifier;
import com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition;
import com.fasterxml.jackson.databind.module.SimpleModule;
import lombok.SneakyThrows;
import net.binis.codegen.annotation.builder.CodeRequest;
import net.binis.codegen.exception.ValidationFormException;
import net.binis.codegen.validation.Validatable;
import net.binis.codegen.validation.annotation.SanitizeLowerCase;
import net.binis.codegen.validation.annotation.SanitizeTrim;
import net.binis.codegen.validation.annotation.ValidateLength;
import net.binis.codegen.validation.annotation.ValidateNull;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class JacksonTest {

    @SneakyThrows
    @Test
    void test() {
        var mapper = new ObjectMapper();
        mapper.setTypeFactory(new CodeProxyTypeFactory(mapper.getTypeFactory()));
        SimpleModule module = new SimpleModule();
        module.setDeserializerModifier(new CodeBeanDeserializerModifier());
        mapper.registerModule(module);

        var obj = mapper.readValue("{\"name\": \"Binis\", \"value\": \"Belev\"}", CodeJacksonTest.class);

        assertEquals("Binis", obj.getName());
        assertEquals("Belev", obj.getValue());

        var obj2 = mapper.readValue("{\"name\": \"Binis\", \"value\": \"  Belev  \"}", CodeJacksonTest2.class);

        assertEquals("binis", obj2.getName());
        assertEquals("Belev", obj2.getValue());

        assertThrows(ValidationFormException.class, () -> mapper.readValue("{\"name\": \"Binis\", \"value\": \"Belev\"}", CodeJacksonTest3.class));
    }

    @CodeRequest
    interface CodeJacksonTestPrototype {
        String name();
        String value();
    }

    @CodeRequest
    interface CodeJacksonTest2Prototype {
        @ValidateNull
        @SanitizeLowerCase
        @ValidateLength(min=5)
        String name();

        @SanitizeTrim
        String value();
    }

    @CodeRequest
    interface CodeJacksonTest3Prototype {
        @ValidateNull
        @SanitizeLowerCase
        @ValidateLength(min=10)
        String name();

        @SanitizeTrim
        String value();
    }

}
