plugins {
    id ("com.android.application")
    id ("kotlin-android")
}

android {
    compileSdk = 31

    defaultConfig {
        applicationId = "com.gmail.mukatdisovilyas.easycalculator"
        minSdk = 21
        targetSdk = 31
        versionCode = 1
        versionName = "1.0"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles (getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
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

    implementation ("androidx.core:core-ktx:1.7.0")
    implementation ("androidx.appcompat:appcompat:1.4.0")
    implementation ("com.google.android.material:material:1.4.0")
    implementation ("androidx.constraintlayout:constraintlayout:2.1.2")
    implementation (files("libs/MathParser.org-mXparser-v.4.4.0-jdk11.jar"))
    testImplementation ("junit:junit:4.13.2")
    androidTestImplementation ("androidx.test.ext:junit:1.1.3")
    androidTestImplementation ("androidx.test.espresso:espresso-core:3.4.0")

    implementation ("com.azeesoft.lib.colorpicker:colorpicker:1.0.8@aar")

    implementation ("com.github.PhilJay:MPAndroidChart:v3.1.0")
    implementation ("androidx.core:core-ktx:1.7.0")
}
