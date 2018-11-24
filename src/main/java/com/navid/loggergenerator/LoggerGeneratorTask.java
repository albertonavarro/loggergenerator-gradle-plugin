package com.navid.loggergenerator;


import org.gradle.api.DefaultTask;
import org.gradle.api.file.ConfigurableFileCollection;
import org.gradle.api.file.FileCollection;
import org.gradle.api.tasks.InputFiles;
import org.gradle.api.tasks.JavaExec;
import org.gradle.api.tasks.TaskAction;

public class LoggerGeneratorTask extends DefaultTask {
    private String message;
    private String recipient;
    private final ConfigurableFileCollection dataFiles;

    public LoggerGeneratorTask() {
        dataFiles = getProject().files();
    }


    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public String getRecipient() { return recipient; }
    public void setRecipient(String recipient) { this.recipient = recipient; }

    public void setDataFiles(FileCollection dataFiles) {
        this.dataFiles.setFrom(dataFiles);
    }

    @InputFiles
    public FileCollection getDataFiles() {
        return dataFiles;
    }

    @TaskAction
    void sayGreeting() {
        System.out.printf("%s, %s!\n", getMessage(), getRecipient());
        System.out.println(getDataFiles().getFiles());

        this.getProject().getTasks().withType(JavaExec.class);
        LoggerGeneratorInvoker.invoke(this);
    }
}
