dependencies {
  implementation("org.springframework.boot:spring-boot-starter-web")
  implementation("org.springframework.boot:spring-boot-starter-data-jpa")
  implementation("org.springframework.boot:spring-boot-starter-data-redis")
  implementation("org.springframework.kafka:spring-kafka")
  implementation("org.springframework.boot:spring-boot-starter-websocket")


  implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
  implementation("org.jetbrains.kotlin:kotlin-reflect")
  runtimeOnly("com.h2database:h2")
  runtimeOnly("org.postgresql:postgresql")

  implementation(project(":passive-income:system-core"))
  implementation(project(":passive-income:system-data-jpa"))
  implementation(project(":passive-income:system-data-redis"))
  implementation(project(":passive-income:system-kafka"))

  implementation(project(":passive-income:domain-common"))
  implementation(project(":passive-income:domain-credentials"))
  implementation(project(":passive-income:domain-user"))
  implementation(project(":passive-income:domain-finance"))
  implementation(project(":passive-income:domain-portfolio"))
  implementation(project(":passive-income:domain-stock"))
  implementation(project(":passive-income:domain-trading"))

  implementation(project(":passive-income:type-common"))
}
