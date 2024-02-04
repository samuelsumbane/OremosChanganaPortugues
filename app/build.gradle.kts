plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.samuel.oremoschangana"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.samuel.oremoschangana"
        minSdk = 21
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")
    implementation("androidx.activity:activity-compose:1.8.2")
    implementation(platform("androidx.compose:compose-bom:2023.08.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.08.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")


    // ============= navigation ==============
    val nav_version = "2.5.3"

    // Java language implementation
    implementation("androidx.navigation:navigation-fragment:$nav_version")
    implementation("androidx.navigation:navigation-ui:$nav_version")

    // Kotlin
    implementation("androidx.navigation:navigation-fragment-ktx:$nav_version")
    implementation("androidx.navigation:navigation-ui-ktx:$nav_version")

    // Feature module Support
    implementation("androidx.navigation:navigation-dynamic-features-fragment:$nav_version")

    // Testing Navigation
    androidTestImplementation("androidx.navigation:navigation-testing:$nav_version")

    // Jetpack Compose Integration
    implementation("androidx.navigation:navigation-compose:$nav_version")

    // ============ room data ================

//    val roomVersion = "2.4.1"
//
//    implementation("androidx.room:room-runtime:$roomVersion")
//    annotationProcessor("androidx.room:room-compiler:$roomVersion")
//
//    // To use Kotlin annotation processing tool (kapt)
//    kapt("androidx.room:room-compiler:$roomVersion")
//    // To use Kotlin Symbolic Processing (KSP)
//    ksp("androidx.room:room-compiler:$roomVersion")
//
//    // optional - Kotlin Extensions and Coroutines support for Room
//    implementation("androidx.room:room-ktx:$roomVersion")
//
//    // optional - RxJava2 support for Room
//    implementation("androidx.room:room-rxjava2:$roomVersion")
//
//    // optional - RxJava3 support for Room
//    implementation("androidx.room:room-rxjava3:$roomVersion")
//
//    // optional - Guava support for Room, including Optional and ListenableFuture
//    implementation("androidx.room:room-guava:$roomVersion")
//
//    // optional - Test helpers
//    testImplementation("androidx.room:room-testing:$roomVersion")
//
//    // optional - Paging 3 Integration
//    implementation("androidx.room:room-paging:2.4.1")
}