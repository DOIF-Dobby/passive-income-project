plugins {
  kotlin("jvm") version "1.9.25"
  kotlin("plugin.spring") version "1.9.25"
  kotlin("plugin.jpa") version "1.9.25"
  id("org.springframework.boot") version "3.4.0"
  id("io.spring.dependency-management") version "1.1.6"
  id("org.jlleitschuh.gradle.ktlint") version "12.1.1"
  id("org.mj.passiveincome.build.optional.optional-dependencies")
}

// 빌드 시 루트 프로젝트 jar 생성 되지 않게
tasks.jar {
  enabled = false
}
// 빌드 시 루트 프로젝트 bootJar 생성 되지 않게
tasks.bootJar {
  enabled = false
}

// 서브 모듈이지만 directory인 모듈들
val directoryModules = arrayOf(":passive-income")

val kotestVersion = "5.9.1"

allprojects {
  group = "org.mj.passiveincome"

  repositories {
    mavenCentral()
  }
}

subprojects {
  apply {
    plugin("org.jetbrains.kotlin.jvm")
    plugin("org.jetbrains.kotlin.plugin.jpa")
    plugin("org.jetbrains.kotlin.plugin.spring")
    plugin("io.spring.dependency-management")
    plugin("org.mj.passiveincome.build.optional.optional-dependencies")
  }

  kotlin {
    jvmToolchain(21)
    compilerOptions {
      freeCompilerArgs.addAll("-Xjsr305=strict")
    }
  }

  java {
    toolchain {
      languageVersion = JavaLanguageVersion.of(21)
    }
  }

  dependencyManagement {
    imports {
      mavenBom("org.springframework.boot:spring-boot-dependencies:3.4.0")
    }
  }

  dependencies {
    testImplementation("org.springframework.boot:spring-boot-starter-test") // 테스트 편의상
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    testImplementation("io.kotest:kotest-runner-junit5:$kotestVersion")
  }

  allOpen {
    annotation("jakarta.persistence.Entity")
    annotation("jakarta.persistence.MappedSuperclass")
    annotation("jakarta.persistence.Embeddable")
  }

  // app 모듈은 bootJar 생성 & plain jar은 생성하지 않는다.
  if (project.name.startsWith("app-")) {
    apply {
      plugin("org.springframework.boot")
    }

    tasks.jar {
      enabled = false
    }
  }

  // jar 생성
  tasks.jar {
    // 디렉토리 서브 모듈들은 jar를 생성하지 않는다.
    if (directoryModules.contains(project.path)) {
      enabled = false
    }
  }

  tasks.test {
    useJUnitPlatform()
  }
}
