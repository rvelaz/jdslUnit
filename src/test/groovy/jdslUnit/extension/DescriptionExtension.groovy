package jdslUnit.extension

import javaposse.jobdsl.dsl.Job

class DescriptionExtension implements JdslUnitObjectExtension {

    @Override
    def addTestMethods() {
        Job.metaClass.hasDescription = { String description ->
            return ((String) delegate.node.'description'[0].value()).equals(description)
        }
    }
}
