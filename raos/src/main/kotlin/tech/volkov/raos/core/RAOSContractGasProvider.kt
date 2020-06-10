package tech.volkov.raos.core

import org.web3j.tx.gas.ContractGasProvider
import java.math.BigInteger

class RAOSContractGasProvider : ContractGasProvider {

    companion object {
        private val GAS_LIMIT = BigInteger.valueOf(6721975L)
        private val GAS_PRICE = BigInteger.valueOf(20000000000L)
    }

    override fun getGasLimit(contractFunc: String?): BigInteger = GAS_LIMIT
    override fun getGasLimit(): BigInteger = GAS_LIMIT

    override fun getGasPrice(contractFunc: String?): BigInteger = GAS_PRICE
    override fun getGasPrice(): BigInteger = GAS_PRICE
}