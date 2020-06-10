package tech.volkov.raos.builder

import tech.volkov.raos.model.RAOSContractContext
import tech.volkov.raos.service.RAOSService
import tech.volkov.raos.service.impl.RAOSServiceImpl

private val raosService: RAOSService = RAOSServiceImpl()

/**
 * Main high order function that receives [landlordPrivateKey], [tenantPrivateKey] with [block]
 * @return [RAOSContractContext]
 */
fun contract(landlordPrivateKey: String, tenantPrivateKey: String, block: RAOSContractContext.() -> Unit = {}) {
    val raosContractContext = RAOSContractContext().apply { block() }
    raosService.processContract(landlordPrivateKey, tenantPrivateKey, raosContractContext)
}
