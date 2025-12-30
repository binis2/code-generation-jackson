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

import net.binis.codegen.annotation.CodeConfiguration;
import net.binis.codegen.exception.MapperException;
import net.binis.codegen.exception.ValidationFormException;
import net.binis.codegen.factory.CodeFactory;
import net.binis.codegen.jackson.mapping.keys.MappingKeys;
import net.binis.codegen.jackson.serialize.CodeEnumStringSerializer;
import net.binis.codegen.map.Mapper;
import net.binis.codegen.tools.Reflection;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.json.JsonMapper;
import tools.jackson.databind.module.SimpleModule;

import java.util.Map;

import static java.util.Objects.nonNull;

@CodeConfiguration
public class CodeJackson {

    public static void initialize() {
        CodeFactory.registerType(ObjectMapper.class, CodeFactory.lazy(CodeJackson::getMapper));
        CodeFactory.registerType(JsonMapper.class, CodeFactory.lazy(CodeJackson::getMapper));

        Mapper.registerMapper(String.class, Object.class, (source, destination) -> {
            try {
                return CodeFactory.create(ObjectMapper.class).readerForUpdating(destination).readValue(source);
            } catch (ValidationFormException v) {
                throw v;
            } catch (Exception e) {
                throw new MapperException(e);
            }
        });

        Mapper.registerMapper(Map.class, Object.class, (source, destination) -> {
            try {
                return CodeFactory.create(ObjectMapper.class).convertValue(source, destination.getClass());
            } catch (ValidationFormException v) {
                throw v;
            } catch (Exception e) {
                throw new MapperException(e);
            }
        });

        Mapper.map().key(MappingKeys.JSON).source(Object.class).destination(String.class).producer(o -> {
            try {
                return CodeFactory.create(ObjectMapper.class).writeValueAsString(o);
            } catch (Exception e) {
                return "{ \"exception\": \"" + e.getMessage() + "\"}";
            }
        });
        Mapper.registerMapper(String.class, Object.class, MappingKeys.JSON, (source, destination) -> {
            try {
                return CodeFactory.create(ObjectMapper.class).readerForUpdating(destination).readValue(source);
            } catch (ValidationFormException v) {
                throw v;
            } catch (Exception e) {
                throw new MapperException(e);
            }
        });

        var xml = Reflection.loadClass("tools.jackson.dataformat.xml.XmlMapper");
        if (nonNull(xml)) {
            Mapper.map().key(MappingKeys.XML).source(Object.class).destination(String.class).producer(o -> {
                try {
                    return ((ObjectMapper) CodeFactory.create(xml)).writeValueAsString(o);
                } catch (Exception e) {
                    return "<exception>" + e.getMessage() + "</exception>";
                }
            });
            Mapper.registerMapper(String.class, Object.class, MappingKeys.XML, (source, destination) -> {
                try {
                    return ((ObjectMapper) CodeFactory.create(xml)).readerForUpdating(destination).readValue(source);
                } catch (ValidationFormException v) {
                    throw v;
                } catch (Exception e) {
                    throw new MapperException(e);
                }
            });
        }
    }

    public static ObjectMapper getMapper() {
        var builder = JsonMapper.builder();
        builder = builder.typeFactory(new CodeProxyTypeFactory(builder.typeFactory()))
                .deserializerFactory(builder.deserializerFactory().withDeserializerModifier(new CodeBeanDeserializerModifier()));
        var module = new SimpleModule();
        module.addSerializer(new CodeEnumStringSerializer());
        builder.addModule(module);
        return builder.build();
    }

    private CodeJackson() {
        //Do nothing
    }

}
