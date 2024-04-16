package com.otus;

import static io.cucumber.junit.platform.engine.Constants.GLUE_PROPERTY_NAME;

import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectPackages("com.otus")
@ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "com.otus")
public class Runner_Test {
}
