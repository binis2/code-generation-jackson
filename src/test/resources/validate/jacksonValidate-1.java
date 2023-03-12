/*Generated code by Binis' code generator.*/
package net.binis.codegen.validate.range;

import net.binis.codegen.objects.Projectable;
import net.binis.codegen.modifier.BaseModifier;
import net.binis.codegen.creator.EntityCreatorModifier;
import net.binis.codegen.collection.CodeList;
import net.binis.codegen.annotation.Default;
import javax.annotation.processing.Generated;
import java.util.List;

@Generated(value = "net.binis.codegen.validate.range.TestPrototype", comments = "TestImpl")
@Default("net.binis.codegen.validate.range.TestImpl")
@SuppressWarnings("unchecked")
public interface Test extends Projectable {

    // region starters
    static Test.Modify create() {
        return (Test.Modify) EntityCreatorModifier.create(Test.class).with();
    }
    // endregion

    String getExecutions();
    List<Integer> getList();
    String getSanitization();
    int getValue();

    Test.Modify with();

    // region inner classes
    interface Fields<T> {
        T executions(String executions);
        T sanitization(String sanitization);
        T value(int value);
    }

    interface Modify extends Test.Fields<Test.Modify>, BaseModifier<Test.Modify, Test> {
        Modify list(List<Integer> list);
        CodeList<Integer, Test.Modify> list();
    }
    // endregion
}
