package jenkins.common

import javaposse.jobdsl.dsl.Job
import javaposse.jobdsl.dsl.helpers.triggers.TriggerContext
import jdslUnit.JdslUnit
import spock.lang.Specification
import support.TestHelper

@JdslUnit
class PRTriggerExecuteGradleTaskJobBuilderSpec extends Specification {
    PRTriggerExecuteGradleTaskJobBuilder testObj
    GitRepository gitRepository
    def jobName

    def setup() {
        //Generated from ghprb plugin
        TriggerContext.metaClass.githubPullRequest = {}

        testObj = new PRTriggerExecuteGradleTaskJobBuilder(TestHelper.parentJob, 'Job triggered from a PR that executes a Gradle task')
        gitRepository = new GitRepository()
        gitRepository.branch = "\${COMMIT_ID}"
        gitRepository.refspec = '+refs/pull/*:refs/remotes/origin/pr/*'
        gitRepository.url = 'org/repo'
        gitRepository.credentials = 'credentials'
        jobName = 'pr-trigger'
        testObj.gradleTask = 'runTests'
    }

    def 'should have a description'() {
        when:
        Job job = testObj.build(jobName, gitRepository)

        then:
        job.hasDescription('Job triggered from a PR that executes a Gradle task')
    }

    def 'should keep last 10 builds information'() {
        when:
        Job job = testObj.build(jobName, gitRepository)

        then:
        job.keepsLogs(10)
    }

    def 'should keep build and system variables'() {
        when:
        Job job = testObj.build(jobName, gitRepository)

        then:
        job.keepsSystemVariables()
        job.keepsBuildVariables()
    }


    def 'should pull from a Github repository'() {
        when:
        Job job = testObj.build(jobName, gitRepository)

        then:
        job.hasSCM().withRefspec('+refs/pull/*:refs/remotes/origin/pr/*')
        job.hasSCM().withUrl('https://github.com/org/repo.git')
        job.hasSCM().withBranch('${COMMIT_ID}')
        job.hasSCM().withCredentials('credentials')
    }

    def 'should run the runTests task'() {
        when:
        Job job = testObj.build(jobName, gitRepository)

        then:
        job.executesGradle().withWrapper()
        job.executesGradle().withTasks('runTests')
    }
}
