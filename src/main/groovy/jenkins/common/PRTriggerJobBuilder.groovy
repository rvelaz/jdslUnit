package jenkins.common

import javaposse.jobdsl.dsl.DslFactory
import javaposse.jobdsl.dsl.Job

class PRTriggerJobBuilder extends JobsBuilder {
    def description

    PRTriggerJobBuilder(DslFactory dslFactory, description) {
        super(dslFactory)
        this.description = description
    }

    def build(jobName, GitRepository gitRepository) {
        Job job = createJob(dslFactory, jobName)
        job.description(description)
        job.logRotator {
            numToKeep 10
        }

        job.environmentVariables {
            keepBuildVariables(true)
            keepSystemVariables(true)
        }


        gitRepositorylHelper.addGitRepository(job, gitRepository)

        job.triggers {
            githubPullRequest {
                admin('github_user')
                cron('H/5 * * * *')
                useGitHubHooks()
                permitAll()
                autoCloseFailedPullRequests(false)
                extensions {
                    commitStatus {
                        context('Jenkins trigger is being executed')
                        triggeredStatus('Unit tests triggered')
                        startedStatus('Running unit tests')
                        statusUrl('https://jenkins.organization.com')
                        statusUrl("https://jenkins.organization.com}/view/VIEW_NAME/job/${jobName}")
                        completedStatus('SUCCESS', 'All good')
                        completedStatus('FAILURE', 'Something went wrong. Investigate!')
                        completedStatus('ERROR', 'Something went really wrong. Investigate!')
                    }
                }
            }
        }

        job
    }
}