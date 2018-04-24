package jenkins.common

import jdslUnit.JdslUnit
import spock.lang.Specification
import support.TestHelper

@JdslUnit
class ViewHelperSpec extends Specification {

    def 'should add a view'() {
        given:
        def dslFactory = TestHelper.parentJob

        when:
        ViewHelper.addView(dslFactory, 'view-name', 'description', '*.regex')

        then:
        dslFactory.hasListView().withName('view-name')
    }
}
