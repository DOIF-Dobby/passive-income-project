dependencies {
  implementation("org.springframework:spring-context")
  implementation("org.springframework:spring-web")
  implementation("com.fasterxml.jackson.core:jackson-databind")
  implementation("com.fasterxml.jackson.datatype:jackson-datatype-jdk8")
  implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310")
  implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
  implementation("com.fasterxml.jackson.module:jackson-module-parameter-names")

  implementation(project(":passive-income:system-core"))
}
