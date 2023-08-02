package org.example.eda.adapter.controller

import org.example.eda.usecase.createClient.CreateClientInputDTO
import org.example.eda.usecase.createClient.CreateClientUseCase
import org.springframework.web.servlet.function.ServerRequest
import org.springframework.web.servlet.function.ServerResponse

class ClientController(
    private val createClientUseCase: CreateClientUseCase
) {

    fun createClient(
        request: ServerRequest
    ) = ServerResponse.created(request.uri())
        .body(
            createClientUseCase.execute(
                request.body(CreateClientInputDTO::class.java)
            )
        )

}