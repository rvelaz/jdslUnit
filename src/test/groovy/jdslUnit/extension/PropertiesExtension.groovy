package jdslUnit.extension

import javaposse.jobdsl.dsl.Job

class PropertiesExtension implements JdslUnitObjectExtension {

    @Override
    def addTestMethods() {
        Job.metaClass.keepsSystemVariables = {
            return (boolean) delegate.node.'properties'[0].'EnvInjectJobProperty'[0].'keepJenkinsSystemVariables'[0].value()
        }
        Job.metaClass.keepsBuildVariables = {
            return (boolean) delegate.node.'properties'[0].'EnvInjectJobProperty'[0].'keepBuildVariables'[0].value()
        }
    }
}
