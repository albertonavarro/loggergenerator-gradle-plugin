package com.navid.loggergenerator;

public class LoggerGeneratorExtension {

    private String generatorVersion;

    private String inputFile;

    private String outputFolder;

    private String packageName;

    public String getGeneratorVersion() {
        return generatorVersion;
    }

    public LoggerGeneratorExtension setGeneratorVersion(String generatorVersion) {
        this.generatorVersion = generatorVersion;
        return this;
    }

    public String getInputFile() {
        return inputFile;
    }

    public LoggerGeneratorExtension setInputFile(String inputFile) {
        this.inputFile = inputFile;
        return this;
    }

    public String getOutputFolder() {
        return outputFolder;
    }

    public LoggerGeneratorExtension setOutputFolder(String outputFolder) {
        this.outputFolder = outputFolder;
        return this;
    }

    public String getPackageName() {
        return packageName;
    }

    public LoggerGeneratorExtension setPackageName(String packageName) {
        this.packageName = packageName;
        return this;
    }
}
