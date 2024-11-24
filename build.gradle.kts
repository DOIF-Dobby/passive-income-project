plugins {
  kotlin("jvm") version "1.9.25"
  kotlin("plugin.spring") version "1.9.25"
  kotlin("plugin.jpa") version "1.9.25"
  id("io.spring.dependency-management") version "1.1.6"
}

// 빌드 시 루트 프로젝트 jar 생성 되지 않게
tasks.jar {
  enabled = false
}

// 서브 모듈이지만 directory인 모듈들
val directoryModules = arrayOf(
  ":passive-income"
)

kotlin {
  jvmToolchain(21)
}

dependencies {
  testImplementation(kotlin("test"))
}

tasks.test {
  useJUnitPlatform()
}

allprojects {
  group = "org.mj.passiveincome"
  version = "1.0-SNAPSHOT"

  repositories {
    mavenCentral()
  }
}

subprojects {
  apply {
    plugin("org.jetbrains.kotlin.jvm")
    plugin("io.spring.dependency-management")
  }

  // jar 생성
  tasks.jar {
    // 디렉토리 서브 모듈들은 jar를 생성하지 않는다.
    if (directoryModules.contains(project.path)) {
      enabled = false
    }
  }

  dependencyManagement {
    imports {
      mavenBom("org.springframework.boot:spring-boot-dependencies:3.4.0")
    }
  }

  dependencies {
    testImplementation("org.springframework.boot:spring-boot-starter-test")
  }

  tasks.test {
    useJUnitPlatform()
  }
}

allOpen {
  annotation("jakarta.persistence.Entity")
  annotation("jakarta.persistence.MappedSuperclass")
  annotation("jakarta.persistence.Embeddable")
}