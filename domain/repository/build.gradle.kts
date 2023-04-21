plugins {
    id("com.android.library")
    kotlin("android")
}

dependencies {
    implementation(project(Modules.commonJvm))
    implementation(project(Modules.commonEntity))
}