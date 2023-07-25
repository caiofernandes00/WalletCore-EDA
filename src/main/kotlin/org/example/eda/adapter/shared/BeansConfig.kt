package org.example.eda.adapter.shared

import org.example.eda.adapter.controller.AccountController
import org.example.eda.adapter.controller.AppRouter.routes
import org.example.eda.adapter.repository.AccountRepositoryImpl
import org.example.eda.adapter.repository.ClientRepositoryImpl
import org.example.eda.adapter.repository.Config
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.support.GenericApplicationContext
import org.springframework.context.support.beans

val beans = beans {
    bean<AccountController>()
    bean(::routes)
    bean {
        Config.connect()
        Config.migrate()

        val clientRepositoryImpl = ClientRepositoryImpl()
        AccountRepositoryImpl(
            clientRepositoryImpl
        )
    }
}

class BeanConfig : ApplicationContextInitializer<GenericApplicationContext> {
    override fun initialize(applicationContext: GenericApplicationContext) =
        beans.initialize(applicationContext)
}