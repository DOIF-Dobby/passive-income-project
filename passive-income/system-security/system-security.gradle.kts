dependencies {
  implementation("org.springframework.security:spring-security-config")

  implementation("io.jsonwebtoken:jjwt-api")
  runtimeOnly("io.jsonwebtoken:jjwt-impl")
  runtimeOnly("io.jsonwebtoken:jjwt-jackson")

  implementation(project(":passive-income:system-core"))
  implementation(project(":passive-income:type-common"))
}
