package feature.user.di

import base.domain.extention.factoryCastOf
import base.domain.extention.singleCalsOf
import feature.user.data.repository.user.UserRepositoryImpl
import feature.user.domain.repository.UserRepository
import feature.user.domain.usecase.DeleteUserUseCase
import feature.user.domain.usecase.DeleteUserUseCaseImpl
import feature.user.domain.usecase.GetUserUseCase
import feature.user.domain.usecase.GetUserUseCaseImpl
import feature.user.domain.usecase.GetUsersUseCase
import feature.user.domain.usecase.GetUsersUseCaseImpl
import feature.user.domain.usecase.UpdateUserUseCase
import feature.user.domain.usecase.UpdateUserUseCaseImpl
import feature.user.presentation.UserProcessor
import feature.user.presentation.UserPublisher
import feature.user.presentation.UserReducer
import feature.user.presentation.UserViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.Module
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

fun featureUserModule(): Module = module {
    viewModelOf(::UserViewModel)
    factoryOf(::UserProcessor)
    factoryOf(::UserPublisher)
    factoryOf(::UserReducer)
    factoryCastOf(::DeleteUserUseCaseImpl, DeleteUserUseCase::class)
    factoryCastOf(::GetUsersUseCaseImpl, GetUsersUseCase::class)
    factoryCastOf(::GetUserUseCaseImpl, GetUserUseCase::class)
    factoryCastOf(::UpdateUserUseCaseImpl, UpdateUserUseCase::class)
    singleCalsOf(::UserRepositoryImpl, UserRepository::class)
}