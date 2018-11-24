package com.navid.loggergenerator;

import java.util.HashMap;
import java.util.Map;

public class LoggerGeneratorInvoker {

    static void invoke(LoggerGeneratorTask loggerGenTask) {
        Map m = new HashMap<>();
        m.put("jar", loggerGenTask.getDataFiles().getSingleFile());
        m.put("fork", true);
        Map m2 = new HashMap<>();
        m2.put("--input", "/home/alberto/code/loggergenerator/src/test/resources/mapping.yml");
        m.put("args", m2);
        //m.put("args", "--input " + "/home/alberto/code/loggergenerator/src/test/resources/mapping.yml --package com.navid --output .");
        Object result = loggerGenTask.getAnt().invokeMethod("java", m);
        loggerGenTask.getLogger().info("Output: {}", result);
    }
}
