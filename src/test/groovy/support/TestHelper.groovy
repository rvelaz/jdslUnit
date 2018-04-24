package support

import javaposse.jobdsl.dsl.JobManagement
import javaposse.jobdsl.dsl.JobParent
import javaposse.jobdsl.dsl.MemoryJobManagement

class TestHelper {
    static JobParent getParentJob() {
        JobParent jp = new JobParent() {
            @Override
            Object run() {
                return null
            }

            @Override
            String readFileFromWorkspace(String filePath) {

                def file = new File(filePath)
                if (!file.exists()) {
                    throw new RuntimeException(file + " Does not exist")
                }

                return filePath
            }
        }
        JobManagement jm = new MemoryJobManagement()
        jp.setJm(jm)
        jp
    }
}
