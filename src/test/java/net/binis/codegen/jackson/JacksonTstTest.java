package net.binis.codegen.jackson;

/*-
 * #%L
 * code-generator-jackson
 * %%
 * Copyright (C) 2021 - 2023 Binis Belev
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

import net.binis.codegen.jackson.prototype.ElementTestPrototype;
import net.binis.codegen.test.BaseCodeGenTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class JacksonTstTest extends BaseCodeGenTest {

    @Test
    void test() {
        var ann = ElementTestPrototype.class.getAnnotations();
        assertEquals(2, ann.length);
        assertEquals("@net.binis.codegen.annotation.CodePrototype(classSetters=true, generateImplementation=true, classGetters=false, implementationPackage=\"\", implementationPath=\"\", generateConstructor=true, baseModifierClass=net.binis.codegen.modifier.BaseModifier.class, generateInterface=true, basePath=\"\", mixInClass=void.class, name=\"\", options={}, enrichers={net.binis.codegen.annotation.processor.utils.dummy.ElementInsertionTestEnricher.class, net.binis.codegen.enrich.AsEnricher.class}, interfaceName=\"\", interfaceSetters=true, strategy=IMPLEMENTATION, inheritedEnrichers={}, interfacePath=\"\", base=false)", ann[0].toString());
        assertEquals("@net.binis.codegen.annotation.processor.utils.dummy.Dummy(shrt=13, bool=true, lng=15L, ints={1, 2, 3, 4, 5}, typ=BOTH, integer=10, cls=java.util.List.class, byt=(byte)0x78, chr='Z', dbl=0.15, flt=1.3f)", ann[1].toString());
    }

}
