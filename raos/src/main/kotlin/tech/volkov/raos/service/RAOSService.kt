package tech.volkov.raos.service

import tech.volkov.raos.model.RAOSContractContext

interface RAOSService {

    fun handleCreateContractRequest(raosContractContext: RAOSContractContext)
}
