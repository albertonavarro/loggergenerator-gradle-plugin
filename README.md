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
  - name: meloinvento
    type: com.mycompany.MadeUp
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

You will need *(for now)* some extra lines in your build.gradle so you can use the generated code:

```groovy
compileJava.dependsOn loggerGeneratorTask
sourceSets.main.java.srcDir "${buildDir}/codegen"
```

Now `./gradlew build` will trigger the codegen and it will be usable by your code.
