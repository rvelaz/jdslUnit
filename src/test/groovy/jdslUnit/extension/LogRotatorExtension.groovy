package jdslUnit.extension

import javaposse.jobdsl.dsl.Job

class LogRotatorExtension implements JdslUnitObjectExtension {

    @Override
    def addTestMethods() {

        Job.metaClass.keepsLogs = { numLogs ->
            return delegate.node.'logRotator'[0].'numToKeep'[0].value() == numLogs
        }
    }
}