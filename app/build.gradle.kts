plugins {
    id(Dependencies.Plugins.androidApplication)
    id(Dependencies.Plugins.kotlinAndroid)
    id(Dependencies.Plugins.kotlinKapt)
    id(Dependencies.Plugins.kotlinParcelize)
    id(Dependencies.Plugins.hiltPlugin)
    id(Dependencies.Plugins.safeArgsKotlin)
    id(Dependencies.Plugins.dependencyUpdates)  version Versions.Plugins.dependencyUpdates
}

android {
    compileSdk = ConfigData.compileSdk
    defaultConfig {
        applicationId = ConfigData.applicationId
        minSdk = ConfigData.minSdk
        targetSdk = ConfigData.targetSdk
        versionCode = ConfigData.versionCode
        versionName = ConfigData.versionName

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    buildFeatures {
        viewBinding = true
        dataBinding = true
    }
    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = ConfigData.javaVersion
        targetCompatibility = ConfigData.javaVersion
    }
    kotlinOptions {
        jvmTarget = ConfigData.jvmTarget
    }

    testOptions.unitTests.isIncludeAndroidResources = true

    packagingOptions {
        resources {
            excludes += listOf("**/attach_hotspot_windows.dll", "META-INF/licenses/**",
                "META-INF/AL2.0", "\"META-INF/LGPL2.1\"")
        }
    }
}

dependencies {
    //Android X
    implementation(Dependencies.AndroidX.coreKts)
    implementation(Dependencies.AndroidX.activityKtx)
    implementation((Dependencies.AndroidX).fragmentKtx)
    implementation(Dependencies.AndroidX.appCompat)
    implementation(Dependencies.AndroidX.constraintLayout)
    implementation(Dependencies.AndroidX.lifecycleViewModel)
    implementation(Dependencies.AndroidX.lifecycleLiveData)
    implementation(Dependencies.AndroidX.lifecycleJava8)
    implementation(Dependencies.AndroidX.navigationFragmentKtx)
    implementation(Dependencies.AndroidX.navigationUiKtx)
    implementation(Dependencies.Material.material)

    // Room components
    implementation(Dependencies.Room.room)
    kapt(Dependencies.Room.roomCompiler)
    androidTestImplementation(Dependencies.Room.roomTesting)

    //Hilt
    implementation(Dependencies.Hilt.hiltAndroid)
    kapt(Dependencies.Hilt.androidCompiler)
    kapt(Dependencies.Hilt.hiltCompiler)
    implementation(Dependencies.Hilt.lifecycleViewModel)
    androidTestImplementation(Dependencies.Hilt.androidTesting)
    kaptAndroidTest(Dependencies.Hilt.hiltCompiler)
    // For local unit tests
    testImplementation(Dependencies.Hilt.androidTesting)
    kaptTest(Dependencies.Hilt.hiltCompiler)

    //Live Event
    implementation(Dependencies.LiveEvent.liveEvent)

    // Testing
    debugImplementation(Dependencies.TestLibs.fragmentTesting)
    testImplementation(Dependencies.TestLibs.coreTesting)
    testImplementation(Dependencies.TestLibs.jUnitKtx)
    testImplementation(Dependencies.TestLibs.jUnit)
    testImplementation(Dependencies.TestLibs.kotlinCoroutines)
    implementation(Dependencies.TestLibs.navigation)
    androidTestImplementation(Dependencies.AndroidTestLibs.espressoCore)
    androidTestImplementation(Dependencies.AndroidTestLibs.jUnit)
    androidTestImplementation(Dependencies.AndroidTestLibs.mockitoCore)
    androidTestImplementation(Dependencies.AndroidTestLibs.dexmakerMockit)
    androidTestImplementation(Dependencies.AndroidTestLibs.espressoContrib)
    androidTestImplementation(Dependencies.AndroidTestLibs.coreTesting)
    androidTestImplementation(Dependencies.AndroidTestLibs.coroutinesTest)
}
kapt {
    correctErrorTypes = true
}