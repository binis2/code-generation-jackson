package net.binis.codegen.jackson.prototype;

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

import net.binis.codegen.annotation.CodePrototype;
import net.binis.codegen.annotation.processor.utils.dummy.Dummy;
import net.binis.codegen.annotation.processor.utils.dummy.ElementInsertionTestEnricher;
import net.binis.codegen.annotation.type.EmbeddedModifierType;
import net.binis.codegen.enrich.AsEnricher;
import net.binis.codegen.modifier.BaseModifier;

@CodePrototype(enrichers = { ElementInsertionTestEnricher.class, AsEnricher.class }, classGetters = true, interfaceName = "ElementTest", baseModifierClass = BaseModifier.class)
@Dummy(bool = false, integer = 0, lng = 0L, shrt = (short)0, dbl = 0.0, flt = 0.0F, byt = (byte)0, chr = 'Z', cls = ElementTestPrototype.class, typ = EmbeddedModifierType.BOTH, ints = {1, 2, 3, 4})
public interface ElementTestPrototype {

    long id();

}
