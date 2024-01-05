/*Generated code by Binis' code generator.*/
package net.binis.codegen.jackson.enums;

/*-
 * #%L
 * code-generator-validation
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

import net.binis.codegen.objects.base.enumeration.CodeEnum;
import net.binis.codegen.factory.CodeFactory;
import net.binis.codegen.annotation.Default;
import javax.annotation.processing.Generated;

@Generated(value = "net.binis.codegen.enums.OpenApiEnumPrototype", comments = "OpenApiEnumImpl")
@Default("net.binis.codegen.enums.OpenApiEnumImpl")
public interface OpenApiEnum extends CodeEnum {

    static final OpenApiEnum ONE = CodeFactory.initializeEnumValue(OpenApiEnum.class, "ONE", 0);

    static final OpenApiEnum TWO = CodeFactory.initializeEnumValue(OpenApiEnum.class, "TWO", 1);

    static OpenApiEnum valueOf(String name) {
        return CodeFactory.enumValueOf(OpenApiEnum.class, name);
    }

    static OpenApiEnum valueOf(int ordinal) {
        return CodeFactory.enumValueOf(OpenApiEnum.class, ordinal);
    }

    static OpenApiEnum[] values() {
        return CodeFactory.enumValues(OpenApiEnum.class);
    }
}
