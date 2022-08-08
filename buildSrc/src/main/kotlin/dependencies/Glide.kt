package dependencies

import org.gradle.api.artifacts.dsl.DependencyHandler

fun DependencyHandler.glide(){
    implementation("com.github.bumptech.glide:glide:4.13.2")
    implementation("jp.wasabeef:glide-transformations:4.3.0")
    implementation("jp.co.cyberagent.android:gpuimage:2.1.0")
    kapt("com.github.bumptech.glide:compiler:4.13.0")
}
