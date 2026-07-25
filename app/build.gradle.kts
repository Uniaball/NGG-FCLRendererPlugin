@file:Suppress("UnstableApiUsage")

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.uniaball.plugin.nggu"
    compileSdk = 34

    buildFeatures {
        buildConfig = true
    }

    defaultConfig {
        applicationId = "com.uniaball.plugin.nggu"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "Release 0.0.1"

        manifestPlaceholders["des"] = "Krypton Wrapper-U (OpenGL 3.1+)"
        manifestPlaceholders["renderer"] = "NGGL4ES:libng_gl4es.so:libEGL.so"
        manifestPlaceholders["boatEnv"] = mutableMapOf<String, String>().apply {
            put("LIBGL_USE_MC_COLOR", "1")
            put("LIBGL_GL", "31")
            put("LIBGL_ES", "3")
            put("LIBGL_NORMALIZE", "1")
            put("LIBGL_NOERROR", "1")
        }.run {
            var env = ""
            forEach { (key, value) ->
                env += "$key=$value:"
            }
            env.dropLast(1)
        }
        manifestPlaceholders["pojavEnv"] =
            manifestPlaceholders["boatEnv"] as String +
                    (mutableMapOf<String, String>().apply {
                        put("POJAV_RENDERER", "opengles3")
                    }.run {
                        var env = ":"
                        forEach { (key, value) ->
                            env += "$key=$value:"
                        }
                        env.dropLast(1)
                    })

        buildConfigField("boolean", "useANGLE", "false")
    }

    signingConfigs {
        if (System.getenv("KEYSTORE_FILE") != null) {
            create("release") {
                storeFile = file(System.getenv("KEYSTORE_FILE"))
                storePassword = System.getenv("KEYSTORE_PASSWORD")
                keyAlias = System.getenv("KEY_ALIAS")
                keyPassword = System.getenv("KEY_PASSWORD")
            }
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            signingConfig = signingConfigs.findByName("release")
        }
        configureEach {
            resValue("string", "app_name", "Krypton Wrapper-U")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation("com.squareup.okhttp3:okhttp:4.12.0")
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.activity:activity-ktx:1.9.3")
    implementation("com.google.android.material:material:1.12.0")
    implementation("org.jetbrains.kotlin:kotlin-stdlib:2.0.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")
    implementation("io.noties.markwon:core:4.6.2")
    implementation("io.noties.markwon:ext-strikethrough:4.6.2")
    implementation(project(":NGG"))
}
