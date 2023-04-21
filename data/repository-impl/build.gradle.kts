plugins {
    id("com.android.library")
}

apply {
    from("$rootDir/android-common-build.gradle")
}

dependencies {

    implementation(project(Modules.network))
    implementation(project(Modules.commonJvm))
    implementation(project(Modules.commonEntity))
    implementation(project(Modules.repository))
    implementation(Libs.retrofit)
    implementation(Libs.dagger)
    implementation(Libs.dagger_kapt)
    implementation(Libs.kotlin_reflect)
    implementation(Libs.gson_converter)
    testImplementation(Tests.coroutines_test)
    testImplementation(Tests.mockk)
    testImplementation(Tests.mockito)

}