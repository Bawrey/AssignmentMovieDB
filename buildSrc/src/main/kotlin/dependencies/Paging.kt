package dependencies

import org.gradle.api.artifacts.dsl.DependencyHandler

fun DependencyHandler.paging(){
    implementation("androidx.paging:paging-runtime-ktx:3.1.1")
}