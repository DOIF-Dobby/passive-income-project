plugins {
  `kotlin-dsl`
}

repositories {
  mavenCentral()
}

gradlePlugin {
  plugins {
    create("optional-dependencies") {
      id = "org.mj.passiveincome.build.optional.optional-dependencies"
      implementationClass = "org.mj.passiveincome.build.optional.OptionalDependenciesPlugin"
      version = project.version
    }
  }
}
