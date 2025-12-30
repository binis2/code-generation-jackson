package net.binis.codegen.jackson.objects;

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

import net.binis.codegen.annotation.builder.CodeRequest;
import net.binis.codegen.enrich.CreatorModifierEnricher;
import net.binis.codegen.enrich.ModifierEnricher;
import net.binis.codegen.enrich.ValidationEnricher;
import net.binis.codegen.options.ExposeValidateMethodOption;
import net.binis.codegen.options.HiddenCreateMethodOption;
import net.binis.codegen.options.ValidationFormOption;
import net.binis.codegen.validation.annotation.SanitizeTrim;
import net.binis.codegen.validation.annotation.ValidateLength;
import net.binis.codegen.validation.annotation.ValidateNull;
import net.binis.codegen.validation.annotation.ValidateRegEx;

@CodeRequest(enrichers = {CreatorModifierEnricher.class, ModifierEnricher.class, ValidationEnricher.class}, options = {ExposeValidateMethodOption.class, HiddenCreateMethodOption.class, ValidationFormOption.class})
public interface TestRequestPrototype {

    @ValidateNull
    @ValidateLength(min = 5, value = 10, minMessage = "Name must be longer than 5 characters!", maxMessage = "Name must be no longer than 10 characters!")
    String name();

    @ValidateNull
    @SanitizeTrim
    String value();

    @ValidateNull
    @ValidateLength(3)
    @ValidateRegEx(expression = "\\d+", message = "Value must have only numbers!")
    String numbers();

    @ValidateNull
    SubRequestPrototype sub();

    @CodeRequest(enrichers = {ModifierEnricher.class})
    interface SubRequestPrototype {

        @ValidateNull
        @SanitizeTrim
        String value();

    }

}
