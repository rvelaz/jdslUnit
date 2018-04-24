package jenkins.common

import javaposse.jobdsl.dsl.Job

class GitRepositoryHelper {

    Job addGitRepository(Job job, GitRepository gitRepository) {
        job.scm {
            git {
                extensions {
                    cleanBeforeCheckout()
                    wipeOutWorkspace()
                }
                remote {
                    github(gitRepository.url)
                    refspec(gitRepository.refspec)
                    credentials(gitRepository.credentials)
                }
                branch(gitRepository.branch)
            }
        }
        job
    }
}
