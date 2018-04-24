package jdslUnit.extension

import javaposse.jobdsl.dsl.JobParent
import jdslUnit.node.JenkinsView

class ViewExtension implements JdslUnitObjectExtension {

    @Override
    def addTestMethods() {
        JobParent.metaClass.hasListView = {
            return new JenkinsView(delegate.'referencedViews'[0])
        }
    }
}
