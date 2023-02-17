/*Generated code by Binis' code generator.*/
package net.binis.codegen.validate.range;

import static org.junit.jupiter.api.Assertions.assertEquals;
import net.binis.codegen.validation.validator.RangeValidator;
import net.binis.codegen.validation.validator.NullValidator;
import net.binis.codegen.validation.validator.LengthValidator;
import net.binis.codegen.validation.sanitizer.TrimSanitizer;
import net.binis.codegen.validation.sanitizer.OnlyNotNullsLambdaSanitizer;
import net.binis.codegen.validation.sanitizer.LengthSanitizer;
import net.binis.codegen.validation.flow.Validation;
import net.binis.codegen.validation.executor.LambdaExecutor;
import net.binis.codegen.validation.Validatable;
import net.binis.codegen.modifier.impl.BaseModifierImpl;
import net.binis.codegen.modifier.Modifiable;
import net.binis.codegen.factory.CodeFactory;
import net.binis.codegen.collection.CodeListImpl;
import net.binis.codegen.collection.CodeList;
import javax.annotation.processing.Generated;
import java.util.List;

@Generated(value = "TestPrototype", comments = "Test")
@SuppressWarnings("unchecked")
public class TestImpl implements Test, Modifiable<Test.Modify>, Validatable {

    protected String executions;

    protected List<Integer> list;

    protected String sanitization;

    protected int value;

    // region constructor & initializer
    {
        CodeFactory.registerType(Test.class, TestImpl::new, null);
    }

    public TestImpl() {
    }
    // endregion

    // region getters
    public <T> T as(Class<T> cls) {
        return CodeFactory.projection(this, cls);
    }

    public <T> T cast(Class<T> cls) {
        return CodeFactory.cast(this, cls);
    }

    public String getExecutions() {
        return executions;
    }

    public List<Integer> getList() {
        return list;
    }

    public String getSanitization() {
        return sanitization;
    }

    public int getValue() {
        return value;
    }

    public void validate() {
        Validation.form(this.getClass(), e -> Validation.start(e, this.getClass(), "value", value).validate(NullValidator.class, "(%s) Value can't be null").validateWithMessages(RangeValidator.class, new String[] { "min %2$d(%3$d)", "max %2$d(%4$d)" }, 0, 1000).validateWithMessages(LengthValidator.class, new String[] { "Value for field '%s' is shorter than %3$d!", "Value for field '%s' is longer than %4$d!" }, 1, 255).perform(v -> value = v), e -> Validation.start(e, this.getClass(), "sanitization", sanitization).sanitize(OnlyNotNullsLambdaSanitizer.class, ((java.util.function.Function<String, String>) String::toLowerCase)).sanitize(LengthSanitizer.class, 50).sanitize(TrimSanitizer.class).perform(v -> sanitization = v), e -> Validation.start(e, this.getClass(), "list", list).validate(NullValidator.class, "(%s) Value can't be null").validateCollection(NullValidator.class, "(%s) Value can't be null").validateWithMessagesCollection(RangeValidator.class, new String[] { "minC %2$d(%3$d)", "maxC %2$d(%4$d)" }, 0, 1000).validateWithMessagesCollection(LengthValidator.class, new String[] { "Value for field '%s' is shorter than %3$d!", "Value for field '%s' is longer than %4$d!" }, 1, 255).perform(v -> list = v));
    }

    public Test.Modify with() {
        return new TestModifyImpl(this);
    }
    // endregion

    // region inner classes
    @SuppressWarnings("unchecked")
    protected class TestModifyImpl extends BaseModifierImpl<Test.Modify, Test> implements Test.Modify {

        protected TestModifyImpl(Test parent) {
            super(parent);
        }

        public Test done() {
            return TestImpl.this;
        }

        public Test.Modify executions(String executions) {
            Validation.start(this.getClass(), "executions", executions).execute(LambdaExecutor.class, "(%s) Invalid value!", ((java.util.function.Consumer<String>) value -> {
            })).execute(LambdaExecutor.class, "(%s) Invalid value!", ((java.util.function.Consumer<String>) value -> {
            })).execute(LambdaExecutor.class, "(%s) Invalid value!", ((java.util.function.Consumer<String>) value -> assertEquals("test", value))).perform(v -> TestImpl.this.executions = v);
            return this;
        }

        public Test.Modify list(List<Integer> list) {
            Validation.start(this.getClass(), "list", list).validate(NullValidator.class, "(%s) Value can't be null").validateCollection(NullValidator.class, "(%s) Value can't be null").validateWithMessagesCollection(RangeValidator.class, new String[] { "minC %2$d(%3$d)", "maxC %2$d(%4$d)" }, 0, 1000).validateWithMessagesCollection(LengthValidator.class, new String[] { "Value for field '%s' is shorter than %3$d!", "Value for field '%s' is longer than %4$d!" }, 1, 255).perform(v -> TestImpl.this.list = v);
            return this;
        }

        public CodeList list() {
            if (TestImpl.this.list == null) {
                TestImpl.this.list = new java.util.ArrayList<>();
            }
            return new CodeListImpl<>(this, TestImpl.this.list, value -> Validation.start(this.getClass(), "list", value).validate(NullValidator.class, "(%s) Value can't be null").validateWithMessages(RangeValidator.class, new String[] { "minC %2$d(%3$d)", "maxC %2$d(%4$d)" }, 0, 1000).validateWithMessages(LengthValidator.class, new String[] { "Value for field '%s' is shorter than %3$d!", "Value for field '%s' is longer than %4$d!" }, 1, 255));
        }

        public Test.Modify sanitization(String sanitization) {
            Validation.start(this.getClass(), "sanitization", sanitization).sanitize(OnlyNotNullsLambdaSanitizer.class, ((java.util.function.Function<String, String>) String::toLowerCase)).sanitize(LengthSanitizer.class, 50).sanitize(TrimSanitizer.class).perform(v -> TestImpl.this.sanitization = v);
            return this;
        }

        public Test.Modify value(int value) {
            Validation.start(this.getClass(), "value", value).validate(NullValidator.class, "(%s) Value can't be null").validateWithMessages(RangeValidator.class, new String[] { "min %2$d(%3$d)", "max %2$d(%4$d)" }, 0, 1000).validateWithMessages(LengthValidator.class, new String[] { "Value for field '%s' is shorter than %3$d!", "Value for field '%s' is longer than %4$d!" }, 1, 255).perform(v -> TestImpl.this.value = v);
            return this;
        }
    }
    // endregion
}
