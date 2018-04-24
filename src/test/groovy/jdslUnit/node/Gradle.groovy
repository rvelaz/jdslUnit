package jdslUnit.node

class Gradle {
    def gradle

    Gradle(gradle) {
        this.gradle = gradle
    }

    boolean withSwitches(switches) {
        return ((String) this.gradle.switches[0].value()).equals(switches)
    }

    boolean hasSwitch(taskSwitch) {
        return ((String) this.gradle.switches[0].value()).contains(taskSwitch)
    }

    boolean withWrapper() {
        return (boolean) (this.gradle.'useWrapper'[0].value())
    }

    boolean fromRootBuildScriptDir(dir) {
        return ((boolean) (this.gradle.'fromRootBuildScriptDir'[0].value())
                && ((String) this.gradle.'rootBuildScriptDir'[0].value()).equals(dir))
    }

    boolean withTasks(tasks) {
        return ((String) this.gradle.'tasks'[0].value()).equals(tasks)
    }
}
