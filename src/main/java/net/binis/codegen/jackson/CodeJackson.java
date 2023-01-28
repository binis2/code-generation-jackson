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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

public class CodeJackson {

    public static ObjectMapper getMapper() {
        var mapper = new ObjectMapper();
        mapper.setTypeFactory(new CodeProxyTypeFactory(mapper.getTypeFactory()));
        SimpleModule module = new SimpleModule();
        module.setDeserializerModifier(new CodeBeanDeserializerModifier());
        mapper.registerModule(module);
        return mapper;
    }

}