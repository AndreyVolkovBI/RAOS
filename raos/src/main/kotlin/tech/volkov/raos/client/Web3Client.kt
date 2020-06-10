package tech.volkov.raos.client

import org.web3j.crypto.Credentials
import org.web3j.crypto.WalletUtils
import org.web3j.protocol.Web3j
import org.web3j.protocol.core.methods.response.TransactionReceipt
import org.web3j.protocol.core.methods.response.Web3ClientVersion
import org.web3j.protocol.http.HttpService
import org.web3j.tx.RawTransactionManager
import org.web3j.tx.TransactionManager
import org.web3j.tx.Transfer
import org.web3j.utils.Convert
import tech.volkov.raos.core.AddressBook
import java.io.IOException
import java.math.BigDecimal
import java.math.BigInteger

class Web3Client {

    private val PRIVATE_KEY = "087db5d7c2647f17e4d028f65d46babac4525eb7f810fec992a3eac10cc53ae1"

    private val GAS_LIMIT = BigInteger.valueOf(6721975L)
    private val GAS_PRICE = BigInteger.valueOf(20000000000L)

    private val RECIPIENT = "0x466B6E82CD017923298Db45C5a3Db7c66Cd753C8"

    private val CONTRACT_ADDRESS = "0x2cf178c0fcf153dd0f40db1af064824a8c6751a5"

    init {
        val web3j: Web3j = Web3j.build(HttpService())
        val credentials: Credentials = getCredentialsFromPrivateKey()
        val addressBook: AddressBook = loadContract(CONTRACT_ADDRESS, web3j, credentials)
        removeAddress(addressBook)
        printAddresses(addressBook)
    }

    private fun printWeb3Version(web3j: Web3j) {
        var web3ClientVersion: Web3ClientVersion? = null
        try {
            web3ClientVersion = web3j.web3ClientVersion().send()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        val web3ClientVersionString: String = web3ClientVersion?.web3ClientVersion.orEmpty()
        println("Web3 client version: $web3ClientVersionString")
    }

    private fun getCredentialsFromWallet(): Credentials? {
        return WalletUtils.loadCredentials(
            "passphrase",
            "wallet/path"
        )
    }

    private fun getCredentialsFromPrivateKey(): Credentials {
        return Credentials.create(PRIVATE_KEY)
    }

    private fun transferEthereum(web3j: Web3j, credentials: Credentials) {
        val transactionManager: TransactionManager = RawTransactionManager(web3j, credentials)
        val transfer = Transfer(web3j, transactionManager)
        val transactionReceipt: TransactionReceipt = transfer.sendFunds(
            RECIPIENT,
            BigDecimal.ONE,
            Convert.Unit.ETHER,
            GAS_PRICE,
            GAS_LIMIT
        ).send()

        println("Transaction = " + transactionReceipt.transactionHash)
    }

    private fun deployContract(web3j: Web3j, credentials: Credentials): String? {
        return AddressBook.deploy(web3j, credentials, GAS_PRICE, GAS_LIMIT)
            .send()
            .contractAddress
    }

    private fun loadContract(contractAddress: String, web3j: Web3j, credentials: Credentials): AddressBook {
        return AddressBook.load(contractAddress, web3j, credentials, GAS_PRICE, GAS_LIMIT)
    }

    private fun addAddresses(addressBook: AddressBook) {
        addressBook
            .addAddress("0x256a04B9F02036Ed2f785D8f316806411D605285", "Tom")
            .send()
        addressBook
            .addAddress("0x82CDf5a3192f2930726637e9C738A78689a91Be3", "Susan")
            .send()
        addressBook
            .addAddress("0x95F57F1DD015ddE7Ec2CbC8212D0ae2faC9acA11", "Bob")
            .send()
    }

    private fun printAddresses(addressBook: AddressBook) {
        for (address in addressBook.addresses.send()) {
            val addressString = address.toString()
            val alias: String = addressBook.getAlias(addressString).send()
            println("Address $addressString aliased as $alias")
        }
    }

    private fun removeAddress(addressBook: AddressBook) {
        addressBook
            .removeAddress("0x256a04B9F02036Ed2f785D8f316806411D605285")
            .send()
    }
}
