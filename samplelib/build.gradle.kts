plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("maven-publish")
}

android {
    namespace = "com.sevenpeakssoftware.samplelib"
    compileSdk = 34

    defaultConfig {
        minSdk = 23

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
}

publishing {
    publications {
        // Publication for the Release variant
        create<MavenPublication>("azure-artifact-release"){
            groupId = "com.sevenpeakssoftware.samplelib"
            artifactId = "samplelib"
            version = "1.0.0"
            artifact("${layout.buildDirectory.get().asFile}/outputs/aar/samplelib-release.aar")
        }

        // Publication for the Debug variant
        create<MavenPublication>("azure-artifact-debug"){
            groupId = "com.sevenpeakssoftware.samplelib"
            artifactId = "samplelib"
            version = "1.0.0"
            artifact("${layout.buildDirectory.get().asFile}/outputs/aar/samplelib-debug.aar")
        }
    }
    repositories {
        maven{
            name = "android-lib-demo"
            url = uri("https://pkgs.dev.azure.com/1993khiladi/_packaging/1993khiladi/maven/v1")
            credentials {
                username = project.findProperty("PERSONAL_AA_USER") as String? ?: System.getenv("PERSONAL_AA_USER")
                password = project.findProperty("PERSONAL_AA_API_KEY") as String? ?: System.getenv("PERSONAL_AA_API_KEY")
            }
        }
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}