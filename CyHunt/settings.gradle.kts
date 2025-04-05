//pluginManagement {
//    repositories {
//        google {
//            content {
//                includeGroupByRegex("com\\.android.*")
//                includeGroupByRegex("com\\.google.*")
//                includeGroupByRegex("androidx.*")
//            }
//        }
//        mavenCentral()
//        gradlePluginPortal()
//    }
//}
//dependencyResolutionManagement {
//    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
//    repositories {
//        google()
//        mavenCentral()
//    }
//}
//
//rootProject.name = "CyHunt"
//include(":app")
//
pluginManagement {
    repositories {
        google() // Repository for Android and Google plugins
        mavenCentral() // Central repository for other plugins
        gradlePluginPortal() // Gradle plugin portal for additional plugins
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google() // Repository for project dependencies
        mavenCentral() // Central repository for other dependencies
    }
}

rootProject.name = "CyHunt"
include(":app") // Include your app module in the project
