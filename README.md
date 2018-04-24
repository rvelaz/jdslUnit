This is an example of how Jenkins JobDSL can be used in Groovy classes instead of in Groovy scripts. 
There are mainly two advantages to this:

* Re-usability: Jenkins jobs builders are defined as classes and as such can be parametrised and reused.
* Unit tests: Unit tests can be written to test the jobs are configured in the way they should.

#Setup

The project is composed of three main parts:

* seed job definition: that's what's going to generate a job that will pull the job builders and build the required jobs. This has to be part of Jenkins provisioning.
* builder scripts: simple scripts that build views and jobs
* job builders: Groovy classes that define how to build jobs

##Seed job

It can be part of the provisioning of Jenkins, it does two things:
* defines a credential with permissions to connect to Github 
* Creates a job that pulls from Github and executes the builder scripts

This is static and usually does not need to change.
It can be found in: seed-jobs/seed.groovy


##Builder scripts
They are defined in dsl/pipeline and they simple call a method in the job builders class. To add more
jobs just simply add them there. Here's an example:

```
CISeedJobUnitTestJobBuilder ciSeedJobUnitTestJobBuilder = new CISeedJobUnitTestJobBuilder(this)
ciSeedJobUnitTestJobBuilder.build()
```

##Job builders
They are Groovy classes with a build method that uses JobDSL to define jobs.


##Unit testing

What JobDSL does is it allows to modify the XML object that represents a job. Let's see an example:

```
job.description('some description')
```

Using Spock testing framework, this can be tested with the following code:

```
def 'ssould have a description'() {
    when:
    Job job = testObj.build(jobName, gitRepository)

    then:
    with(job) {
        ((String) it.node.'description'[0].value()).equals('some description')
    }
}
```

This is a simple example, but it get trickier with more complex objects:

```
(it.node.'properties'[0].'hudson.model.ParametersDefinitionProperty'[0].'parameterDefinitions'.'hudson.model.StringParameterDefinition')

```

This is impossible to remember and leads to copy pasting and lots of mistakes. 

###JdslUnit
In order to simplify writing unit tests using the Spock framework for for job builders I've defined a Spock extension 
(jdslUnit.JdslUnitExtension). The extension checks the classes that implement the *JdslUnitObjectExtension* interface
and dynamically adds new methods to the *javaposse.jobdsl.dsl.Job* class that can be used in testing. Following the
description example above a test would look like:

```
 def 'should have a description'() {
    when:
    Job job = testObj.build(jobName, gitRepository)

    then:
    job.hasDescription('Job triggered from a PR')
}
```  


In this example the *DescriptionExtension* just adds a *hasDescription* method to the Job class:
```
class DescriptionExtension implements JdslUnitObjectExtension {

    @Override
    def addTestMethods() {
        Job.metaClass.hasDescription = { String description ->
            return ((String) delegate.node.'description'[0].value()).equals(description)
        }
    }
}
```

**Unit tests and auto-generated code**

Due to the way JobDSL works, not all the configurations that can be set with JobDSL can be tested. This is because
if a plugin has commited a JobDSL version, the JobDSL plugin will auto-generate the code. When this happens it is 
not possible to test the plugins and the functionality needs to be excluded from the tests. To do it, Groovy metaClass
method can be used to override the default functionality. 

```
SomeJenkinsContext.metaClass.methodToOverride = {}

```

See PRTriggerJobBuilderSpec for an example.

 