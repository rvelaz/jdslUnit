package jdslUnit

import jdslUnit.extension.JdslUnitObjectExtension
import org.reflections.Reflections
import org.spockframework.runtime.extension.AbstractAnnotationDrivenExtension
import org.spockframework.runtime.model.SpecInfo

class JdslUnitExtension extends AbstractAnnotationDrivenExtension<JdslUnit> {
    void visitSpecAnnotation(JdslUnit annotation, SpecInfo info) {

        //The following is so this class does not need to be modified. To add new functionality
        //it's just enough to add a class that implements JdslUnitObjectExtension to the package
        Reflections reflections = new Reflections('jdslUnit.extension')
        Set<Class<? extends JdslUnitObjectExtension>> allClasses = reflections.getSubTypesOf(JdslUnitObjectExtension.class)
        allClasses.each { clazz ->
            clazz.newInstance().addTestMethods()
        }
    }
}
