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
import net.binis.codegen.annotation.CodeConfiguration;
import net.binis.codegen.exception.MapperException;
import net.binis.codegen.exception.ValidationFormException;
import net.binis.codegen.factory.CodeFactory;
import net.binis.codegen.jackson.serialize.CodeEnumStringSerializer;
import net.binis.codegen.map.Mapper;

@CodeConfiguration
public class CodeJackson {

    public static void initialize() {
        CodeFactory.registerType(ObjectMapper.class, CodeFactory.lazy(CodeJackson::getMapper));
        Mapper.registerMapper(String.class, Object.class, (source, destination) -> {
            try {
                return CodeFactory.create(ObjectMapper.class).readerForUpdating(destination).readValue(source);
            } catch (ValidationFormException v) {
                throw v;
            } catch (Exception e) {
                throw new MapperException(e);
            }
        });
    }

    public static ObjectMapper getMapper() {
        var mapper = new ObjectMapper();
        mapper.setTypeFactory(new CodeProxyTypeFactory(mapper.getTypeFactory()));
        SimpleModule module = new SimpleModule();
        module.setDeserializerModifier(new CodeBeanDeserializerModifier());
        module.addSerializer(new CodeEnumStringSerializer());
        mapper.registerModule(module);
        return mapper;
    }

    private CodeJackson() {
        //Do nothing
    }

}
