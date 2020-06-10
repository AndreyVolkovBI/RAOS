package tech.volkov.raos.service.impl

import org.web3j.crypto.Credentials
import org.web3j.protocol.Web3j
import org.web3j.protocol.http.HttpService
import tech.volkov.raos.core.RAOSContractGasProvider
import tech.volkov.raos.model.RAOSContractContext
import tech.volkov.raos.service.RAOSService
import tech.volkov.raosexample.RAOSContract
import java.math.BigInteger

class RAOSServiceImpl : RAOSService {

    private val web3j = Web3j.build(HttpService())

    override fun processContract(
        landlordPrivateKey: String, tenantPrivateKey: String,
        raosContractContext: RAOSContractContext
    ) {
        log(
            "Started processing contract for landlord: ${raosContractContext.landlord.name}, " +
                "tenant: ${raosContractContext.tenant.name}"
        )
        val raosContractAddress = deployContract(web3j, buildCredentials(landlordPrivateKey))

        val landlordContract = loadContract(raosContractAddress, web3j, buildCredentials(landlordPrivateKey))
        callLandlordFunctions(landlordContract, raosContractContext)

        val tenantContract = loadContract(raosContractAddress, web3j, buildCredentials(tenantPrivateKey))
        callTenantFunctions(tenantContract, raosContractContext)

        callRentApartmentFunction(tenantContract, raosContractContext)

        log("Successfully finished processing contract")
    }

    private fun callLandlordFunctions(raosContract: RAOSContract, context: RAOSContractContext) {
        with(context.landlord) {
            raosContract
                .setLandlord(name, passport)
                .send()
        }

        with(context.apartment) {
            raosContract
                .setApartment(name, BigInteger.valueOf(area), address, BigInteger.valueOf(priceInWeiPerNight))
                .send()
        }
        log("Landlord functions was successfully called")
    }

    private fun callTenantFunctions(raosContract: RAOSContract, context: RAOSContractContext) {
        with(context.tenant) {
            raosContract
                .setTenant(name, passport)
                .send()
        }
        log("Tenant functions was successfully called")
    }

    private fun callRentApartmentFunction(raosContract: RAOSContract, context: RAOSContractContext) {
        raosContract.rentApartment(BigInteger.valueOf(context.apartment.priceInWeiPerNight * 10))
    }

    override fun getRentStartDate(contractAddress: String, privateKey: String): BigInteger? {
        val contract = loadContract(contractAddress, web3j, buildCredentials(privateKey))
        return contract.rentStartDate.send()
    }

    private fun deployContract(web3j: Web3j, credentials: Credentials): String {
        return RAOSContract.deploy(web3j, credentials, RAOSContractGasProvider())
            .send()
            .contractAddress
            .also { log("The contract was successfully deployed, address: $it") }
    }

    private fun loadContract(contractAddress: String, web3j: Web3j, credentials: Credentials): RAOSContract {
        return RAOSContract.load(contractAddress, web3j, credentials, RAOSContractGasProvider())
    }

    private fun buildCredentials(privateKey: String) = Credentials.create(privateKey)

    private fun log(message: String) = println(message).also { println() }
}
