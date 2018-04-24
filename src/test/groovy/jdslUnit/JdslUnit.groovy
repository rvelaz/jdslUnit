package jdslUnit

import org.spockframework.runtime.extension.ExtensionAnnotation

import java.lang.annotation.ElementType
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy
import java.lang.annotation.Target

@Target([ElementType.TYPE])
@Retention(RetentionPolicy.RUNTIME)
@ExtensionAnnotation(JdslUnitExtension)
@interface JdslUnit {}