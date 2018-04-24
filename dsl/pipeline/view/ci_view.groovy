package pipeline.view

import javaposse.jobdsl.dsl.DslFactory
import jenkins.common.ViewHelper

ViewHelper.addView(this as DslFactory, 'CI', 'CI Jobs', '.*ci.*')
