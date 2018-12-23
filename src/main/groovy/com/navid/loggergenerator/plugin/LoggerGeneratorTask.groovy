package com.navid.loggergenerator.plugin

import org.gradle.api.DefaultTask
import org.gradle.api.file.ConfigurableFileCollection
import org.gradle.api.file.FileCollection
import org.gradle.api.tasks.InputFiles
import org.gradle.api.tasks.Internal
import org.gradle.api.tasks.TaskAction

/**
 * A custom task type, allows projects to create tasks of type 'GreetingTask'
 * Reference:
 * https://docs.gradle.org/4.6/userguide/more_about_tasks.html#sec:task_input_output_annotations
 */
class LoggerGeneratorTask extends DefaultTask {

    @Internal
    String generatorVersion = "zzz";
    @Internal
    String inputFile;
    @Internal
    String outputFolder;
    @Internal
    String packageName;
    @Internal
    ConfigurableFileCollection executableJars;

    LoggerGeneratorTask() {
        executableJars = getProject().files();
    }

    @TaskAction
    void greet() {
        System.out.println(getExecutableJars().getFiles());

        //this.getProject().getTasks().withType(JavaExec.class);
        LoggerGeneratorInvoker.invoke(this)
    }


    @InputFiles
    FileCollection getExecutableJars() {
        return executableJars
    }

    void setExecutableJars(FileCollection executableJars) {
        this.executableJars.setFrom(executableJars)
    }



}
