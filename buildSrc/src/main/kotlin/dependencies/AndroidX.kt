package dependencies

import org.gradle.api.artifacts.dsl.DependencyHandler

fun DependencyHandler.androidX(){
    implementation("androidx.lifecycle:lifecycle-extensions:2.2.0")
    implementation("androidx.annotation:annotation:1.4.0")
    implementation("androidx.activity:activity-ktx:1.5.1")
    implementation("androidx.fragment:fragment-ktx:1.5.1")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
}