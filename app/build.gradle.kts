import com.android.build.api.dsl.ApkSigningConfig
import java.util.*

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "online.vapcom.githubstars"
    compileSdk = 34
    buildToolsVersion = "34.0.0"

    defaultConfig {
        applicationId = "online.vapcom.githubstars"
        minSdk = 21
        targetSdk = 34
        versionCode = 1
        versionName = "1.0.0.$versionCode"
        setProperty("archivesBaseName", "githubstars-$versionName")

        // global config
        buildConfigField("String", "GITHUB_SEARCH_ENDPOINT", "\"https://api.github.com/search/\"")

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    fun ApkSigningConfig.initFromProperties() {
        val keystoreProperties = Properties().apply {
            //TODO: for release build this file path should be given via Gradle properties
            val propsFileName = "$name-keystore.properties"
            val file = rootProject.file(propsFileName)

            if (file.canRead()) {
                file.inputStream().use(::load)
            } else throw InvalidUserDataException("Cannot find keystore properties file '${file.absolutePath}'")
        }

        storeFile = keystoreProperties.getProperty("storeFile")?.let(::file)
        storePassword = keystoreProperties.getProperty("storePassword")
        keyAlias = keystoreProperties.getProperty("keyAlias")
        keyPassword = keystoreProperties.getProperty("keyPassword")

        enableV1Signing = true
        enableV2Signing = true
        enableV3Signing = true
        enableV4Signing = true
    }

    signingConfigs {
        create("release") {
            //NOTE: currently using debug keys with release name
            //If you are going to build a release for Google Play, you need to generate new ones.
            initFromProperties()
        }
        getByName("debug") {
            initFromProperties()
        }
    }

    buildTypes {
        getByName("release") {
            //TODO: before release: discuss with PM is it need to obfuscate app?
            isMinifyEnabled = false

            //TODO: set it to false for release builds
            isDebuggable = true

            //TODO: uncomment and setup proguard files for release candidate:
            // proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")

            signingConfig = signingConfigs.getByName("release")
        }

        getByName("debug") {
            isMinifyEnabled = false
            signingConfig = signingConfigs.getByName("debug")
            versionNameSuffix = "-debug"
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
        freeCompilerArgs = freeCompilerArgs + listOf(
            "-opt-in=kotlin.RequiresOptIn"     // some composables needs @OptIn(ExperimentalComposeUiApi::class)
        )
    }

    buildFeatures {
        compose = true
        buildConfig = true
    }

    composeOptions {
        // Compose to Kotlin Compatibility Map: https://developer.android.com/jetpack/androidx/releases/compose-kotlin#kts
        kotlinCompilerExtensionVersion = "1.5.3"
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    // last version of Compose BOM: https://developer.android.com/jetpack/compose/bom/bom-mapping
    val composeBom = platform("androidx.compose:compose-bom:2023.10.01")
    implementation(composeBom)

    //NOTE: do not use version numbers for compose libraries, all versions will be taken from Compose BOM
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
    implementation("androidx.activity:activity-compose:1.8.0")
    implementation("androidx.navigation:navigation-compose:2.7.4")

    // https://github.com/ktorio/ktor/releases
    val ktorVersion = "2.3.5"
    implementation("io.ktor:ktor-client-okhttp:$ktorVersion")
    implementation("io.ktor:ktor-client-core:$ktorVersion")
    implementation("io.ktor:ktor-client-serialization:$ktorVersion")
    implementation("io.ktor:ktor-client-logging:$ktorVersion")
    implementation("io.ktor:ktor-serialization-kotlinx-json:$ktorVersion")
    implementation("io.ktor:ktor-client-content-negotiation:$ktorVersion")

    // https://github.com/Kotlin/kotlinx.serialization/releases
    val serializationVersion = "1.6.0"
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-core:$serializationVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:$serializationVersion")

    // https://github.com/InsertKoinIO/koin/tags
    val koinVersion = "3.5.0"
    implementation("io.insert-koin:koin-android:$koinVersion")
    implementation("io.insert-koin:koin-androidx-compose:$koinVersion")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    androidTestImplementation(composeBom)
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")

    //TODO: implementation("io.ktor:ktor-client-mock:$ktorVersion")
    //TODO: implementation("io.ktor:ktor-server-test-host:$ktorVersion")

}
