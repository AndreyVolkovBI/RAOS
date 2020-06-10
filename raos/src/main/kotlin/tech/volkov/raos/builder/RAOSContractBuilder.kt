package tech.volkov.raos.builder

import tech.volkov.raos.model.RAOSContractContext
import tech.volkov.raos.service.RAOSService
import tech.volkov.raos.service.impl.RAOSServiceImpl

private val raosService: RAOSService = RAOSServiceImpl()

/**
 * Main high order function that receives [privateKey] with [block]
 * @return [RAOSContractContext]
 */
fun contract(privateKey: String, block: RAOSContractContext.() -> Unit = {}) {
    val raosContractContext = RAOSContractContext().apply { block() }
    raosService.handleCreateContractRequest(raosContractContext)
}
