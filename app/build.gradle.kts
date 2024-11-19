import java.io.FileNotFoundException
import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.hilt)
    alias(libs.plugins.google.android.libraries.mapsplatform.secrets.gradle.plugin)
    alias(libs.plugins.devtool.ksp)
    alias(libs.plugins.kotlin.serialization)
    alias (libs.plugins.gms.google.services)
    alias (libs.plugins.firebase.crashlytics)
    id("kotlin-parcelize")
}

val animeLoomProperties: Properties by lazy {
    val properties = Properties()

    val localPropertiesFile = rootProject.file("local.properties")

    if (localPropertiesFile.exists()) {
        properties.load(localPropertiesFile.inputStream())
    } else {
        throw FileNotFoundException("Local properties file not found.")
    }

    properties
}

android {
    namespace = ProjectConfig.NAMESPACE
    compileSdk = ProjectConfig.COMPILE_SDK

    val isGenerateBuild = ProjectConfig.GENERATE_LOCAL_ARCHIVE
    val configVersionCode = ProjectConfig.VERSION_CODE
    val configMajorVersion = ProjectConfig.MAJOR_VERSION
    val configMinorVersion = ProjectConfig.MINOR_VERSION
    val configPatchVersion = ProjectConfig.PATCH_VERSION
    val appName = ProjectConfig.APP_FILENAME

    defaultConfig {
        applicationId = ProjectConfig.APPLICATION_ID
        minSdk = ProjectConfig.MIN_SDK
        targetSdk = ProjectConfig.TARGET_SDK
        versionCode = 23
        versionName = "1.2.4"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        buildConfigField("String", "BASE_URL", "\"https://kitsu.app/api/edge/\"")

        if (isGenerateBuild) {
            versionCode = configVersionCode
            versionName = "${configMajorVersion}.${configMinorVersion}.${configPatchVersion}"

            applicationVariants.all {
                base.archivesName.set("$appName-${buildType.name}-$versionCode-$versionName")
            }
        }
    }

    if (isGenerateBuild){
        signingConfigs {
            register("release") {
                storeFile = file("keystore/animeloom.jks")
                storePassword = animeLoomProperties["storePassword"].toString()
                keyAlias = animeLoomProperties["keyAlias"].toString()
                keyPassword = animeLoomProperties["keyPassword"].toString()
            }
        }
    }

    buildTypes {
        debug {
            applicationIdSuffix = ".debug"
            isDebuggable = true
            isMinifyEnabled = false
        }

        release {
            if (isGenerateBuild) signingConfig = signingConfigs.getByName("release")
            isDebuggable = false
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)

    implementation(libs.material)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)

    implementation(libs.androidx.ui.text.google.fonts)

    //Splash Api
    implementation (libs.splash.api)

    //Compose Navigation
    implementation(libs.androidx.compose.navigation)

    // Lifecycle
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.lifecycle.runtime.compose)
    implementation(libs.androidx.lifecycle.livedata.ktx)

    //Coil
    implementation(libs.coil.compose)
    implementation(libs.coil.network.okhttp)

    //Retrofit
    implementation (libs.retrofit)
    implementation (libs.converter.gson)
    implementation (libs.logging.interceptor)

    implementation (libs.gson)

    //Hilt
    implementation(libs.androidx.hilt.compose.navigation)
    implementation(libs.hilt)
    implementation(libs.androidx.runtime.livedata)
    ksp(libs.hilt.compiler)
    androidTestImplementation(libs.hilt.android.testing)

    //Room
    implementation (libs.androidx.room.runtime)
    ksp (libs.androidx.room.compiler)
    implementation (libs.androidx.room.ktx)

    //Paging 3
    implementation (libs.androidx.paging.runtime)
    implementation (libs.androidx.paging.compose)

    //Material Icon Extended
    implementation (libs.material.icons.extended)

    //Firebase
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.crashlytics)
    implementation(libs.firebase.analytics)

    //Datastore
    implementation (libs.androidx.datastore.preferences)

    implementation(libs.kotlinx.serialization.json)

    // Mockito for mocking
    testImplementation (libs.mockk)
    testImplementation (libs.mockk.mockk)
    testImplementation (libs.mockito.kotlin)

    // Coroutines test
    testImplementation (libs.kotlinx.coroutines.test)

    // Turbine for Flow testing
    testImplementation (libs.turbine)

    // ByteBuddy for mock creation
    testImplementation (libs.byte.buddy)

    // Lifecycle and ViewModel testing
    testImplementation (libs.androidx.core.testing)

    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}