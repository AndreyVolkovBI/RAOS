package tech.volkov.raos.service

import tech.volkov.raos.model.RAOSContractContext
import java.math.BigInteger

interface RAOSService {

    fun processContract(
        landlordPrivateKey: String, tenantPrivateKey: String,
        raosContractContext: RAOSContractContext
    )

    fun getRentStartDate(contractAddress: String, privateKey: String): BigInteger?
}
