package net.binis.codegen.jackson;

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

import lombok.SneakyThrows;
import net.binis.codegen.annotation.builder.CodeBuilder;
import net.binis.codegen.annotation.builder.CodeRequest;
import net.binis.codegen.exception.ValidationFormException;
import net.binis.codegen.jackson.objects.TestRequest;
import net.binis.codegen.validation.annotation.SanitizeLowerCase;
import net.binis.codegen.validation.annotation.SanitizeTrim;
import net.binis.codegen.validation.annotation.ValidateLength;
import net.binis.codegen.validation.annotation.ValidateNull;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class JacksonTest {

    @SneakyThrows
    @Test
    void test() {
        var mapper = CodeJackson.getMapper();

        var obj = mapper.readValue("{\"name\": \"Binis\", \"value\": \"Belev\"}", CodeJacksonTest.class);

        assertEquals("Binis", obj.getName());
        assertEquals("Belev", obj.getValue());

        var obj2 = mapper.readValue("{\"name\": \"Binis\", \"value\": \"  Belev  \"}", CodeJacksonTest2.class);

        assertEquals("binis", obj2.getName());
        assertEquals("Belev", obj2.getValue());

        assertThrows(ValidationFormException.class, () -> mapper.readValue("{\"name\": \"Binis\", \"value\": \"Belev\"}", CodeJacksonTest3.class));

        var col = mapper.readValue("{\"name\": \"Binis\", \"item\": { \"value\": \"Belev\"}, \"list\": [{ \"value\": \"Belev\"}], \"set\": [{ \"value\": \"Smith\"}], \"map\": {\"key\" : {\"value\": \"Belev\"}}}", CodeJacksonTestCollection.class);

        assertEquals("Binis", col.getName());
        assertEquals("Belev", col.getList().get(0).getValue());

        assertEquals("Smith", col.getSet().iterator().next().getValue());

        assertEquals("Belev", col.getMap().get("key").getValue());

        try {
            mapper.readValue("{\"name\": \"Binis\", \"list\": [{ \"value\": null}], \"set\": [{ \"value\": null}], \"map\": {\"key\" : {\"value\": null}}}", CodeJacksonTestCollection.class);
        } catch (ValidationFormException ex) {
            assertNotNull(ex.getErrors().get("set[0].value"));
            assertNotNull(ex.getErrors().get("list[0].value"));
            assertNotNull(ex.getErrors().get("map[\"key\"].value"));
        }

        try {
            mapper.readValue("{\"list\": [{ \"value\": null}, { \"value\": \"null\"}, { \"value\": null}]}", CodeJacksonTestCollection2.class);
        } catch (ValidationFormException ex) {
            assertNotNull(ex.getErrors().get("list[0].value"));
            assertNotNull(ex.getErrors().get("list[2].value"));
        }

        var regObj = mapper.readValue("{\"list\": [{ \"value\": \"1\"}, { \"value\": \"null\"}, { \"value\": \"2\"}]}", CodeJacksonTestCollection3.class);
        assertEquals(3, regObj.getList().size());
    }

    @Test
    void testSub() {
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
        @ValidateNull
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

    @CodeRequest
    interface CodeJacksonTestCollectionPrototype {
        @ValidateNull
        String name();

        @ValidateNull
        List<Item> list();

        @ValidateNull
        Set<Item> set();

        @ValidateNull
        Map<String, Item> map();

        @ValidateNull
        Item item();

        @CodeRequest
        interface Item {
            @ValidateNull
            String value();
        }
    }

    @CodeRequest
    interface CodeJacksonTestCollection2Prototype {

        List<Item> list();
        @CodeRequest
        interface Item {
            @ValidateNull
            String value();
        }
    }

    @CodeBuilder
    interface CodeJacksonTestCollection3Prototype {
        List<Item> list();
        @CodeRequest
        interface Item {
            @ValidateNull
            String value();
        }
    }

}
