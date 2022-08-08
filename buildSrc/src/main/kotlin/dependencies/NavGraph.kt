package dependencies

import org.gradle.api.artifacts.dsl.DependencyHandler

fun DependencyHandler.navGraph(){
    implementation("androidx.navigation:navigation-fragment-ktx:2.5.1")
    implementation("androidx.navigation:navigation-ui-ktx:2.5.1")
}