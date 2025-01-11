dependencies {
  implementation("jakarta.persistence:jakarta.persistence-api")
  implementation("org.springframework.data:spring-data-jpa")

  implementation(project(":passive-income:system-core"))
  implementation(project(":passive-income:system-data-jpa"))

  implementation(project(":passive-income:domain-common"))

  implementation(project(":passive-income:type-common"))
}

apply {
  plugin("java-test-fixtures")
}
