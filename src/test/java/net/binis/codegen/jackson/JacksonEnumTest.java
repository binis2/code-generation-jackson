package net.binis.codegen.jackson;

/*-
 * #%L
 * code-generator-jackson
 * %%
 * Copyright (C) 2021 - 2026 Binis Belev
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

import net.binis.codegen.annotation.EnumPrototype;
import net.binis.codegen.annotation.builder.CodeBuilder;
import net.binis.codegen.factory.CodeFactory;
import net.binis.codegen.test.BaseCodeGenTest;
import org.junit.jupiter.api.Test;
import tools.jackson.databind.ObjectMapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

class JacksonEnumTest extends BaseCodeGenTest {

    @Test
    void testEnum() {
        var obj = TestObject.create().type(TestEnum.TEST).done();
        var s = CodeFactory.create(ObjectMapper.class).writeValueAsString(obj);
        assertEquals("{\"type\":\"TEST\"}", s);
        var obj2 = TestObject.create()._map(s).done();
        assertEquals(obj.getType(), obj2.getType());
    }

    @EnumPrototype
    public enum TestEnumPrototype {
        TEST
    }

    @CodeBuilder
    public interface TestObjectPrototype {
        TestEnumPrototype type();
    }

}
