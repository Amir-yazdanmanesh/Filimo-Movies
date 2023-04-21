
plugins {
    id("com.android.library")
    kotlin("android")
}

apply {
    from("$rootDir/android-feature-build.gradle")
}

dependencies {
    implementation (Libs.shimmer)
    testImplementation(Tests.mockito)
    testImplementation(Tests.mockk)
    testImplementation(Tests.coroutines_test)
}
