plugins {
    id("com.android.application")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.application1"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.example.application1"
        minSdk = 24
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
}

dependencies {
//    implementation ("com.google.firebase:firebase-bom:32.5.0")
    implementation(platform("com.google.firebase:firebase-bom:32.5.0"))
//    implementation ("com.google.firebase:firebase-analytics")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.5.0")
    implementation ("com.github.bumptech.glide:glide:3.7.0")
//    implementation( "com.firebase:firebase-client-android:2.3.1"}
//    1
//2
    implementation ("com.lorentzos.swipecards:library:1.0.9")
    implementation("com.firebase:firebase-client-android:2.5.2+")
//    implementation ("com.firebase:firebase-ui:3.1.0")
    implementation ("com.firebaseui:firebase-ui-auth:7.2.0")
    implementation("com.google.firebase:firebase-database")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("com.google.firebase:firebase-auth")
    implementation("com.google.android.gms:play-services-auth:20.7.0")
//    implementation ("com.android.support:support-v4:28.0.0")
    implementation ("de.hdodenhof:circleimageview:3.1.0")

//    implementation("com.google.firebase:firebase-firestore")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}