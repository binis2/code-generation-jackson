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

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.ClassStack;
import com.fasterxml.jackson.databind.type.TypeBindings;
import com.fasterxml.jackson.databind.type.TypeFactory;
import net.binis.codegen.factory.CodeFactory;
import net.binis.codegen.tools.Reflection;

import java.util.*;

import static java.util.Objects.nonNull;
import static net.binis.codegen.tools.Reflection.loadClass;
import static net.binis.codegen.tools.Tools.with;

public class CodeProxyTypeFactory extends TypeFactory {

    protected static Set<Class<?>> collections = initCollections();

    protected static Set<Class<?>> initCollections() {
        var result = new HashSet<Class<?>>();
        result.add(Map.class);
        result.add(List.class);
        result.add(Set.class);
        result.add(Collection.class);
        with(loadClass("java.util.SequencedCollection"), result::add);
        with(loadClass("java.util.ImmutableCollections$SubList"), result::add);
        with(loadClass("java.util.ImmutableCollections$List12"), result::add);
        with(loadClass("java.util.ImmutableCollections$ListN"), result::add);
        with(loadClass("java.util.ImmutableCollections$Set12"), result::add);
        with(loadClass("java.util.ImmutableCollections$SetN"), result::add);
        with(loadClass("java.util.ImmutableCollections$Map1"), result::add);
        with(loadClass("java.util.ImmutableCollections$MapN"), result::add);

        return result;
    }

    public CodeProxyTypeFactory(TypeFactory parent) {
        super(Reflection.getFieldValue(parent, "_typeCache"));
    }

    @Override
    protected JavaType _fromClass(ClassStack context, Class<?> rawType, TypeBindings bindings) {
        var type = collections.contains(rawType) ? null : CodeFactory.lookup(rawType);
        if (nonNull(type)) {
            return super._fromClass(context, type, bindings);
        }
        if (List.class.isAssignableFrom(rawType)) {
            rawType = List.class;
        } else if (Set.class.isAssignableFrom(rawType)) {
            rawType = Set.class;
        } else if (Map.class.isAssignableFrom(rawType)) {
            rawType = Map.class;
        }
        return super._fromClass(context, rawType, bindings);
    }


}
