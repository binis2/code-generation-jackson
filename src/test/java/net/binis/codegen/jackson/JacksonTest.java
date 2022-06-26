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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import lombok.SneakyThrows;
import net.binis.codegen.annotation.builder.CodeRequest;
import net.binis.codegen.enrich.CreatorModifierEnricher;
import net.binis.codegen.enrich.ModifierEnricher;
import net.binis.codegen.exception.ValidationFormException;
import net.binis.codegen.jackson.objects.TestRequest;
import net.binis.codegen.validation.annotation.*;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class JacksonTest {

    @SneakyThrows
    @Test
    public void test() {
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

    @Test
    public void testSub() {
        TestRequest.create()
                .name("name1")
                .numbers("123")
                .value("value")
                .sub()
                    .value("value")
                .done()
            .done()
            .validate();
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
