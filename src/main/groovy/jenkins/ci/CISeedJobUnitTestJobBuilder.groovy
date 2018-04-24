package jenkins.ci

import javaposse.jobdsl.dsl.DslFactory
import jenkins.common.GitRepository
import jenkins.common.JobsBuilder
import jenkins.common.PRTriggerExecuteGradleTaskJobBuilder

class CISeedJobUnitTestJobBuilder extends JobsBuilder{
    CISeedJobUnitTestJobBuilder(DslFactory dslFactory) {
        super(dslFactory)
    }

    def build(){
        PRTriggerExecuteGradleTaskJobBuilder jobBuilder = new PRTriggerExecuteGradleTaskJobBuilder(dslFactory, 'Job that runs unit tests for the seed job builders')
        GitRepository gitRepository = new GitRepository()
        gitRepository.branch = "\${COMMIT_ID}"
        gitRepository.refspec = '+refs/pull/*:refs/remotes/origin/pr/*'
        gitRepository.url = 'org/repo'
        gitRepository.credentials = 'credentials'
        jobBuilder.gradleTask = 'check'
        def job = jobBuilder.build('ci_seed_unit_tests', gitRepository)
        job
    }
}
