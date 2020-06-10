package tech.volkov.raos.model

@DslMarker
annotation class RAOSContractDSL

@RAOSContractDSL
class RAOSContractContext {
    var landlord: Landlord = Landlord()
        private set

    var tenant: Tenant = Tenant()
        private set

    var apartment: Apartment = Apartment()
        private set

    var deal: Deal = Deal()
        private set

    fun landlord(init: Landlord.() -> Unit) {
        landlord = Landlord().apply(init)
    }

    fun tenant(init: Tenant.() -> Unit) {
        tenant = Tenant().apply(init)
    }

    fun apartment(init: Apartment.() -> Unit) {
        apartment = Apartment().apply(init)
    }

    fun deal(init: Deal.() -> Unit) {
        deal = Deal().apply(init)
    }
}
