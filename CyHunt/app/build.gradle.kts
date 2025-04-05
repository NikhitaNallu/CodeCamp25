//plugins {
//    alias(libs.plugins.android.application)
//    id("com.google.gms.google-services")
//}
//
//android {
//    namespace = "com.example.cyhunt"
//    compileSdk = 35
//
//    defaultConfig {
//        applicationId = "com.example.cyhunt"
//        minSdk = 26
//        targetSdk = 35
//        versionCode = 1
//        versionName = "1.0"
//
//        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
//    }
//
//    buildTypes {
//        release {
//            isMinifyEnabled = false
//            proguardFiles(
//                getDefaultProguardFile("proguard-android-optimize.txt"),
//                "proguard-rules.pro"
//            )
//        }
//    }
//    compileOptions {
//        sourceCompatibility = JavaVersion.VERSION_11
//        targetCompatibility = JavaVersion.VERSION_11
//    }
//}
//
//dependencies {
//
//    implementation(libs.appcompat)
//    implementation(libs.material)
//    implementation(libs.activity)
//    implementation(libs.constraintlayout)
//    implementation(libs.firebase.auth)
//    implementation(libs.play.services.maps)
//   // implementation(libs.gms.play.services.maps)
//    //implementation(libs.play.services.location)
//    implementation(libs.firebase.auth)
//    implementation(libs.firebase.firestore)
//    testImplementation(libs.junit)
//    androidTestImplementation(libs.ext.junit)
//    androidTestImplementation(libs.espresso.core)
//    implementation (libs.play.services.auth)
//    implementation (libs.play.services.maps)
//    implementation(platform(libs.firebase.bom))
//    implementation(libs.firebase.analytics)
//    implementation(libs.firebase.firestore)
//    implementation(libs.appcompat)
//    implementation(libs.material)
//    implementation(libs.activity)
//    implementation(libs.constraintlayout)
//    implementation(libs.firebase.auth)
//    testImplementation(libs.junit)
//    androidTestImplementation(libs.ext.junit)
//    androidTestImplementation(libs.espresso.core)
//    implementation (libs.play.services.auth)
//    implementation (libs.play.services.maps)
//    implementation(platform(libs.firebase.bom))
//    implementation(libs.firebase.analytics)
//    implementation(libs.firebase.firestore)
//}
//plugins {
//    alias(libs.plugins.android.application)
//    id("com.google.gms.google-services")
//}
//
//android {
//    namespace = "com.example.cyhunt"
//    compileSdk = 35
//
//    defaultConfig {
//        applicationId = "com.example.cyhunt"
//        minSdk = 26
//        targetSdk = 35
//        versionCode = 1
//        versionName = "1.0"
//
//        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
//    }
//
//    buildTypes {
//        release {
//            isMinifyEnabled = false
//            proguardFiles(
//                getDefaultProguardFile("proguard-android-optimize.txt"),
//                "proguard-rules.pro"
//            )
//        }
//    }
//
//    compileOptions {
//        sourceCompatibility = JavaVersion.VERSION_11
//        targetCompatibility = JavaVersion.VERSION_11
//    }
//}
//
//dependencies {
//
//    // UI Dependencies
//    implementation(libs.appcompat)
//    implementation(libs.material)
//    implementation(libs.activity)
//    implementation(libs.constraintlayout)
//
//    // Firebase Dependencies
//    implementation(platform(libs.firebase.bom)) // Firebase BOM to manage versions automatically
//    implementation(libs.firebase.auth)          // Firebase Authentication
//    implementation(libs.firebase.firestore)    // Firebase Firestore
//    implementation(libs.firebase.analytics)    // Firebase Analytics
//
//    // Google Play Services Dependencies
//    implementation(libs.play.services.maps)     // Google Maps
//    implementation(libs.play.services.auth)     // Google Auth
//
//    // Testing Dependencies
//    testImplementation(libs.junit)
//    androidTestImplementation(libs.ext.junit)
//    androidTestImplementation(libs.espresso.core)
//}
plugins {
    alias(libs.plugins.android.application)
    id("com.google.gms.google-services")
}


android {
    namespace = "com.example.cyhunt"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.cyhunt"
        minSdk = 26
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    implementation("androidx.appcompat:appcompat:1.4.0")
    implementation("com.google.android.material:material:1.5.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.3")

    // Firebase dependencies
    implementation(platform("com.google.firebase:firebase-bom:29.0.3"))
    implementation("com.google.firebase:firebase-auth")
    implementation("com.google.firebase:firebase-firestore")
    implementation("com.google.firebase:firebase-analytics")

    // Google Play services dependencies
    implementation("com.google.android.gms:play-services-maps:17.0.1")
    implementation("com.google.android.gms:play-services-auth:20.0.1")

    // Testing dependencies
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
}
