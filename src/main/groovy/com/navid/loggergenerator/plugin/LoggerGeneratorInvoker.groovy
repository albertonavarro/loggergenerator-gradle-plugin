package com.navid.loggergenerator.plugin

class LoggerGeneratorInvoker {

    static void invoke(LoggerGeneratorTask loggerGenTask) {
        Map executionParameters = new HashMap<>();
        executionParameters.put("jar", loggerGenTask.getExecutableJars().getSingleFile());
        executionParameters.put("fork", true);
        executionParameters.put("args",
                "--input " + loggerGenTask.getInputFile() +
                        " --package " + loggerGenTask.getPackageName() +
                        " --output " + loggerGenTask.getOutputFolder());
        Object result = loggerGenTask.getAnt().invokeMethod("java", executionParameters);
    }
}
