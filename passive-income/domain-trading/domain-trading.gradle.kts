dependencies {
  implementation("jakarta.persistence:jakarta.persistence-api")
  implementation("org.springframework.data:spring-data-jpa")

  implementation(project(":passive-income:system-core"))
  implementation(project(":passive-income:system-data-jpa"))

  implementation(project(":passive-income:domain-user"))
}

apply {
  plugin("java-test-fixtures")
}
