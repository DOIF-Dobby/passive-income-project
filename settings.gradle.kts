plugins {
  id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}

include("passive-income")
include("passive-income:passive-income-core")
include("passive-income:passive-income-util")
include("passive-income:passive-income-app-service")

rootProject.name = "passive-income-project"

// 하위 모듈들 build.gradle.kts 파일을 각 모듈 이름으로 변경
rootProject.children.forEach(::setUpChildProject)

fun setUpChildProject(project: ProjectDescriptor) {
  val buildFileName = "${project.name}.gradle.kts"
  project.buildFileName = buildFileName
  assert(project.buildFile.isFile) { "File named $buildFileName must exist." }
  project.children.forEach(::setUpChildProject)
}