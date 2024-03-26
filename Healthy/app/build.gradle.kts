plugins {
    id("com.android.application")
}

android {
    namespace = "eus.aimar.healthy"
    compileSdk = 34

    defaultConfig {
        applicationId = "eus.aimar.healthy"
        minSdk = 26
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    buildFeatures{
        viewBinding=true;
        dataBinding =true;
    }
}

dependencies {



    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.navigation:navigation-fragment:2.7.3")
    implementation("androidx.navigation:navigation-ui:2.7.3")
    implementation(platform("org.jetbrains.kotlin:kotlin-bom:1.8.0"))
    implementation("com.google.firebase:firebase-crashlytics-buildtools:2.9.9")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    //Room dependencies
    implementation ("androidx.room:room-runtime:2.5.2")
    annotationProcessor ("androidx.room:room-compiler:2.5.2")

    //Okhttp dependency
    implementation("com.squareup.okhttp3:okhttp:4.11.0")

    //Bcrypt depenency
    //implementation("at.favre.lib:bcrypt:0.8.1");

    //Guava dependency
    implementation("com.google.guava:guava:32.1.3-jre")
}