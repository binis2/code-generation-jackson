package net.binis.codegen;

import net.binis.codegen.annotation.CodePrototype;
import net.binis.codegen.annotation.Default;
import net.binis.codegen.annotation.EnumPrototype;
import net.binis.codegen.annotation.type.GenerationStrategy;
import net.binis.codegen.annotation.validation.Sanitize;
import net.binis.codegen.annotation.validation.Validate;
import net.binis.codegen.enrich.OpenApiElementEnricher;
import net.binis.codegen.options.GenerateOpenApiIfAvailableOption;
import net.binis.codegen.validation.annotation.ValidateEmail;
import net.binis.codegen.validation.annotation.ValidateLength;
import net.binis.codegen.validation.annotation.ValidateNull;
import net.binis.codegen.validation.annotation.ValidateRange;
import net.binis.codegen.validation.sanitizer.TrimSanitizer;
import net.binis.codegen.validation.validator.NullValidator;
import net.binis.codegen.jackson.enums.OpenApiEnum;

import java.util.List;
import java.util.Map;
import java.util.Set;

@CodePrototype(strategy = GenerationStrategy.NONE, enrichers = OpenApiElementEnricher.class, options = GenerateOpenApiIfAvailableOption.class)
public interface TestView {
    @Default("\"asd\"")
    @ValidateLength(min = 10, minMessage = "Must be longer than %4$d!", maxMessage = "Must be shorter than %3$d!")
    @Sanitize(TrimSanitizer.class)
    @ValidateEmail
    String getTitle();

    @Default(value = "100")
    @ValidateRange(min = Integer.MIN_VALUE, max = Integer.MAX_VALUE - 5)
    int getNumber();

    @ValidateNull
    List<Long> getList();

    @Validate(value = NullValidator.class, params = {"asd", "fgh"}, message = "test")
    Set<Long> getSet();

    @Validate(value = NullValidator.class, params = {}, message = "another test")
    Map<Long, String> getMap();

    @ValidateNull
    OpenApiEnum getType();

    GenerationStrategy getCompiled();

    @EnumPrototype
    enum OpenApiEnumPrototype {
        ONE,
        TWO,
        THREE
    }

}