package tech.volkov.raosexample.api

import io.ktor.application.call
import io.ktor.http.ContentType
import io.ktor.response.respondText
import io.ktor.routing.get
import io.ktor.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import tech.volkov.raos.service.RAOSService
import tech.volkov.raos.service.impl.RAOSServiceImpl

private val raosService: RAOSService = RAOSServiceImpl()

fun main(args: Array<String>) {
    embeddedServer(Netty, 8090) {
        routing {
            get("/start") {
                val queryParameters = call.request.queryParameters
                val contractAddress = queryParameters["contractAddress"]
                val privateKey = queryParameters["privateKey"]

                if (contractAddress != null && privateKey != null) {
                    val startDate = raosService.getRentStartDate(contractAddress, privateKey)
                    call.respondText(startDate.toString(), ContentType.Text.Html)
                }
                call.respondText("Failed to get start date", ContentType.Text.Html)
            }
        }
    }.start(wait = true)
}
