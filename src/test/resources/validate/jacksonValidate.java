package net.binis.codegen.validate.range;

import net.binis.codegen.annotation.builder.CodeValidationBuilder;
import net.binis.codegen.enrich.AsEnricher;
import net.binis.codegen.jackson.annotations.ExecuteLambdaTest;
import net.binis.codegen.options.ValidationFormOption;
import net.binis.codegen.validation.annotation.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

@CodeValidationBuilder(enrichers = AsEnricher.class, options = ValidationFormOption.class)
public interface TestPrototype {

    @ValidateNull
    @ValidateRange(min=0, max = 1000, minMessage = "min %2$d(%3$d)", maxMessage = "max %2$d(%4$d)")
    @ValidateLength(min = 1)
    int value();

    @SanitizeLowerCase
    @SanitizeLength(50)
    @SanitizeTrim
    String sanitization();

    @ExecuteLambda("value -> {}")
    @ExecuteLambdaTest("value -> {}")
    @ExecuteLambdaTest("value -> assertEquals(\"test\", value)")
    String executions();

    @ValidateNull
    List<@ValidateNull @ValidateRange(min=0, max = 1000, minMessage = "minC %2$d(%3$d)", maxMessage = "maxC %2$d(%4$d)") @ValidateLength(min = 1) Integer> list();


}
