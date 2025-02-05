plugins {
  id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}

include(
  "passive-income",
  "passive-income:app-api",
  "passive-income:app-kis",
  "passive-income:domain-common",
  "passive-income:domain-credentials",
  "passive-income:domain-finance",
  "passive-income:domain-portfolio",
  "passive-income:domain-stock",
  "passive-income:domain-trading",
  "passive-income:domain-user",
  "passive-income:system-core",
  "passive-income:system-data",
  "passive-income:system-data-jpa",
  "passive-income:system-data-redis",
  "passive-income:system-kafka",
  "passive-income:system-security",
  "passive-income:system-security-oauth2",
  "passive-income:system-web",
  "passive-income:type-common",
)

rootProject.name = "passive-income-project"

// 하위 모듈들 build.gradle.kts 파일을 각 모듈 이름으로 변경
rootProject.children.forEach(::setUpChildProject)

fun setUpChildProject(project: ProjectDescriptor) {
  val buildFileName = "${project.name}.gradle.kts"
  project.buildFileName = buildFileName
  assert(project.buildFile.isFile) { "File named $buildFileName must exist." }
  project.children.forEach(::setUpChildProject)
}
