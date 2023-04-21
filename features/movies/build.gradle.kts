plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
}

apply {
    from("$rootDir/android-feature-build.gradle")
}

dependencies {
    testImplementation(Tests.coroutines_test)
    testImplementation(Tests.mockk)
    testImplementation(Tests.mockito)
    implementation(Libs.shimmer)
}