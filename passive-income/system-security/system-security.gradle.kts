dependencies {
  implementation("io.jsonwebtoken:jjwt-api")
  runtimeOnly("io.jsonwebtoken:jjwt-impl")
  runtimeOnly("io.jsonwebtoken:jjwt-jackson")

  implementation(project(":passive-income:system-core"))
}
