package jdslUnit.node

class JenkinsView {
    def view

    JenkinsView(view) {
        this.view = view
    }

    boolean withName(name) {
        isListView() && ((String) this.view.'name').equals(name)
    }

    boolean isListView() {
        return ((Class) this.view.class).toString().equals('class javaposse.jobdsl.dsl.views.ListView')
    }
}
