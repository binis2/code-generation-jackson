package net.binis.codegen.jackson;

/*-
 * #%L
 * code-generation-test
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

import io.swagger.v3.oas.annotations.media.Schema;
import net.binis.codegen.test.BaseCodeGenElementTest;
import net.binis.codegen.test.BaseCodeGenTest;
import net.binis.codegen.tools.Reflection;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class OpenApiElementTest extends BaseCodeGenElementTest {

    @Test
    void enrichOpenApiElement() {
        var cls = testSingle("enrichOpenApiElement.java", "net.binis.codegen.TestView");

        assertNotNull(cls);
        var method = Reflection.findMethod("getType", cls);
        assertNotNull(method);
        var ann = method.getAnnotation(Schema.class);
        assertNotNull(ann);
        assertEquals("type", ann.name());

        method = Reflection.findMethod("getTitle", cls);
        assertNotNull(method);
        ann = method.getAnnotation(Schema.class);
        assertNotNull(ann);
        assertEquals("asd", ann.defaultValue());
        assertEquals(10, ann.minLength());

        method = Reflection.findMethod("getNumber", cls);
        assertNotNull(method);
        ann = method.getAnnotation(Schema.class);
        assertNotNull(ann);
        assertEquals("100", ann.defaultValue());
        assertEquals("Integer.MIN_VALUE", ann.minimum());
        assertEquals("Integer.MAX_VALUE - 5", ann.maximum());

        method = Reflection.findMethod("getCompiled", cls);
        assertNotNull(method);
        ann = method.getAnnotation(Schema.class);
        assertNotNull(ann);
        assertEquals("string", ann.type());
        assertEquals(4, ann.allowableValues().length);

    }

}
