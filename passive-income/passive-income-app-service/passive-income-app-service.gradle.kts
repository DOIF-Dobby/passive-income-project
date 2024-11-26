dependencies {
  implementation("org.springframework.boot:spring-boot-starter-data-jpa")
  implementation("org.springframework.boot:spring-boot-starter-web")
  implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
  implementation("org.jetbrains.kotlin:kotlin-reflect")
  runtimeOnly("com.h2database:h2")
  runtimeOnly("org.postgresql:postgresql")

  implementation(project(":passive-income:passive-income-core"))
  implementation(project(":passive-income:passive-income-util"))

  implementation(project(":passive-income:passive-income-domain-user"))
  implementation(project(":passive-income:passive-income-domain-stock"))

}
