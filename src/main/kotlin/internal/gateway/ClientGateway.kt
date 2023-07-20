package internal.gateway

import internal.entity.Client

interface ClientGateway {
    fun getById(id: String): Client?
    fun save(client: Client)
}