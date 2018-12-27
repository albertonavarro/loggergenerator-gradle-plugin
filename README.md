# loggergenerator-gradle-plugin
Gradle plugin for https://github.com/albertonavarro/loggergenerator

## About loggergenerator
LoggerGenerator is a code generation tool that help you manage your Java logs according to 
the Cutting-Edge practices for logging described here: 
https://looking4q.blogspot.com/2018/11/logging-cutting-edge-practices.html

The usage of the tool is more thoroughly described in its repository: 
https://github.com/albertonavarro/loggergenerator

## Plugin installation:

Until it is accepted as gradle plugin..

git clone https://github.com/albertonavarro/loggergenerator-gradle-plugin

./gradlew clean build publishPluginPublicationPublicationToMavenLocal

## Plugin usage

### Importing plugin 

```groovy
buildscript{
    repositories {
        mavenLocal()

    }
    dependencies{
        classpath 'com.navid.loggergenerator:loggergenerator-plugin:1.1.0'
    }
}

apply plugin: com.navid.loggergenerator.plugin.LoggerGeneratorPlugin

```

### Using the default task

```groovy
loggerGeneratorTask{
    inputFile = "validLogDescriptor.yml"
    packageName = "com.navid.codegen"
    outputFolder = "$buildDir/codegen"
}
```

### Overriding tool version

Given that loggergenerator and loggergenerator-gradle-plugin are developed and deployed separately, 
at some point, you might want to override default version. In that case, you need an Extension as follows:

```groovy
LoggerGeneratorConfig {
    executableVersion = "1.0.2"
}
```

### Example of valid log descriptor 

*(please refer to https://github.com/albertonavarro/loggergenerator for up to date and version compliant format)*

```yml
version: 1
mappings:
  - name: objectId
    type: java.lang.String
    description: blablablah
  - name: status
    type: java.lang.Number
    description: blablablah
sayings:
  - code: COD_1
    message: "something went wrong with this other thing"
    variables:
      - objectId
      - status
    extradata:
      key: value
```

### Expected result

On `outputFolder` files are generated with the `packageName` structure.

For above example, we can expect a generated source like:
```java
package com.navid.codegen;

import static net.logstash.logback.argument.StructuredArguments.*;

import java.lang.Iterable;
import java.lang.Number;
import java.lang.String;
import net.logstash.logback.argument.StructuredArgument;

public final class LoggerUtils {
  public static StructuredArgument kvObjectId(String objectId) {
    return keyValue("objectId",objectId);
  }

  public static StructuredArgument aObjectId(Iterable<String> objectId) {
    return array("objectId",objectId);
  }

  public static StructuredArgument aObjectId(String... objectId) {
    return array("objectId",objectId);
  }

  public static StructuredArgument kvStatus(Number status) {
    return keyValue("status",status);
  }

  public static StructuredArgument aStatus(Iterable<Number> status) {
    return array("status",status);
  }

  public static StructuredArgument aStatus(Number... status) {
    return array("status",status);
  }
}
```

### Using generated tool as part of your project

You will need *(for now)* some extra lines in your build.gradle so you can use the generated code:

```groovy
sourceSets {
    main {
        java {
            srcDir file("${buildDir}/codegen")
        }
    }
}

compileJava.dependsOn loggerGeneratorTask
```

and some dependencies

```groovy
compile("ch.qos.logback:logback-classic:1.2.3")
compile group: 'net.logstash.logback', name: 'logstash-logback-encoder', version: '5.1'
```

Now `./gradlew build` will trigger the codegen and it will be usable by your code.
