package jenkins.ci

import javaposse.jobdsl.dsl.Job
import javaposse.jobdsl.dsl.helpers.triggers.TriggerContext
import jenkins.common.GitRepository
import jdslUnit.JdslUnit
import spock.lang.Specification
import support.TestHelper

@JdslUnit
class CISeedJobUnitTestJdsBuilderSpec extends Specification {
    CISeedJobUnitTestJobBuilder testObj
    GitRepository gitRepository

    def setup() {
        //Generated from ghprb plugin
        TriggerContext.metaClass.githubPullRequest = {}

        testObj = new CISeedJobUnitTestJobBuilder(TestHelper.parentJob)
        gitRepository = new GitRepository()
        gitRepository.branch = "\${COMMIT_ID}"
        gitRepository.refspec = '+refs/pull/*:refs/remotes/origin/pr/*'
        gitRepository.url = 'org/repo'
        gitRepository.credentials = 'credentials'
    }

    def 'should have a description'() {
        when:
        Job job = testObj.build()

        then:
        job.hasDescription('Job that runs unit tests for the seed job builders')
    }

    def 'should keep last 10 builds information'() {
        when:
        Job job = testObj.build()

        then:
        job.keepsLogs(10)
    }

    def 'should keep build and system variables'() {
        when:
        Job job = testObj.build()

        then:
        job.keepsSystemVariables()
        job.keepsBuildVariables()
    }


    def 'should pull from a Github repository'() {
        when:
        Job job = testObj.build()

        then:
        job.hasSCM().withRefspec('+refs/pull/*:refs/remotes/origin/pr/*')
        job.hasSCM().withUrl('https://github.com/org/repo.git')
        job.hasSCM().withBranch('${COMMIT_ID}')
        job.hasSCM().withCredentials('credentials')
    }

    def 'should run the runTests task'() {
        when:
        Job job = testObj.build()

        then:
        job.executesGradle().withWrapper()
        job.executesGradle().withTasks('check')
    }
}
