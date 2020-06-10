package tech.volkov.raosexample

import tech.volkov.raos.builder.contract

class SampleRAOSContract {
    companion object {
        private const val PRIVATE_KEY = "secret-private-key"
    }

    fun main(args: Array<String>) {
        createContract()
    }

    private fun createContract() {
        contract(PRIVATE_KEY) {

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
}
