import jetbrains.buildServer.configs.kotlin.*
import jetbrains.buildServer.configs.kotlin.buildFeatures.perfmon
import jetbrains.buildServer.configs.kotlin.buildSteps.nodeJS
import jetbrains.buildServer.configs.kotlin.triggers.vcs


version = "2024.12"

project {
    buildType(Install)
    buildType(Build)
    buildType(Test)

    sequential {
        buildType(Install)
        buildType(Build)
        buildType(Test)
    }
}

object Install : BuildType({
    name = "Install"
    vcs {
        root(DslContext.settingsRoot)
    }
    steps {
        nodeJS {
            id = "nodejs_runner"
            shellScript = "npm ci"
        }
    }

    features {
        perfmon {
        }
    }
})

object Test : BuildType({
    name = "Test"

    vcs {
        root(DslContext.settingsRoot)
    }

    steps {
        nodeJS {
            id = "nodejs_runner"
            shellScript = "npm run test"
        }
    }
})

object Build : BuildType({
    name = "Build"
    steps {
        nodeJS {
            id = "nodejs_runner"
            shellScript = "npm run build"
        }
    }
})
