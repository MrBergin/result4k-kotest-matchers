import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.gradle
import jetbrains.buildServer.configs.kotlin.v2019_2.triggers.vcs

version = "2020.2"

project {

    params {
        text(
            name = "env.ORG_GRADLE_PROJECT_ossrhUsername",
            value = "mrbergin",
            readOnly = true,
            allowEmpty = true,
        )
        password(
            name = "env.ORG_GRADLE_PROJECT_ossrhPassword",
            value = "credentialsJSON:f51c6ca9-d418-4fc8-a59e-a8719a3cdb18",
            display = ParameterDisplay.HIDDEN,
            readOnly = true,
        )
        password(
            name = "env.ORG_GRADLE_PROJECT_signingKeyId",
            value = "credentialsJSON:15a6bff2-8f23-4dd5-a7b3-ebf3ae2d1b40",
            display = ParameterDisplay.HIDDEN,
            readOnly = true
        )
        password(
            name = "env.ORG_GRADLE_PROJECT_signingPassword",
            value = "credentialsJSON:f51c6ca9-d418-4fc8-a59e-a8719a3cdb18",
            display = ParameterDisplay.HIDDEN,
            readOnly = true,
        )
        password(
            name = "env.ORG_GRADLE_PROJECT_signingKey",
            value = "credentialsJSON:058438f9-5fcb-4c6b-be28-dcfb0f6f36da",
            display = ParameterDisplay.HIDDEN,
            readOnly = true,
        )
    }

    buildType(Build)
    buildType(Release)

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
})

object Release : BuildType({
    name = "Release"
    allowExternalStatus = true

    vcs {
        root(DslContext.settingsRoot)
    }

    steps {
        gradle {
            tasks = ":publishMavenPublicationToSonatypeStagingRepository"
            jdkHome = "%env.JDK_11_0_x64%"
        }
    }
})
