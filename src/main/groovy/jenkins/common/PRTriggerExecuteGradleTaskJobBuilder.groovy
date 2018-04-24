package jenkins.common

import javaposse.jobdsl.dsl.DslFactory

class PRTriggerExecuteGradleTaskJobBuilder extends JobsBuilder {
    def gradleTask
    def description

    PRTriggerExecuteGradleTaskJobBuilder(DslFactory dslFactory, description) {
        super(dslFactory)
        this.description = description
    }

    def build(jobName, GitRepository gitRepository) {
        PRTriggerJobBuilder jobBuilder = new PRTriggerJobBuilder(dslFactory, description)
        def job = jobBuilder.build(jobName, gitRepository)
        job.steps {
            gradle {
                useWrapper(true)
                tasks(gradleTask)
            }
        }
        job
    }
}