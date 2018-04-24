import hudson.model.FreeStyleProject
import hudson.model.JDK
import hudson.model.labels.LabelAtom
import hudson.plugins.git.GitSCM
import hudson.plugins.sonar.*
import hudson.plugins.sonar.*
import hudson.plugins.sonar.*
import hudson.plugins.sonar.model.*
import hudson.plugins.sonar.model.*
import hudson.plugins.sonar.model.*
import javaposse.jobdsl.plugin.*
import jenkins.model.*
import com.cloudbees.jenkins.plugins.sshcredentials.impl.*
import com.cloudbees.plugins.credentials.*
import com.cloudbees.plugins.credentials.impl.*
import com.cloudbees.plugins.credentials.domains.*;
import hudson.security.*
import hudson.plugins.sonar.*;
import hudson.plugins.sonar.model.*;
import org.jenkinsci.plugins.postbuildscript.*;
import hudson.model.labels.*;

import jenkins.model.*
import com.cloudbees.plugins.credentials.*
import com.cloudbees.plugins.credentials.common.*
import com.cloudbees.plugins.credentials.domains.*
import com.cloudbees.jenkins.plugins.sshcredentials.impl.*
import hudson.plugins.sshslaves.*;


//Create credential
domain = Domain.global()
credential_id = 'github_credential_id'
store = Jenkins.instance.getExtensionList('com.cloudbees.plugins.credentials.SystemCredentialsProvider')[0].getStore()

private_key = new BasicSSHUserPrivateKey.FileOnMasterPrivateKeySource("/path/to/key", null, null);
credentials = new BasicSSHUserPrivateKey(CredentialsScope.GLOBAL,credential_id, 'github_user', private_key, null, 'Credential to access Github');

matcher = CredentialsMatchers.withId(credential_id)
available_credentials = CredentialsProvider.lookupCredentials(StandardUsernameCredentials.class, Jenkins.getInstance(), hudson.security.ACL.SYSTEM, new SchemeRequirement("ssh"))

existing_credentials = CredentialsMatchers.firstOrNull(available_credentials, matcher)

if(existing_credentials != null) {
    store.updateCredentials(domain, existing_credentials, credentials)
}
else {
    store.addCredentials(domain, credentials)
}

def url = "https://github.com/repository.git"
def jobName = "seed"

Jenkins.instance.projects.each { project ->
    if (project.name == jobName) {
        project.delete()
        project.save()
    }
}

project = Jenkins.instance.createProject(FreeStyleProject, jobName)
def gitScm = new GitSCM(url)
gitScm.branches = [new hudson.plugins.git.BranchSpec("*/master")]
gitScm.userRemoteConfigs = [new hudson.plugins.git.UserRemoteConfig(url, 'origin', 'refs/heads/*:refs/remotes/origin/*', credential_id)]

project.scm = gitScm
masterLabel = new LabelAtom('master')
project.setAssignedLabel(masterLabel)
project.getBuildersList().clear()


ExecuteDslScripts executeDslScripts = new ExecuteDslScripts()
executeDslScripts.removedJobAction = RemovedJobAction.IGNORE
executeDslScripts.removedViewAction = RemovedViewAction.DELETE
executeDslScripts.additionalClasspath = 'src/main/groovy'
executeDslScripts.targets = 'dsl/**/*_jobs.groovy'

project.getBuildersList().add(executeDslScripts)
project.save()

