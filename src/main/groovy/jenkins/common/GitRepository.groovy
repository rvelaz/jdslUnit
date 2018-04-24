package jenkins.common

import javaposse.jobdsl.dsl.Job

class GitRepository {
    String url
    String refspec = ''
    String branch = ''
    String credentials

    Job addGitRepository(Job job) {
        job.scm {
            git {
                extensions {
                    cleanBeforeCheckout()
                    wipeOutWorkspace()
                }
                remote {
                    github(this.url)
                    refspec(this.refspec)
                    credentials(this.credentials)
                }
                branch(this.branch)
            }
        }
        job
    }
}
