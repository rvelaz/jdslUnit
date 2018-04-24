package jdslUnit.node


class SCM {
    Object scm

    SCM(Object scm) {
        this.scm = scm
    }

    boolean withRefspec(String refspec) {
        this.scm.find {
            if (it.'hudson.plugins.git.UserRemoteConfig'[0] != null && ((String) it.'hudson.plugins.git.UserRemoteConfig'[0].'refspec'[0]) == null) return true
            if (it.'hudson.plugins.git.UserRemoteConfig'[0] != null && ((String) it.'hudson.plugins.git.UserRemoteConfig'[0].'refspec'[0].value()).equals(refspec)) return true
            return false
        }
    }

    boolean withUrl(String url) {
        this.scm.find {
            return (it.'hudson.plugins.git.UserRemoteConfig'[0] != null && ((String) it.'hudson.plugins.git.UserRemoteConfig'[0].'url'[0].value()).equals(url))
        }
    }

    boolean withCredentials(String credentials) {
        this.scm.find {
            return (it.'hudson.plugins.git.UserRemoteConfig'[0] != null && ((String) it.'hudson.plugins.git.UserRemoteConfig'[0].'credentialsId'[0].value()).equals(credentials))
        }
    }

    boolean withBranch(String branch) {
        return (this.scm.'branches'[0] != null
                && ((String) this.scm.'branches'[0].'hudson.plugins.git.BranchSpec'[0].'name'[0].value()).equals(branch))
    }
}
