package tech.volkov.raosexample.api

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import io.ktor.application.ApplicationCall
import io.ktor.application.call
import io.ktor.http.ContentType
import io.ktor.response.respondText
import io.ktor.routing.get
import io.ktor.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import org.web3j.tuples.generated.Tuple2
import org.web3j.tuples.generated.Tuple4
import tech.volkov.raos.service.RAOSService
import tech.volkov.raos.service.impl.RAOSServiceImpl
import java.math.BigInteger
import java.sql.Timestamp
import java.util.Date

private val raosService: RAOSService = RAOSServiceImpl()
private val mapper = jacksonObjectMapper()

fun main() {
    embeddedServer(Netty, 8090) {
        routing {
            get("/start-date") {
                getContractRequestData(call).apply {
                    if (contractAddress != null && privateKey != null) {
                        val startDate = raosService.getRentStartDate(contractAddress, privateKey)
                        if (startDate != null) {
                            call.respondText(getDateDtoByDate(startDate), ContentType.Application.Json)
                        }
                    } else {
                        call.respondText("Failed to get start date", ContentType.Application.Json)
                    }
                }
            }

            get("/end-date") {
                getContractRequestData(call).apply {
                    if (contractAddress != null && privateKey != null) {
                        val endDate = raosService.getRentEndDate(contractAddress, privateKey)
                        if (endDate != null) {
                            call.respondText(getDateDtoByDate(endDate), ContentType.Application.Json)
                        }
                    } else {
                        call.respondText("Failed to get end date", ContentType.Text.Html)
                    }
                }
            }

            get("/time-left") {
                getContractRequestData(call).apply {
                    if (contractAddress != null && privateKey != null) {
                        val timeLeft = raosService.getTimeLeft(contractAddress, privateKey)
                        call.respondText(timeLeft.toString(), ContentType.Text.Html)
                    } else {
                        call.respondText("Failed to get time left", ContentType.Text.Html)
                    }
                }
            }

            get("/landlord") {
                getContractRequestData(call).apply {
                    if (contractAddress != null && privateKey != null) {
                        val landlord = raosService.getLandlord(contractAddress, privateKey)
                        if (landlord != null) {
                            call.respondText(getPersonData(landlord), ContentType.Application.Json)
                        }
                    } else {
                        call.respondText("Failed to get landlord", ContentType.Text.Html)
                    }
                }
            }

            get("/tenant") {
                getContractRequestData(call).apply {
                    if (contractAddress != null && privateKey != null) {
                        val tenant = raosService.getTenant(contractAddress, privateKey)
                        if (tenant != null) {
                            call.respondText(getPersonData(tenant), ContentType.Application.Json)
                        }
                    } else {
                        call.respondText("Failed to get tenant", ContentType.Text.Html)
                    }
                }
            }

            get("/apartment") {
                getContractRequestData(call).apply {
                    if (contractAddress != null && privateKey != null) {
                        val apartment = raosService.getApartment(contractAddress, privateKey)
                        if (apartment != null) {
                            call.respondText(getApartment(apartment), ContentType.Application.Json)
                        }
                    } else {
                        call.respondText("Failed to get apartment", ContentType.Text.Html)
                    }
                }
            }

            get("/deal") {
                getContractRequestData(call).apply {
                    if (contractAddress != null && privateKey != null) {
                        val deal = raosService.getDeal(contractAddress, privateKey)
                        if (deal != null) {
                            call.respondText(getDeal(deal), ContentType.Application.Json)
                        }
                    } else {
                        call.respondText("Failed to get deal", ContentType.Text.Html)
                    }
                }
            }
        }
    }.start(wait = true)
}

private fun getContractRequestData(applicationCall: ApplicationCall): ContractRequestData {
    val queryParameters = applicationCall.request.queryParameters
    val contractAddress = queryParameters["contractAddress"]
    val privateKey = queryParameters["privateKey"]

    return ContractRequestData(contractAddress, privateKey)
}

private fun getDateDtoByDate(_date: BigInteger): String {
    val timestamp = Timestamp(_date.toLong() * 1000)
    val date = Date(timestamp.time).toString()
    return mapper.writeValueAsString(DateDto(date, _date)) ?: _date.toString()
}

data class DateDto(val date: String, val timestamp: BigInteger?)

private fun getPersonData(data: Tuple2<String, String>): String {
    val personData = PersonData(data.component1(), data.component2())
    return mapper.writeValueAsString(personData) ?: data.toString()
}

data class PersonData(val name: String, val passport: String)

private fun getApartment(data: Tuple4<String, BigInteger, String, BigInteger>): String {
    val apartment = ApartmentDto(data.component1(), data.component2(), data.component3(), data.component4())
    return mapper.writeValueAsString(apartment) ?: data.toString()
}

data class ApartmentDto(val name: String, val area: BigInteger, val address: String, val weiPerNight: BigInteger)

private fun getDeal(data: Tuple4<BigInteger, BigInteger, BigInteger, BigInteger>): String {
    val deal = DealDto(data.component1(), data.component2(), getDateDto(data.component3()), getDateDto(data.component4()))
    return mapper.writeValueAsString(deal) ?: deal.toString()
}

private fun getDateDto(_date: BigInteger): DateDto {
    val timestamp = Timestamp(_date.toLong() * 1000)
    val date = Date(timestamp.time).toString()
    return DateDto(date, _date)
}

data class DealDto(val nightsCount: BigInteger, val totalPrice: BigInteger, val startDate: DateDto, val endDate: DateDto)

data class ContractRequestData(val contractAddress: String?, val privateKey: String?)
