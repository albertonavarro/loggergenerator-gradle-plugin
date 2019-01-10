package com.navid.loggergenerator.plugin

import org.gradle.api.AntBuilder

class LoggerGeneratorInvoker {

    static void invoke(LoggerGeneratorTask loggerGenTask) {
        Map executionParameters = new HashMap<>();
        executionParameters.put("jar", loggerGenTask.getExecutableJars().getSingleFile());
        executionParameters.put("args",
                "--input " + loggerGenTask.getInputFile() +
                        " --package " + loggerGenTask.getPackageName() +
                        " --codegen-output " + loggerGenTask.getCodegenOutput() +
                        " --class-name " + loggerGenTask.getClassName() +
                        " --html-name " + loggerGenTask.getHtmlName() +
                        " --html-output " + loggerGenTask.getHtmlOutput() +
                        " --compat-" + loggerGenTask.getCompat())

        try {
            loggerGenTask.project.javaexec {
                main="-jar"
                args = [ loggerGenTask.getExecutableJars().getSingleFile(),
                        "--input" , loggerGenTask.getInputFile() ,
                                "--package" , loggerGenTask.getPackageName() ,
                                "--codegen-output" , loggerGenTask.getCodegenOutput() ,
                                "--class-name" , loggerGenTask.getClassName() ,
                                "--html-name" , loggerGenTask.getHtmlName() ,
                                "--html-output" , loggerGenTask.getHtmlOutput() ,
                                "--compat-" + loggerGenTask.getCompat()
                ]
            }
        } catch( Exception e) {
            System.out.println("Command failed: " + executionParameters.toString())
            throw e
        }
    }
}
