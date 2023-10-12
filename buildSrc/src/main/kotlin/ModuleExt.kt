import org.gradle.api.provider.Provider
import org.gradle.kotlin.dsl.kotlin
import org.gradle.plugin.use.PluginDependenciesSpec
import org.gradle.plugin.use.PluginDependency
import org.gradle.plugin.use.PluginDependencySpec

fun PluginDependenciesSpec.id(provider: Provider<PluginDependency>): PluginDependencySpec {
    return id(provider.get().pluginId)
}

fun PluginDependenciesSpec.kotlin(provider: Provider<PluginDependency>): PluginDependencySpec {
    return kotlin(provider.get().pluginId)
}