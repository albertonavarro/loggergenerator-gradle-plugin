package com.navid.loggergenerator.plugin

class LoggerGeneratorInvoker {

    static void invoke(LoggerGeneratorTask loggerGenTask) {
        Map executionParameters = new HashMap<>();
        executionParameters.put("failOnError", true)
        executionParameters.put("jar", loggerGenTask.getExecutableJars().getSingleFile());
        executionParameters.put("fork", true);
        executionParameters.put("args",
                "--input " + loggerGenTask.getInputFile() +
                        " --package " + loggerGenTask.getPackageName() +
                        " --codegen-output " + loggerGenTask.getCodegenOutput() +
                        " --class-name " + loggerGenTask.getClassName() +
                        " --html-name " + loggerGenTask.getHtmlName() +
                        " --html-output " + loggerGenTask.getHtmlOutput() +
                        " --compat-" + loggerGenTask.getCompat())

        try {
            Object result = loggerGenTask.getAnt().invokeMethod("java", executionParameters)
        } catch( Exception e) {
            System.out.println("Command failed: " + executionParameters.toString())
            throw e
        }
    }
}
