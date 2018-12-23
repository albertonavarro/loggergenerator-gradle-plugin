package com.navid.loggergenerator.plugin


import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * The plugin's entry point.
 * Reference:
 * https://docs.gradle.org/4.6/userguide/custom_plugins.html#sec:writing_a_simple_plugin
 */
public class LoggerGeneratorPlugin implements Plugin<Project>{

    /**
     * Applies this plugin to the given Gradle project
     * @param project The Gradle project
     */
    void apply(Project project) {
        // Apply all functionality from the GreetingModule
        LoggerGeneratorModule.load(project)
    }
}
