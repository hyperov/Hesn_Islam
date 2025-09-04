import java.util.Properties
import java.io.FileInputStream

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.dagger.hilt.android")
    id("kotlin-parcelize")
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
    id("com.google.firebase.firebase-perf")
    id("com.google.devtools.ksp")
}

val apiKeyPropertiesFile = rootProject.file("apikey.properties")
val apiKeyProperties = Properties().apply {
    load(FileInputStream(apiKeyPropertiesFile))
}

android {
    namespace = "com.islam.hesn.myapplication"
    signingConfigs {
        create("release") {
            storeFile = file("hesn_islam_key_store.jks")
            storePassword = "0109904219"
            keyAlias = "key_hesn_islam"
            keyPassword = "0109904219"
        }
    }
    buildFeatures {
        viewBinding = true
        buildConfig = true
    }

    defaultConfig {
        applicationId = "com.islam.hesn.myapplication"
        minSdk = 23
        compileSdk = 36
        targetSdk = 36
        versionCode = 16
        versionName = "2.6"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        buildConfigField("String", "YOUTUBE_API_KEY", apiKeyProperties["YOUTUBE_API_KEY"] as String)
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }

    buildTypes {
        getByName("debug") {
            manifestPlaceholders["crashlyticsCollectionEnabled"] = "false"
            extensions.extraProperties["enableCrashlytics"] = false
        }
        getByName("release") {
            isMinifyEnabled = true
            isShrinkResources = false
            extensions.extraProperties["enableCrashlytics"] = true
            signingConfig = signingConfigs.getByName("release")
            manifestPlaceholders["crashlyticsCollectionEnabled"] = "true"
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    packagingOptions {
        jniLibs {
            excludes += setOf("META-INF/*")
        }
        resources {
            excludes += mutableSetOf(
                "META-INF/DEPENDENCIES",
                "META-INF/LICENSE",
                "META-INF/LICENSE.txt",
                "META-INF/license.txt",
                "META-INF/NOTICE",
                "META-INF/NOTICE.txt",
                "META-INF/notice.txt",
                "META-INF/ASL2.0",
                "META-INF/*",
                "META-INF/*.kotlin_module")
        }
    }
}

dependencies {
    implementation(platform("com.google.firebase:firebase-bom:34.2.0"))
    implementation("com.google.firebase:firebase-crashlytics")
    implementation("com.google.firebase:firebase-analytics")
    implementation("com.google.firebase:firebase-perf")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.9.3")
    testImplementation("androidx.test.ext:junit-ktx:1.3.0")
    val paging_version = "3.3.6"
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation("androidx.core:core-ktx:1.17.0")
    implementation("androidx.appcompat:appcompat:1.7.1")
    implementation("androidx.constraintlayout:constraintlayout:2.2.1")
    implementation("com.google.android.material:material:1.13.0")
    implementation("androidx.navigation:navigation-fragment-ktx:2.9.3")
    implementation("androidx.navigation:navigation-ui-ktx:2.9.3")
    implementation("androidx.legacy:legacy-support-v4:1.0.0")
    implementation("androidx.recyclerview:recyclerview:1.4.0")

    implementation("com.google.code.gson:gson:2.13.1")

    //Retrofit
    implementation("com.squareup.retrofit2:retrofit:3.0.0")
    implementation("com.squareup.retrofit2:converter-gson:3.0.0")
    implementation("com.squareup.okhttp3:logging-interceptor:5.1.0")
//    implementation("com.squareup.retrofit2:converter-scalars:2.1.0")

    implementation("com.google.dagger:hilt-android:2.57.1")
    ksp("com.google.dagger:hilt-compiler:2.57.1")
    //viewmodel with hilt
    ksp("androidx.hilt:hilt-compiler:1.2.0")

    implementation("androidx.lifecycle:lifecycle-extensions:2.2.0")

    //view model , life cycle
//    implementation "android.arch.lifecycle:extensions:1.1.1"
    implementation("androidx.arch.core:core-common:2.2.0")



    //coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.10.2")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.10.2")
    //coroutines viewmodel scope
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.9.3")
    //coroutines lifecycle scope
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.9.3")

    //paging 3 lib
    implementation("androidx.paging:paging-runtime-ktx:$paging_version")

    implementation("com.google.api-client:google-api-client-android:2.8.1") {
        exclude(group = "org.apache.httpcomponents")
    }

    //repo url
    //https://mvnrepository.com/artifact/com.github.thoughtbot.expandable-recycler-view/expandablerecyclerview/v1.4
    implementation("com.github.thoughtbot.expandable-recycler-view:expandablerecyclerview:v1.4")

    implementation("com.github.bumptech.glide:glide:5.0.4")

    implementation("com.airbnb.android:lottie:6.6.7")

    implementation("com.pierfrancescosoffritti.androidyoutubeplayer:core:12.1.2")

    implementation("androidx.preference:preference-ktx:1.2.1")


    //review
    implementation("com.google.android.play:review:2.0.2")
    // For Kotlin users, also add the Kotlin extensions library for Play In-App Review:
    implementation("com.google.android.play:review-ktx:2.0.2")

    //update
    implementation("com.google.android.play:app-update:2.1.0")
    // For Kotlin users, also add the Kotlin extensions library for Play In-App Update:
    implementation("com.google.android.play:app-update-ktx:2.1.0")

    implementation ("com.github.mhiew:android-pdf-viewer:3.2.0-beta.3")

}

