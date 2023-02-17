package net.binis.codegen.validate.range;

import lombok.extern.slf4j.Slf4j;
import net.binis.codegen.exception.ValidationFormException;
import net.binis.codegen.test.TestExecutor;
import net.binis.codegen.validation.Validatable;
import org.opentest4j.AssertionFailedError;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
public class Execute extends TestExecutor {

    @Override
    public boolean execute() {

        var json = "{ \"value\" : -1}";

        try {
            Test.create()._map(json).done().cast(Validatable.class).validate();
            throw new UnsupportedOperationException("1");
        } catch (ValidationFormException e) {
            assertEquals("ValidationFormException(errors={list=[(list) Value can't be null], value=[min -1(0)]}, cls=class net.binis.codegen.validate.range.TestImpl)", e.toString());
        }

        json = "{ \"value\" : 1001, \"list\": []}";
        try {
            Test.create()._map(json).done().cast(Validatable.class).validate();
            throw new UnsupportedOperationException("2");
        } catch (ValidationFormException e) {
            assertEquals("ValidationFormException(errors={value=[max 1001(1000)]}, cls=class net.binis.codegen.validate.range.TestImpl)", e.toString());
        }

        json = "{ \"value\" : 1001, \"list\": [-1, 190, 1001]}";
        try {
            Test.create()._map(json).done().cast(Validatable.class).validate();
            throw new UnsupportedOperationException("3");
        } catch (ValidationFormException e) {
            assertEquals("ValidationFormException(errors={list[2]=[maxC 1001(1000)], list[0]=[minC -1(0)], value=[max 1001(1000)]}, cls=class net.binis.codegen.validate.range.TestImpl)", e.toString());
        }

        var obj = Test.create().sanitization("     TestTest       ").done();
        assertEquals("testtest", obj.getSanitization());

        try {
            Test.create().executions("Test").done();
            throw new UnsupportedOperationException("4");
        } catch (AssertionFailedError e) {
            assertEquals("org.opentest4j.AssertionFailedError: expected: <test> but was: <Test>", e.toString());
        }

        return true;
    }
}
