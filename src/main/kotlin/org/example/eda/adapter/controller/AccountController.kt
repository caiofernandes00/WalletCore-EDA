package org.example.eda.adapter.controller

import org.example.eda.usecase.createAccount.CreateAccountInputDTO
import org.example.eda.usecase.createAccount.CreateAccountUseCase
import org.springframework.web.servlet.function.ServerRequest
import org.springframework.web.servlet.function.ServerResponse

class AccountController(
    private val createAccountUseCase: CreateAccountUseCase
) {

    fun createAccount(
        request: ServerRequest
    ) = ServerResponse.created(request.uri())
        .body(
            createAccountUseCase.execute(
                request.body(CreateAccountInputDTO::class.java)
            )
        )
}