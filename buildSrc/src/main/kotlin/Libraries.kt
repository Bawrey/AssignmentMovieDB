import org.gradle.api.artifacts.dsl.DependencyHandler
import dependencies.*
fun DependencyHandler.libraries(){
    androidCore()
    paging()
    androidX()
    coroutine()
    daggerHilt()
    gander()
    glide()
    gson()
    materialDesign()
    navGraph()
    okHttp()
    recycleView()
    retrofit()
    testUnit()
    vmLifecycle()
    youtubePlayer()
}