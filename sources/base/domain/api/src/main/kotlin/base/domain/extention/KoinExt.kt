package base.domain.extention

import base.domain.startup.AppInitAction
import org.koin.core.definition.Definition
import org.koin.core.module.Module
import org.koin.core.qualifier.TypeQualifier
import org.koin.dsl.bind
import kotlin.reflect.KClass

inline fun <reified T : AppInitAction> Module.bindAppInitAction(
    noinline definition: Definition<T>,
) {
    factory(
        qualifier = T::class.qualifier(),
        definition = definition,
    ) bind AppInitAction::class
}

fun KClass<*>.qualifier() = TypeQualifier(this)