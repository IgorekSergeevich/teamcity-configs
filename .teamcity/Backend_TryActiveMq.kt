import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.gradle
import jetbrains.buildServer.configs.kotlin.v2019_2.triggers.finishBuildTrigger
import jetbrains.buildServer.configs.kotlin.v2019_2.triggers.vcs

object Backend_TryActiveMq : BuildType({
    name = "Try active mq"

    params {
        param("Repo", "try-activemq")
    }

    vcs {
        root(DslContext.settingsRoot)
    }

    steps {
        gradle {
            tasks = "clean build"
            buildFile = "external-system/build.gradle"
            gradleWrapperPath = "external-system"
        }
        gradle {
            tasks = "clean build"
            buildFile = "registration-service/build.gradle"
            gradleWrapperPath = "external-system"
        }
    }

    triggers {
        vcs {
        }
        //finishBuildTrigger {
            //enabled = true
            //buildType = "${Settings.Backend_BuildBackEnd.id}"
            //branchFilter = """
                //+:<default>
                //+:feature/*
            //""".trimIndent()
        //}
    }

    dependencies {
        artifacts(Settings.Backend_BuildBackEnd) {
            buildRule = sameChainOrLastFinished()
            artifactRules = "employee-rest-service-0.0.1-SNAPSHOT.jar"
            enabled = true
        }
        snapshot(Settings.Backend_BuildBackEnd) {}
    }
})