package tech.volkov.raos.service

import org.web3j.tuples.generated.Tuple2
import org.web3j.tuples.generated.Tuple4
import tech.volkov.raos.model.RAOSContractContext
import java.math.BigInteger

interface RAOSService {

    fun processContract(
        landlordPrivateKey: String, tenantPrivateKey: String,
        raosContractContext: RAOSContractContext
    )

    fun getRentStartDate(contractAddress: String, privateKey: String): BigInteger?

    fun getRentEndDate(contractAddress: String, privateKey: String): BigInteger?

    fun getTimeLeft(contractAddress: String, privateKey: String): BigInteger?

    fun getLandlord(contractAddress: String, privateKey: String): Tuple2<String, String>?

    fun getTenant(contractAddress: String, privateKey: String): Tuple2<String, String>?

    fun getApartment(contractAddress: String, privateKey: String): Tuple4<String, BigInteger, String, BigInteger>?

    fun getDeal(contractAddress: String, privateKey: String): Tuple4<BigInteger, BigInteger, BigInteger, BigInteger>?
}
