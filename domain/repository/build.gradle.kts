plugins {
    id("java-library")
    id("org.jetbrains.kotlin.jvm")
}

dependencies {
    implementation(project(Modules.commonJvm))
    implementation(project(Modules.commonEntity))
}
java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}