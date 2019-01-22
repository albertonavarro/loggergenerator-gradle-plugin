# loggergenerator-gradle-plugin
Gradle plugin for https://github.com/albertonavarro/loggergenerator

## About loggergenerator
LoggerGenerator is a code generation tool that help you manage your Java logs according to 
the Cutting-Edge practices for logging described here: 
https://looking4q.blogspot.com/2018/11/logging-cutting-edge-practices.html

The usage of the tool is more thoroughly described in its repository: 
https://github.com/albertonavarro/loggergenerator

## Plugin usage

### Importing plugin 

```groovy
buildscript{
    repositories {
        maven { url "https://dl.bintray.com/albertonavarro/maven" }
    }
    dependencies{
        classpath 'com.navid.loggergenerator:loggergenerator-gradle-plugin:1.4.7'
    }
}

apply plugin: com.navid.loggergenerator.plugin.LoggerGeneratorPlugin

```

### Using the default task

```groovy
loggerGeneratorTask {
    inputFile = "validLogDescriptor.yml" /* this parameter is mandatory */
    packageName = "com.navid.codegen" /* Generated Java class package, defaults to com.example.codegen */
    codegenOutput = "$buildDir/codegen" /* Folder where code is going to be generated, defaults to project root */
    className = "LoggerContract" /* Generated Java class name, defaults to LoggerUtils */
    htmlName = "LoggerContactsDoc.html" /* Generated HTML file name, defaults to LoggerUtilsDoc.html */
    htmlOutput = "$buildDir/htmlgen" /* Folder where html file is going to be generated, defaults to project root */
    compat = "1.8" /* Java compatibility mode, possible values 1.7 and 1.8, defaults to 1.8 */
}
```

### Or creating your own task by extending LoggerGeneratorTask

```groovy
task myCodegenTask(type: LoggerGeneratorTask) {
    inputFile = "validLogDescriptor.yml" /* this parameter is mandatory */
    packageName = "com.navid.codegen" /* Generated Java class package, defaults to com.example.codegen */
    codegenOutput = "$buildDir/codegen" /* Folder where code is going to be generated, defaults to project root */
    className = "LoggerContract" /* Generated Java class name, defaults to LoggerUtils */
    htmlName = "LoggerContactsDoc.html" /* Generated HTML file name, defaults to LoggerUtilsDoc.html */
    htmlOutput = "$buildDir/htmlgen" /* Folder where html file is going to be generated, defaults to project root */
    compat = "1.8" /* Java compatibility mode, possible values 1.7 and 1.8, defaults to 1.8 */
}
```

### Overriding tool version

Given that loggergenerator and loggergenerator-gradle-plugin are developed and deployed separately, 
at some point, you might want to override default version. In that case, you need an Extension as follows:

So far, as long as you keep to the latest plugin version, you'll get the latest tool version anyway.

```groovy
LoggerGeneratorConfig {
    executableVersion = "1.3.0"
}
```

### Example of valid log descriptor 

*(please refer to https://github.com/albertonavarro/loggergenerator for up to date and version compliant format)*

```yml
version: 1
project-name: example
mappings:
  - name: amount
    type: java.lang.Integer
    description: Amount of money to match, in minimum representation (no decimals).
  - name: combinations
    type: java.lang.Integer
    description: Total number of combinations of change.
  - name: coins
    type: int
    description: Number of coins in a combination.
sentences:
  - code: ResultCombinations
    message: "Number of combinations of getting change"
    variables:
      - amount
      - combinations
    extradata: {}
    defaultLevel: info
  - code: ResultMinimum
    message: "Minimum number of coins required"
    variables:
      - amount
      - coins
    extradata: {}
    defaultLevel: info 
```

### Expected result

On `codegenOutput` files are generated with the `packageName` structure.

Previous example would generate something like follows:

```java
package com.navid.codegen;

import static net.logstash.logback.argument.StructuredArguments.keyValue;

import java.lang.Integer;
import java.lang.Iterable;
import net.logstash.logback.argument.StructuredArgument;
import org.slf4j.Logger;

public final class LoggerContract {
  public static StructuredArgument kvAmount(Integer amount) {
    return keyValue("amount",amount);
  }

  public static StructuredArgument aAmount(Iterable<Integer> amount) {
    return new net.logstash.logback.marker.ObjectAppendingMarker("amount",amount);
  }

  public static StructuredArgument aAmount(Integer... amount) {
    return new net.logstash.logback.marker.ObjectAppendingMarker("amount",amount);
  }

  public static StructuredArgument kvCombinations(Integer combinations) {
    return keyValue("combinations",combinations);
  }

  public static StructuredArgument aCombinations(Iterable<Integer> combinations) {
    return new net.logstash.logback.marker.ObjectAppendingMarker("combinations",combinations);
  }

  public static StructuredArgument aCombinations(Integer... combinations) {
    return new net.logstash.logback.marker.ObjectAppendingMarker("combinations",combinations);
  }

  public static StructuredArgument kvCoins(int coins) {
    return keyValue("coins",coins);
  }

  public static StructuredArgument aCoins(Iterable<Integer> coins) {
    return new net.logstash.logback.marker.ObjectAppendingMarker("coins",coins);
  }

  public static StructuredArgument aCoins(int... coins) {
    return new net.logstash.logback.marker.ObjectAppendingMarker("coins",coins);
  }

  public static void auditResultCombinations(Logger logger, Integer amount, Integer combinations) {
    logger.info("Number of combinations of getting change {} {}",kvAmount(amount),kvCombinations(combinations));
  }

  public static void auditResultCombinations(TriConsumer logger, Integer amount,
      Integer combinations) {
    logger.accept("Number of combinations of getting change {} {}",kvAmount(amount),kvCombinations(combinations));
  }

  public static void auditResultMinimum(Logger logger, Integer amount, int coins) {
    logger.info("Minimum number of coins required {} {}",kvAmount(amount),kvCoins(coins));
  }

  public static void auditResultMinimum(TriConsumer logger, Integer amount, int coins) {
    logger.accept("Minimum number of coins required {} {}",kvAmount(amount),kvCoins(coins));
  }

  public interface MonoConsumer {
    void accept(String var1);
  }

  public interface BiConsumer {
    void accept(String var1, Object var2);
  }

  public interface TriConsumer {
    void accept(String var1, Object var2, Object var3);
  }

  public interface ManyConsumer {
    void accept(String var1, Object... var2);
  }
}
```

### Using generated tool as part of your project

You will need *(for now)* some extra lines in your build.gradle so you can use the generated code:

```groovy
sourceSets {
    main {
        java {
            srcDir file("${buildDir}/codegen") /* whatever you have as codegenOutput parameter */
        }
    }
}

compileJava.dependsOn loggerGeneratorTask /* I recommend linking Java compilation to codegen */
```

Generated code requires following dependencies:

```groovy
compile("ch.qos.logback:logback-classic:1.2.3")
compile group: 'net.logstash.logback', name: 'logstash-logback-encoder', version: '5.1'
```

Now `./gradlew build` will trigger the codegen and it will be usable by your code.

### Check the examples

In `examples` https://github.com/albertonavarro/loggergenerator-gradle-plugin/tree/master/examples folder you will 
find different usages and parameters combinations.
