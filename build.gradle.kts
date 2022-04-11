// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        mavenCentral()
    }

    dependencies {
        classpath("com.android.tools.build:gradle:7.0.3")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.Plugins.kotlinGradle}")
        classpath("com.google.dagger:hilt-android-gradle-plugin:${Versions.Plugins.hiltAndroid}")
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:${Versions.Plugins.safeArgsGradle}")

        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
