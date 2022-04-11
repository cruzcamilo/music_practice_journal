import org.gradle.api.JavaVersion

object ConfigData {
    private const val majorVersion = 1
    private const val minorVersion = 0
    private const val patchVersion = 0

    const val compileSdk = 31
    const val applicationId = "com.example.musicpracticejournal"
    const val minSdk = 21
    const val targetSdk = 30
    const val versionCode = 1
    const val versionName = "$majorVersion.$minorVersion.$patchVersion"
    const val jvmTarget = "1.8"

    val javaVersion = JavaVersion.VERSION_1_8
}