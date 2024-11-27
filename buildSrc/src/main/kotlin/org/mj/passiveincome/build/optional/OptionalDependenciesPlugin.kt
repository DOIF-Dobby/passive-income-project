package org.mj.passiveincome.build.optional

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.Configuration
import org.gradle.api.plugins.JavaPlugin
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.api.tasks.SourceSetContainer

class OptionalDependenciesPlugin : Plugin<Project> {
  override fun apply(project: Project) {
    val optional: Configuration = project.configurations.create(OPTIONAL_CONFIGURATION_NAME).apply {
      isCanBeConsumed = false
      isCanBeResolved = false
    }

    project.plugins.withType(JavaPlugin::class.java) {
      val sourceSets: SourceSetContainer = project.extensions.getByType(JavaPluginExtension::class.java).sourceSets


      sourceSets.run {
        this.all {
          project.configurations.getByName(this.compileClasspathConfigurationName).extendsFrom(optional)
          project.configurations.getByName(this.runtimeClasspathConfigurationName).extendsFrom(optional)
        }

      }
    }
  }

  companion object {
    const val OPTIONAL_CONFIGURATION_NAME = "optional"
  }
}
