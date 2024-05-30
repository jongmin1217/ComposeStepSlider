plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("maven-publish")
}


group = "com.bellminp.composestepslider"
version = "1.0.0"

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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
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

val myAttribute = Attribute.of("my.attribute.name", String::class.java)
val myUsage = Attribute.of("my.usage.attribute", Usage::class.java)

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

dependencies.attributesSchema{
    attribute(myAttribute)
    attribute(myUsage)
}

configurations {
    create("myConfiguration") {
        attributes {
            attribute(myAttribute, "my-value")
        }
        attributes {
            attribute(myUsage, project.objects.named(Usage::class.java, "my-value"))
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