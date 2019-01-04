package com.navid.loggergenerator.plugin

import org.gradle.testkit.runner.BuildResult
import org.gradle.testkit.runner.GradleRunner
import org.junit.*
import org.junit.rules.TemporaryFolder

import static org.gradle.testkit.runner.TaskOutcome.*

/**
 * Contains functional tests that use the GradleRunner to run the plugin's task in a controlled environment.
 * Reference:
 * https://docs.gradle.org/4.6/userguide/test_kit.html#sec:functional_testing_with_the_gradle_runner
 */
class LoggerGeneratorModuleTest {
    @Rule
    public final TemporaryFolder testProjectDir = new TemporaryFolder();

    private File build_gradle

    private String validFile = LoggerGeneratorModuleTest.class.getResource("/validLogDescriptor.yml").getFile();

    @Before
    void setup() {
        // Prepare build.gradle
        System.println(testProjectDir.getRoot())
        build_gradle = testProjectDir.newFile('build.gradle')
        build_gradle << 'plugins { id "com.navid.LoggerGeneratorPlugin" }\n'
    }

    /**
     * Helper method that runs a Gradle task in the testProjectDir
     * @param arguments the task arguments to execute
     * @param isSuccessExpected boolean representing whether or not the build is supposed to fail
     * @return the task's BuildResult
     */
    private BuildResult gradle(boolean isSuccessExpected, String[] arguments = ['tasks']) {
        arguments += '--stacktrace'
        def runner = GradleRunner.create()
                .withArguments(arguments)
                .withProjectDir(testProjectDir.root)
                .withPluginClasspath()
                .withDebug(true)
        return isSuccessExpected ? runner.build() : runner.buildAndFail()
    }

    private BuildResult gradle(String[] arguments = ['tasks']) {
        gradle(true, arguments)
    }

    @Test
    void loggerGeneratorTask_extendedConfigBlock() {
        build_gradle << """
            task goodTask(type: LoggerGeneratorTask) {
                packageName = "com.navid.test.loggerGeneratorTask_extendedConfigBlock"
                classOutput = "${testProjectDir.newFolder('codegen').path}"
                inputFile = "$validFile"
            }
            """

        def result = gradle('goodTask')

        assert result.task(":goodTask").outcome == SUCCESS
    }

    @Test(expected = Exception.class)
    void loggerGeneratorTask_LoggerGeneratorConfigBlock() {
        build_gradle << """
            LoggerGeneratorConfig {
                executableVersion = "inexisting"
            }
            
            loggerGeneratorTask {
                inputFile = "$validFile"
            }
            """

        def result = gradle('loggerGeneratorTask')

        assert result.task(":loggerGeneratorTask").outcome == SUCCESS
    }

    @Test
    void loggerGeneratorTask_configBlock() {
        build_gradle << """
            loggerGeneratorTask {
                packageName = "Ahoy"
                classOutput = "William"
                inputFile = "$validFile"
            }
            """

        def result = gradle('loggerGeneratorTask')

        assert result.task(":loggerGeneratorTask").outcome == SUCCESS
    }

}
