package jenkins.common

import javaposse.jobdsl.dsl.Job
import javaposse.jobdsl.dsl.JobManagement
import javaposse.jobdsl.dsl.MemoryJobManagement
import javaposse.jobdsl.dsl.jobs.FreeStyleJob
import jdslUnit.JdslUnit
import spock.lang.Specification

@JdslUnit
class GitRepositoryHelperSpec extends Specification{
    GitRepositoryHelper testObj

    def setup() {
        testObj = new GitRepositoryHelper()
    }

    def 'should add a github repository'() {
        given:
        JobManagement jm = new MemoryJobManagement()
        Job testJob = new FreeStyleJob(jm, 'name')

        GitRepository gitRepository = new GitRepository()
        gitRepository.branch = "\${COMMIT_ID}"
        gitRepository.refspec = '+refs/pull/*:refs/remotes/origin/pr/*'
        gitRepository.url = 'org/repo'
        gitRepository.credentials = 'credentials'

        when:
        Job job = testObj.addGitRepository(testJob, gitRepository)

        then:
        job.hasSCM().withRefspec('+refs/pull/*:refs/remotes/origin/pr/*')
        job.hasSCM().withUrl('https://github.com/org/repo.git')
        job.hasSCM().withBranch('${COMMIT_ID}')
        job.hasSCM().withCredentials('credentials')
    }
}