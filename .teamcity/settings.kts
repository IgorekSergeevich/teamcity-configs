import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.gradle
import jetbrains.buildServer.configs.kotlin.v2019_2.triggers.finishBuildTrigger
import jetbrains.buildServer.configs.kotlin.v2019_2.triggers.vcs

/*
The settings script is an entry point for defining a TeamCity
project hierarchy. The script should contain a single call to the
project() function with a Project instance or an init function as
an argument.

VcsRoots, BuildTypes, Templates, and subprojects can be
registered inside the project using the vcsRoot(), buildType(),
template(), and subProject() methods respectively.

To debug settings scripts in command-line, run the

    mvnDebug org.jetbrains.teamcity:teamcity-configs-maven-plugin:generate

command and attach your debugger to the port 8000.

To debug in IntelliJ Idea, open the 'Maven Projects' tool window (View
-> Tool Windows -> Maven Projects), find the generate task node
(Plugins -> teamcity-configs -> teamcity-configs:generate), the
'Debug' option is available in the context menu for the task.
*/

version = "2020.2"

project {
    description = "My test root project"

    params {
        param("Repo", "teamcity-configs")
    }

    subProject(Backend)
}


object Backend : Project({
    name = "Backend"
    description = "My test backend project"

    buildType(Backend_TryActiveMq)
    buildType(Backend_BuildBackEnd)
})

object Backend_BuildBackEnd : BuildType({
    name = "Build back end"
    description = "Build backend"

    artifactRules = "backend/cloud-publishing-rest/employee-rest-service/build/libs/employee-rest-service-0.0.1-SNAPSHOT.jar"

    params {
        param("Repo", "cloud-publishing")
    }

    vcs {
        root(DslContext.settingsRoot)
    }

    steps {
        gradle {
            tasks = "clean build"
            buildFile = "backend/build.gradle"
            gradleWrapperPath = "backend"
        }
    }
})
