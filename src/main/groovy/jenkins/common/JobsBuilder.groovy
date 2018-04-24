package jenkins.common

import javaposse.jobdsl.dsl.DslFactory
import javaposse.jobdsl.dsl.Job

class JobsBuilder {
    DslFactory dslFactory
    GitRepositoryHelper gitRepositorylHelper

    JobsBuilder(DslFactory dslFactory) {
        this.dslFactory = dslFactory
        this.gitRepositorylHelper = new GitRepositoryHelper()
    }

    Job createJob(DslFactory dslFactory, String name) {
        Job job = dslFactory.job(name)
        job
    }
}
