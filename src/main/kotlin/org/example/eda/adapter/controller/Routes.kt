package org.example.eda.adapter.controller

import org.springframework.web.servlet.function.RouterFunctionDsl
import org.springframework.web.servlet.function.router

object AppRouter {
    private const val API = "/api"

    private fun baseRouter(fn: RouterFunctionDsl.() -> Unit) = router {
        API.nest {
            fn()
        }
    }

    private fun accountRouter() = baseRouter {
        "/account".nest {
            POST(AccountController::createAccount)
        }
    }

    fun routes() = baseRouter {
        accountRouter()
    }
}