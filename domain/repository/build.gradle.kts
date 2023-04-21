plugins {
    id("com.android.library")
    kotlin("android")
}

apply {
    from("$rootDir/android-common-build.gradle")
}

dependencies {
    implementation(project(Modules.commonJvm))
    implementation(project(Modules.commonEntity))
}