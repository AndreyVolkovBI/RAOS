package tech.volkov.raosexample.contract

import tech.volkov.raos.builder.contract
import tech.volkov.raosexample.secret.LANDLORD_PRIVATE_KEY
import tech.volkov.raosexample.secret.TENANT_PRIVATE_KEY

fun main() {
    createContract()
}

private fun createContract() {
    contract(LANDLORD_PRIVATE_KEY, TENANT_PRIVATE_KEY) {

        landlord {
            name = "Ivan Ivanov"
            passport = "Ivan Ivanov's passport data"
        }

        tenant {
            name = "Ilya Sidorov"
            passport = "Ilya Sidorov's passport data"
        }

        apartment {
            name = "Apartment on Petrovka street, near to The Big Theatre"
            address = "Russia, Moscow, Petrovka st. 7, 25"
            area = 57
            priceInWeiPerNight = 10000
        }
    }
}
