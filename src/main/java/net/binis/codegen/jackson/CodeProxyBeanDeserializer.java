package net.binis.codegen.jackson;

/*-
 * #%L
 * code-generator-jackson
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

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;
import com.fasterxml.jackson.databind.deser.ResolvableDeserializer;
import com.fasterxml.jackson.databind.deser.SettableBeanProperty;
import com.fasterxml.jackson.databind.deser.impl.ObjectIdReader;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import com.fasterxml.jackson.databind.type.LogicalType;
import com.fasterxml.jackson.databind.util.AccessPattern;
import com.fasterxml.jackson.databind.util.NameTransformer;
import net.binis.codegen.validation.Validatable;

import java.io.IOException;
import java.util.Collection;

public class CodeProxyBeanDeserializer<T> extends JsonDeserializer<T> implements ResolvableDeserializer, ContextualDeserializer {

    private final JsonDeserializer<T> parent;

    public CodeProxyBeanDeserializer(JsonDeserializer<T> parent) {
        super();
        this.parent = parent;
    }

    @Override
    public T deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        var result = parent.deserialize(p, ctxt);
        if (result instanceof Validatable) {
            ((Validatable) result).validate();
        }
        return result;
    }

    @Override
    public T deserialize(JsonParser p, DeserializationContext ctxt, T intoValue) throws IOException {
        return parent.deserialize(p, ctxt, intoValue);
    }

    @Override
    public Object deserializeWithType(JsonParser p, DeserializationContext ctxt, TypeDeserializer typeDeserializer) throws IOException {
        return parent.deserializeWithType(p, ctxt, typeDeserializer);
    }

    @Override
    public Object deserializeWithType(JsonParser p, DeserializationContext ctxt, TypeDeserializer typeDeserializer, T intoValue) throws IOException {
        return parent.deserializeWithType(p, ctxt, typeDeserializer, intoValue);
    }

    @Override
    public JsonDeserializer<T> unwrappingDeserializer(NameTransformer unwrapper) {
        return parent.unwrappingDeserializer(unwrapper);
    }

    @Override
    public JsonDeserializer<?> replaceDelegatee(JsonDeserializer<?> delegatee) {
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
    public JsonDeserializer<?> getDelegatee() {
        return parent.getDelegatee();
    }

    @Override
    public Collection<Object> getKnownPropertyNames() {
        return parent.getKnownPropertyNames();
    }

    @Override
    public T getNullValue(DeserializationContext ctxt) throws JsonMappingException {
        return parent.getNullValue(ctxt);
    }

    @Override
    public AccessPattern getNullAccessPattern() {
        return parent.getNullAccessPattern();
    }

    @Override
    public Object getAbsentValue(DeserializationContext ctxt) throws JsonMappingException {
        return parent.getAbsentValue(ctxt);
    }

    @Override
    public Object getEmptyValue(DeserializationContext ctxt) throws JsonMappingException {
        return parent.getEmptyValue(ctxt);
    }

    @Override
    public AccessPattern getEmptyAccessPattern() {
        return parent.getEmptyAccessPattern();
    }

    @Override
    public ObjectIdReader getObjectIdReader() {
        return parent.getObjectIdReader();
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
    public void resolve(DeserializationContext ctxt) throws JsonMappingException {
        ((ResolvableDeserializer) parent).resolve(ctxt);
    }

    @Override
    public JsonDeserializer<?> createContextual(DeserializationContext ctxt, BeanProperty property) throws JsonMappingException {
        ((ContextualDeserializer) parent).createContextual(ctxt, property);
        return this;
    }
}
