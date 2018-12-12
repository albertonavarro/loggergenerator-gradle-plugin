package com.navid.loggergenerator;


import org.gradle.api.DefaultTask;
import org.gradle.api.file.ConfigurableFileCollection;
import org.gradle.api.file.FileCollection;
import org.gradle.api.tasks.InputFiles;
import org.gradle.api.tasks.JavaExec;
import org.gradle.api.tasks.TaskAction;

public class LoggerGeneratorTask extends DefaultTask {
    private String generatorVersion;

    private String inputFile;

    private String outputFolder;

    private String packageName;
    private final ConfigurableFileCollection executableJars;

    public LoggerGeneratorTask() {
        executableJars = getProject().files();
    }

    public String getInputFile() {
        return inputFile;
    }

    public LoggerGeneratorTask setInputFile(String inputFile) {
        this.inputFile = inputFile;
        return this;
    }

    public String getOutputFolder() {
        return outputFolder;
    }

    public LoggerGeneratorTask setOutputFolder(String outputFolder) {
        this.outputFolder = outputFolder;
        return this;
    }

    public String getPackageName() {
        return packageName;
    }

    public LoggerGeneratorTask setPackageName(String packageName) {
        this.packageName = packageName;
        return this;
    }

    public String getGeneratorVersion() {
        return generatorVersion;
    }

    public LoggerGeneratorTask setGeneratorVersion(String generatorVersion) {
        this.generatorVersion = generatorVersion;
        return this;
    }

    public void setExecutableJars(FileCollection executableJars) {
        this.executableJars.setFrom(executableJars);
    }

    @InputFiles
    public FileCollection getExecutableJars() {
        return executableJars;
    }

    @TaskAction
    void generateLogs() {
        System.out.println(getExecutableJars().getFiles());

        this.getProject().getTasks().withType(JavaExec.class);
        LoggerGeneratorInvoker.invoke(this);
    }
}
