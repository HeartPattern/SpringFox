rootProject.name = "SpringFox"

val modules = listOf(
    "core",
    "brigadier",
    "starter",
    "common"
)

fun addModule(name: String, dir: String = "modules/$name"){
    include(name)
    findProject(":$name")!!.apply{
        projectDir = File(rootProject.projectDir, dir)
        buildFileName = "$name.gradle.kts"
    }
}

for(module in modules){
    addModule(module)
}

addModule("test", "test")