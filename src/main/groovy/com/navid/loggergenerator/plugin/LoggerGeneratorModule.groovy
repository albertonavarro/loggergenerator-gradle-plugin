package com.navid.loggergenerator.plugin

import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.artifacts.DependencySet
import org.gradle.api.artifacts.repositories.ArtifactRepository

/**
 * The "module" isn't anything Gradle specific.
 * It's just a way of grouping tasks and configuration that share a certain theme.
 * The module's "load" method is called in the plugin's entry point at {@link com.navid.loggergenerator.plugin.LoggerGeneratorPlugin}
 */
class LoggerGeneratorModule {
    static void load(Project project) {

        /*
        * Register a 'greeting' extension, with the properties defined in GreetingExtension
        * Reference:
        * https://docs.gradle.org/4.6/userguide/custom_plugins.html#sec:getting_input_from_the_build
        */
        LoggerGeneratorExtension extension = project.extensions.create("LoggerGeneratorConfig", LoggerGeneratorExtension)

        /*
        * Clever trick so users don't have to reference a custom task class by its fully qualified name.
        * Reference:
        * https://discuss.gradle.org/t/how-to-create-custom-gradle-task-type-and-not-have-to-specify-full-path-to-type-in-build-gradle/6059/4
        */
        project.ext.LoggerGeneratorTask = LoggerGeneratorTask

        project.tasks.create("loggerGeneratorTask", LoggerGeneratorTask)

        project.repositories{
            maven {
                url "http://dl.bintray.com/albertonavarro/maven"
            }
        }

        project.configurations.create("loggerGeneratorConfiguration").defaultDependencies(new Action<DependencySet>() {
            void execute(DependencySet dependencies) {
                dependencies.add(project.getDependencies().create("com.navid.loggergenerator:loggergenerator:" + extension.getExecutableVersion()))
            }
        })

        project.getTasks().withType(LoggerGeneratorTask.class, new Action<LoggerGeneratorTask>() {
            void execute(LoggerGeneratorTask loggerGeneratorTask) {
                loggerGeneratorTask.setExecutableJars(project.configurations.getByName("loggerGeneratorConfiguration"))
            }
        })
    }
}
