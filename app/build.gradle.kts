import java.util.Properties
import java.io.FileInputStream

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.hilt)
    alias(libs.plugins.kotlin.parcelize)
    alias(libs.plugins.google.services)
    alias(libs.plugins.firebase.crashlytics)
    alias(libs.plugins.firebase.perf)
    alias(libs.plugins.ksp)
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
    compileSdk = libs.versions.compileSdk.get().toInt()
    defaultConfig {
        applicationId = "com.islam.hesn.myapplication"
        minSdk = libs.versions.minSdk.get().toInt()
        targetSdk = libs.versions.targetSdk.get().toInt()
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
            excludes += setOf(
                "META-INF/DEPENDENCIES",
                "META-INF/LICENSE",
                "META-INF/LICENSE.txt",
                "META-INF/license.txt",
                "META-INF/NOTICE",
                "META-INF/NOTICE.txt",
                "META-INF/notice.txt",
                "META-INF/ASL2.0",
                "META-INF/*",
                "META-INF/*.kotlin_module"
            )
        }
    }
}

dependencies {
    implementation(platform(libs.firebase.bom))
    implementation(libs.bundles.firebase)

    implementation(libs.bundles.androidx.lifecycle)
    testImplementation(libs.androidx.test.ext.junit.ktx)

    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.material)
    implementation(libs.bundles.androidx.navigation)
    implementation(libs.androidx.legacy.support.v4)
    implementation(libs.androidx.recyclerview)

    implementation(libs.gson)

    // Retrofit
    implementation(libs.bundles.retrofit)

    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
    // viewmodel with hilt
    ksp(libs.androidx.hilt.compiler)

    implementation(libs.androidx.lifecycle.extensions)

    // view model, life cycle
    implementation(libs.androidx.arch.core.common)

    // coroutines
    implementation(libs.bundles.coroutines)

    // paging 3 lib
    implementation(libs.androidx.paging.runtime.ktx)

    // Google Play libraries for app updates and reviews (Android 14 compatible)
    implementation(libs.play.app.update)
    implementation(libs.play.review)

    implementation(libs.google.api.client.android) {
        exclude(group = "org.apache.httpcomponents")
    }

    // repo url
    // https://mvnrepository.com/artifact/com.github.thoughtbot.expandable-recycler-view/expandablerecyclerview/v1.4
    implementation(libs.expandablerecyclerview)

    implementation(libs.glide)

    implementation(libs.lottie)

    implementation(libs.youtubeplayer.core)

    implementation(libs.androidx.preference.ktx)

    // PDF Viewer library
    implementation(libs.pdfviewer)
}
