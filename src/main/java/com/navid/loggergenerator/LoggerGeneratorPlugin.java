package com.navid.loggergenerator;

import org.gradle.api.Action;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.artifacts.DependencySet;

public class LoggerGeneratorPlugin implements Plugin<Project> {
    public void apply(Project project) {
        Configuration configuration = project.getConfigurations().create("loggergenerator");

        project.getTasks().create("hello", LoggerGeneratorTask.class, (task) -> {
            task.setMessage("Hello");
            task.setRecipient("World");
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
                dataProcessing.setDataFiles(configuration);
            }
        });
    }
}