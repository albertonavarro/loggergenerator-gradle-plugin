package com.navid.loggergenerator;

import org.gradle.api.Action;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.artifacts.DependencySet;

public class LoggerGeneratorPlugin implements Plugin<Project> {
    public void apply(Project project) {
        Configuration configuration = project.getConfigurations().create("loggergenerator");

        LoggerGeneratorExtension extension = project.getExtensions().create("loggerGenerator", LoggerGeneratorExtension.class);

        project.getTasks().create("generateLogs", LoggerGeneratorTask.class, (task) -> {
            //LoggerGeneratorExtension extension2 = task.getExtensions().create("loggerGenerator", LoggerGeneratorExtension.class);
            task.setInputFile(extension.getInputFile());
            task.setOutputFolder(extension.getOutputFolder());
            task.setPackageName(extension.getPackageName());
        });

        configureDefaultDependencies(project, configuration);
    }

    private void configureDefaultDependencies(Project project, Configuration configuration) {
        configuration.defaultDependencies(new Action<DependencySet>() {
            @Override
            public void execute(DependencySet dependencies) {
                dependencies.add(project.getDependencies().create("com.navid.loggergenerator:loggergenerator:" + "1.0.1"));
            }
        });

        project.getTasks().withType(LoggerGeneratorTask.class, new Action<LoggerGeneratorTask>() {
            public void execute(LoggerGeneratorTask dataProcessing) {
                dataProcessing.setExecutableJars(configuration);

            }
        });


    }
}