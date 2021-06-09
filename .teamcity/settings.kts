import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.gradle
import jetbrains.buildServer.configs.kotlin.v2019_2.triggers.vcs

version = "2020.2"

project {

    buildType(Build)

    features {
        feature {
            id = "PROJECT_EXT_2"
            type = "IssueTracker"
            param("secure:password", "")
            param("name", "MrBergin/result4k-kotest-matchers")
            param("pattern", """#(\d+)""")
            param("authType", "anonymous")
            param("repository", "https://github.com/MrBergin/result4k-kotest-matchers")
            param("type", "GithubIssues")
            param("secure:accessToken", "")
            param("username", "")
        }
    }
}

object Build : BuildType({
    name = "Build"

    allowExternalStatus = true

    vcs {
        root(DslContext.settingsRoot)
    }

    steps {
        gradle {
            tasks = ":build"
            jdkHome = "%env.JDK_11_0_x64%"
            coverageEngine = jacoco {
                classLocations = "+:build/classes/kotlin/main/**"
                jacocoVersion = "%teamcity.tool.jacoco.0.8.4%"
            }
        }
    }

    triggers {
        vcs {
        }
    }

    requirements {
        matches("teamcity.agent.jvm.os.family", "Linux")
    }
})
