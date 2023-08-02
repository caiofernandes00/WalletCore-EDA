package org.example.eda.adapter.controller

import org.example.eda.usecase.createTransaction.CreateTransactionInputDTO
import org.example.eda.usecase.createTransaction.CreateTransactionUseCase
import org.springframework.web.servlet.function.ServerRequest
import org.springframework.web.servlet.function.ServerResponse

class TransactionController(
    private val createTransactionUseCase: CreateTransactionUseCase
) {

    suspend fun createTransaction(
        request: ServerRequest
    ) = ServerResponse.created(request.uri())
        .body(
            createTransactionUseCase.execute(
                request.body(CreateTransactionInputDTO::class.java)
            )
        )

}