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

val keystoreProperties: Properties by lazy {
    val properties = Properties()

    val localPropertiesFile = rootProject.file("keystore.properties")

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
        versionCode = 26
        versionName = "1.4.2"

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
                storePassword = keystoreProperties["storePassword"].toString()
                keyAlias = keystoreProperties["keyAlias"].toString()
                keyPassword = keystoreProperties["keyPassword"].toString()
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
    implementation(libs.bundles.bundle.androidx.compose)

    implementation(libs.androidx.material3)

    implementation(libs.androidx.ui.text.google.fonts)

    //Splash Api
    implementation (libs.splash.api)

    //App Updates
    implementation(libs.bundles.bundle.app.updates)

    //Compose Navigation
    implementation(libs.androidx.compose.navigation)

    // Lifecycle
    implementation(libs.bundles.bundle.androidx.lifecycle)

    implementation(libs.androidx.runtime.livedata)

    //Coil
    implementation(libs.bundles.bundle.coil)

    //Retrofit
    implementation (libs.retrofit)
    implementation (libs.converter.gson)
    implementation (libs.logging.interceptor)

    implementation (libs.gson)

    //Hilt
    implementation(libs.androidx.hilt.compose.navigation)
    implementation(libs.hilt)
    ksp(libs.hilt.compiler)

    //Room
    implementation (libs.bundles.bundle.room)
    ksp (libs.androidx.room.compiler)

    implementation(libs.androidx.compose.material3.adaptive.navigation)

    //Paging 3
    implementation (libs.bundles.bundle.paging)

    //Material Icon Extended
    implementation (libs.material.icons.extended)

    //Firebase
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.crashlytics)

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

    androidTestImplementation(libs.hilt.android.testing)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
}