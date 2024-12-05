dependencies {
  implementation("org.springframework.boot:spring-boot-starter-data-jpa")
  implementation("org.springframework.boot:spring-boot-starter-web")
  implementation("org.springframework.boot:spring-boot-starter-data-redis")
  implementation("org.springframework.boot:spring-boot-starter-validation")

  implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
  implementation("org.jetbrains.kotlin:kotlin-reflect")
  runtimeOnly("com.h2database:h2")
  runtimeOnly("org.postgresql:postgresql")

  implementation(project(":passive-income:system-core"))
  implementation(project(":passive-income:system-data-jpa"))
  implementation(project(":passive-income:system-data-redis"))
  implementation(project(":passive-income:system-web"))

  implementation(project(":passive-income:domain-common"))
  implementation(project(":passive-income:domain-credentials"))
  implementation(project(":passive-income:domain-user"))
  implementation(project(":passive-income:domain-stock"))
  implementation(project(":passive-income:domain-finance"))
  implementation(project(":passive-income:domain-trading"))


}
