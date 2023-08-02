package org.example.eda.adapter.controller

import org.springframework.web.servlet.function.router


private const val BASE_API = "/api"

fun routes(accountController: AccountController) = router {
    BASE_API.nest {
        "/account".nest {
            POST(accountController::createAccount)
        }
    }
}
