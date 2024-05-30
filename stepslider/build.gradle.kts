plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("maven-publish")
}

android {
    namespace = "com.bellminp.stepslider"
    compileSdk = 34

    defaultConfig {
        minSdk = 26

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
}

val javadocJar = tasks.register("javadocJar", Jar::class.java) {
    archiveClassifier.set("javadoc")
}

publishing{
    publications{
        withType<MavenPublication>{
            artifact(javadocJar)
            pom {
                name.set("ComposeStepSlider")
                description.set("Compose StepSlider")

                url.set("https://github.com/jongmin1217/ComposeStepSlider")
                issueManagement {
                    system.set("Github")
                    url.set("https://github.com/jongmin1217/ComposeStepSlider/issues")
                }
                scm {
                    connection.set("https://github.com/jongmin1217/ComposeStepSlider.git")
                    url.set("https://github.com/jongmin1217/ComposeStepSlider")
                }
                developers {
                    developer {
                        name.set("bellminp")
                        email.set("syj408886@gmail.com")
                    }
                }
            }
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    implementation(libs.bundles.compose)
}