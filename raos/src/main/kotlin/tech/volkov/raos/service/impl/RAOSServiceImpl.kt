package tech.volkov.raos.service.impl

import org.web3j.crypto.Credentials
import org.web3j.protocol.Web3j
import org.web3j.protocol.http.HttpService
import org.web3j.tuples.generated.Tuple2
import org.web3j.tuples.generated.Tuple4
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
        raosContract.rentApartment(BigInteger.valueOf(context.deal.priceInWei)).send()
    }

    override fun getRentStartDate(contractAddress: String, privateKey: String): BigInteger? {
        val contract = loadContract(contractAddress, web3j, buildCredentials(privateKey))
        return contract.rentStartDate.send()
    }

    override fun getRentEndDate(contractAddress: String, privateKey: String): BigInteger? {
        val contract = loadContract(contractAddress, web3j, buildCredentials(privateKey))
        return contract.rentEndDate.send()
    }

    override fun getTimeLeft(contractAddress: String, privateKey: String): BigInteger? {
        val contract = loadContract(contractAddress, web3j, buildCredentials(privateKey))
        return contract.timeLeft.send()
    }

    override fun getLandlord(contractAddress: String, privateKey: String): Tuple2<String, String>? {
        val contract = loadContract(contractAddress, web3j, buildCredentials(privateKey))
        return contract.landlord.send()
    }

    override fun getTenant(contractAddress: String, privateKey: String): Tuple2<String, String>? {
        val contract = loadContract(contractAddress, web3j, buildCredentials(privateKey))
        return contract.tenant.send()
    }

    override fun getDeal(contractAddress: String, privateKey: String): Tuple4<BigInteger, BigInteger, BigInteger, BigInteger>? {
        val contract = loadContract(contractAddress, web3j, buildCredentials(privateKey))
        return contract.deal.send()
    }

    override fun getApartment(contractAddress: String, privateKey: String): Tuple4<String, BigInteger, String, BigInteger>? {
        val contract = loadContract(contractAddress, web3j, buildCredentials(privateKey))
        return contract.apartment.send()
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
