package com.explore.support.validation

sealed class ValidationResult

object ValidationSuccess : ValidationResult()

open class ValdationFailed(val reason:String): ValidationResult()

open class ValdationWarning(val reason:String): ValidationResult()

class StringValidationFailed(reason:String): ValdationFailed(reason)

class EmailValidationFailed(reason:String): ValdationFailed(reason)
