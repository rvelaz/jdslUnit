package jdslUnit.extension

import javaposse.jobdsl.dsl.Job
import jdslUnit.node.SCM

class SCMExtension implements JdslUnitObjectExtension {

    @Override
    def addTestMethods() {
        Job.metaClass.hasSCM = {
            return new SCM(delegate.node.'scm'[0])
        }
    }
}