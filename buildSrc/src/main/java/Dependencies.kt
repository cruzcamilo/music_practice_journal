object Dependencies {
    
    object Plugins {
        val androidApplication by lazy { "com.android.application" }
        val kotlinAndroid by lazy { "kotlin-android"  }
        val kotlinKapt by lazy { "kotlin-kapt" }
        val kotlinParcelize by lazy { "kotlin-parcelize" }
        val hiltPlugin by lazy { "dagger.hilt.android.plugin" }
        val safeArgsKotlin by lazy { "androidx.navigation.safeargs" }
        val dependencyUpdates by lazy { "com.github.ben-manes.versions" }
    }

    object AndroidX {
        val coreKts by lazy { "androidx.core:core-ktx:${Versions.AndroidX.coreKtx}" }
        val activityKtx by lazy { "androidx.activity:activity-ktx:${Versions.AndroidX.activityKtx}" }
        val fragmentKtx by lazy { "androidx.fragment:fragment-ktx:${Versions.AndroidX.fragmentKtx}" }
        val appCompat by lazy { "androidx.appcompat:appcompat:${Versions.AndroidX.appCompat}" }
        val constraintLayout by lazy { "androidx.constraintlayout:constraintlayout:${Versions.AndroidX.constraintLayout}" }
        val lifecycleViewModel by lazy { "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.AndroidX.lifecycleVersion}" }
        val lifecycleLiveData by lazy {"androidx.lifecycle:lifecycle-livedata-ktx:${Versions.AndroidX.lifecycleVersion}"}
        val lifecycleJava8 by lazy { "androidx.navigation:navigation-fragment-ktx:${Versions.AndroidX.navigation}"}
        val navigationFragmentKtx by lazy { "androidx.navigation:navigation-fragment-ktx:${Versions.AndroidX.navigation}"}
        val navigationUiKtx by lazy { "androidx.navigation:navigation-ui-ktx:${Versions.AndroidX.navigation}"}
    }

    object Material {
        val material by lazy { "com.google.android.material:material:${Versions.Material.material}" }
    }

    object Room {
        val room by lazy {"androidx.room:room-ktx:${Versions.Room.roomVersion}"}
        val roomCompiler by lazy { "androidx.room:room-compiler:${Versions.Room.roomVersion}" }
        val roomTesting by lazy { "androidx.room:room-testing:${Versions.Room.roomTesting}" }
    }

    object Hilt {
        val hiltAndroid by lazy { "com.google.dagger:hilt-android:${Versions.Hilt.hiltVersion}" }
        val androidCompiler by lazy { "com.google.dagger:hilt-android-compiler:${Versions.Hilt.hiltVersion}" }
        val lifecycleViewModel by lazy { "androidx.hilt:hilt-lifecycle-viewmodel:${Versions.Hilt.hiltLifecycle}" }
        val hiltCompiler by lazy { "com.google.dagger:hilt-compiler:${Versions.Hilt.hiltVersion}" }
        val androidTesting by lazy { "com.google.dagger:hilt-android-testing:${Versions.Hilt.hiltVersion}" }
    }

    object LiveEvent {
        val liveEvent by lazy { "com.github.hadilq:live-event:${Versions.LiveEvent.hadilqLive}" }
    }

    object TestLibs {
        val fragmentTesting by lazy { "androidx.fragment:fragment-testing:${Versions.TestLibs.fragmentVersion}" }
        val coreTesting by lazy { "androidx.arch.core:core-testing:${Versions.TestLibs.coreTestingVersion}" }
        val jUnitKtx by lazy { "androidx.test.ext:junit-ktx:${Versions.TestLibs.jUnitKtx}" }
        val jUnit by lazy { "junit:junit:${Versions.TestLibs.junitVersion}" }
        val kotlinCoroutines by lazy { "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.TestLibs.coroutinesVersion}" }
        val navigation by lazy { "androidx.navigation:navigation-testing:${Versions.TestLibs.navigation}" }
    }

    object AndroidTestLibs {
        val espressoCore by lazy { "androidx.test.espresso:espresso-core:${Versions.AndroidTestLibs.espresso}" }
        val jUnit by lazy { "androidx.test.ext:junit:${Versions.AndroidTestLibs.jUnit}" }
        val mockitoCore by lazy { "org.mockito:mockito-core:${Versions.AndroidTestLibs.mockito}" }
        val dexmakerMockit by lazy {"com.linkedin.dexmaker:dexmaker-mockito:${Versions.AndroidTestLibs.dexMaker}"  }
        val espressoContrib by lazy { "androidx.test.espresso:espresso-contrib:${Versions.AndroidTestLibs.espresso}" }
        val coreTesting by lazy { "androidx.arch.core:core-testing:${Versions.TestLibs.coreTestingVersion}" }
        val coroutinesTest by lazy { "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.TestLibs.coroutinesVersion}" }
    }

}