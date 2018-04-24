package jdslUnit.extension

import javaposse.jobdsl.dsl.Job
import jdslUnit.node.Gradle

class BuildersExtension implements JdslUnitObjectExtension {

    @Override
    def addTestMethods() {
        Job.metaClass.executesGradle = {
            return new Gradle(delegate.node.'builders'[0].'hudson.plugins.gradle.Gradle'[0])
        }
    }
}
