dependencies {
  implementation("org.springframework.security:spring-security-config")
  implementation("org.springframework.security:spring-security-web")
  implementation("jakarta.servlet:jakarta.servlet-api")
  implementation("com.fasterxml.jackson.core:jackson-databind")
  implementation("com.fasterxml.jackson.datatype:jackson-datatype-jdk8")
  implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310")
  implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
  implementation("com.fasterxml.jackson.module:jackson-module-parameter-names")

  implementation("io.jsonwebtoken:jjwt-api")
  runtimeOnly("io.jsonwebtoken:jjwt-impl")
  runtimeOnly("io.jsonwebtoken:jjwt-jackson")

  implementation(project(":passive-income:system-core"))
  implementation(project(":passive-income:system-security"))
  implementation(project(":passive-income:type-common"))
}
