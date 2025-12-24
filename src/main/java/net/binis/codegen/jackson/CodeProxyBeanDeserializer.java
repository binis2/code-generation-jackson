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

import net.binis.codegen.annotation.Ignore;
import net.binis.codegen.exception.ValidationFormException;
import net.binis.codegen.objects.Pair;
import net.binis.codegen.validation.Validatable;
import tools.jackson.core.JsonParser;
import tools.jackson.databind.BeanProperty;
import tools.jackson.databind.DeserializationConfig;
import tools.jackson.databind.DeserializationContext;
import tools.jackson.databind.ValueDeserializer;
import tools.jackson.databind.deser.SettableBeanProperty;
import tools.jackson.databind.deser.impl.ObjectIdReader;
import tools.jackson.databind.jsontype.TypeDeserializer;
import tools.jackson.databind.type.LogicalType;
import tools.jackson.databind.util.AccessPattern;
import tools.jackson.databind.util.NameTransformer;

import java.util.*;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

public class CodeProxyBeanDeserializer<T> extends ValueDeserializer<T> {

    private final ValueDeserializer<T> parent;

    private static final ThreadLocal<Pair<Integer, Pair<Integer, List<Validatable>>>> stack = ThreadLocal.withInitial(() -> Pair.of(0, Pair.of(Integer.MAX_VALUE, null)));

    public CodeProxyBeanDeserializer(ValueDeserializer<T> parent) {
        super();
        this.parent = parent;
    }

    @Override
    public T deserialize(JsonParser p, DeserializationContext ctxt) {
        var pair = stack.get();
        try {
            var level = pair.getKey() + 1;
            pair.key(level);
            var result = parent.deserialize(p, ctxt);
            var ignore = result.getClass().getAnnotation(Ignore.class);
            if (result instanceof Validatable validatable && pair.getValue().getKey() >= level && (isNull(ignore) || !ignore.forValidation())) {
                var list = pair.getValue().getValue();
                if (isNull(list)) {
                    list = new LinkedList<>();
                    pair.getValue().value(list);
                }
                if (pair.getValue().getKey() > level) {
                    list.clear();
                    pair.getValue().key(level);
                }
                list.add(validatable);
            }
            pair.key(--level);

            if (level == 0) {
                var list = pair.getValue().getValue();
                try {
                    if (nonNull(list) && !list.isEmpty() && pair.getValue().getKey() == 1) {
                        if (list.size() == 1) {
                            list.get(0).validate();
                        } else {
                            Map<String, List<String>> errors = null;
                            for (var i = 0; i < list.size(); i++) {
                                try {
                                    list.get(i).validate();
                                } catch (ValidationFormException ex) {
                                    if (isNull(errors)) {
                                        errors = new LinkedHashMap<>();
                                    }
                                    var prefix = "[" + i + "].";
                                    for (var e : ex.getErrors().entrySet()) {
                                        var eList = e.getValue();
                                        errors.put(prefix + e.getKey(), eList);
                                        eList.replaceAll(s -> prefix + s);
                                    }
                                }
                            }
                            if (nonNull(errors)) {
                                throw new ValidationFormException(null, errors);
                            }
                        }
                    }
                } finally {
                    stack.remove();
                }
            }
            return result;
        } catch (Exception ex) {
            stack.remove();
            throw ex;
        }
    }

    @Override
    public T deserialize(JsonParser p, DeserializationContext ctxt, T intoValue) {
        return parent.deserialize(p, ctxt, intoValue);
    }

    @Override
    public Object deserializeWithType(JsonParser p, DeserializationContext ctxt, TypeDeserializer typeDeserializer) {
        return parent.deserializeWithType(p, ctxt, typeDeserializer);
    }

    @Override
    public Object deserializeWithType(JsonParser p, DeserializationContext ctxt, TypeDeserializer
            typeDeserializer, T intoValue) {
        return parent.deserializeWithType(p, ctxt, typeDeserializer, intoValue);
    }

    @Override
    public ValueDeserializer<T> unwrappingDeserializer(DeserializationContext ctxt, NameTransformer unwrapper) {
        return parent.unwrappingDeserializer(ctxt, unwrapper);
    }

    @Override
    public ValueDeserializer<?> replaceDelegatee(ValueDeserializer<?> delegatee) {
        return parent.replaceDelegatee(delegatee);
    }

    @Override
    public Class<?> handledType() {
        return parent.handledType();
    }

    @Override
    public LogicalType logicalType() {
        return parent.logicalType();
    }

    @Override
    public boolean isCachable() {
        return parent.isCachable();
    }

    @Override
    public ValueDeserializer<?> getDelegatee() {
        return parent.getDelegatee();
    }

    @Override
    public Collection<Object> getKnownPropertyNames() {
        return parent.getKnownPropertyNames();
    }

    @Override
    public Object getNullValue(DeserializationContext ctxt) {
        return parent.getNullValue(ctxt);
    }

    @Override
    public AccessPattern getNullAccessPattern() {
        return parent.getNullAccessPattern();
    }

    @Override
    public Object getAbsentValue(DeserializationContext ctxt) {
        return parent.getAbsentValue(ctxt);
    }

    @Override
    public Object getEmptyValue(DeserializationContext ctxt) {
        return parent.getEmptyValue(ctxt);
    }

    @Override
    public AccessPattern getEmptyAccessPattern() {
        return parent.getEmptyAccessPattern();
    }

    @Override
    public ObjectIdReader getObjectIdReader(DeserializationContext ctxt) {
        return parent.getObjectIdReader(ctxt);
    }

    @Override
    public SettableBeanProperty findBackReference(String refName) {
        return parent.findBackReference(refName);
    }

    @Override
    public Boolean supportsUpdate(DeserializationConfig config) {
        return parent.supportsUpdate(config);
    }

    @Override
    public void resolve(DeserializationContext ctxt) {
        parent.resolve(ctxt);
    }

    @Override
    public ValueDeserializer<?> createContextual(DeserializationContext ctxt, BeanProperty property) {
        parent.createContextual(ctxt, property);
        return this;
    }
}
