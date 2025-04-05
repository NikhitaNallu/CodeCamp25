// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    id("com.google.gms.google-services") version "4.4.2" apply true
}

//plugins {
//    id("com.android.application") version "7.4.2" // or whatever version you're using
//    id("com.google.gms.google-services") version "4.3.15" // Version of Google services plugin
//}
//buildscript {
//
//    dependencies {
//        classpath("com.android.tools.build:gradle:7.4.2") // Use quotes and no extra characters
//        classpath("com.google.gms:google-services:4.3.15") // Use quotes and no extra characters
//    }
//}
//
//allprojects {
//    repositories {
//        google()
//        mavenCentral()
//    }
//}
//
//plugins {
//    id("com.android.application") version "7.4.2" apply false
//    id("com.google.gms.google-services") version "4.3.15" apply false
//}
//
//buildscript {
//    dependencies {
//        classpath("com.android.tools.build:gradle:7.4.2") // Android Gradle Plugin
//        classpath("com.google.gms:google-services:4.3.15") // Google services plugin
//    }
//}
//
