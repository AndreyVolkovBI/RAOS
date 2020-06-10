pragma solidity <0.5.1 >=0.4.20;

/*
 RAOS - Rent Apartment On Solidity smart contract.
 Creates a smart-contract between landlord and tenant.
*/
contract RAOSContract {

    Deal deal;

    constructor() public {
        // Consider the address creating the contract as landlord
        deal.landlord.landlordAddress = msg.sender;
    }

    struct Landlord {
        string name;
        string passport;
        address landlordAddress;
    }

    struct Apartment {
        string name;
        uint area;
        string apartmentAddress;
        uint256 priceInWeiPerNight;
    }

    struct Tenant {
        string name;
        string passport;
        address tenantAddress;
    }

    struct Deal {
        Landlord landlord;
        Apartment apartment;
        Tenant tenant;
        uint nightsCount;
        uint totalPrice;
        uint rentStartDate;
        uint rentEndDate;
    }

    // modifier for ability to call function on behalf of landlord only
    modifier landlordOnly() {
        require(msg.sender == deal.landlord.landlordAddress);
        _;
    }

    // modifier for ability to call function on behalf of tenant only
    modifier tenantOnly() {
        require(msg.sender == deal.tenant.tenantAddress);
        _;
    }

    // modifier that checks that setTenant() function was called only once
    modifier tenantOnce() {
        require(deal.tenant.tenantAddress == 0);
        _;
    }

    // modifier for ability to call function on behalf of tenant or landlord only
    modifier contractMembersOnly() {
        require(
            msg.sender == deal.landlord.landlordAddress ||
            msg.sender == deal.tenant.tenantAddress
        );
        _;
    }

    // set landlord available for landlord only (the account which created the contract)
    function setLandlord(string memory name, string memory passport) landlordOnly public {
        deal.landlord.name = name;
        deal.landlord.passport = passport;
    }

    // set apartment available for landlord only (the account which created the contract)
    function setApartment(string memory name, uint area, string memory apartmentAddress, uint256 priceInWeiPerNight) landlordOnly public {
        deal.apartment.name = name;
        deal.apartment.area = area;
        deal.apartment.apartmentAddress = apartmentAddress;
        deal.apartment.priceInWeiPerNight = priceInWeiPerNight;
    }

    // set tenant available for anyone
    function setTenant(string memory name, string memory passport) tenantOnce public {
        deal.tenant.name = name;
        deal.tenant.passport = passport;
        deal.tenant.tenantAddress = msg.sender;
    }

    function getLandlord() contractMembersOnly public view returns (string memory, string memory) {
        return (deal.landlord.name, deal.landlord.passport);
    }

    function getTenant() contractMembersOnly public view returns (string memory, string memory) {
        return (deal.tenant.name, deal.tenant.passport);
    }

    function getApartment() contractMembersOnly public view returns (string memory, uint, string memory, uint256) {
        return (deal.apartment.name, deal.apartment.area, deal.apartment.apartmentAddress, deal.apartment.priceInWeiPerNight);
    }

    function getDeal() contractMembersOnly public view returns (uint, uint, uint, uint) {
        return (deal.nightsCount, deal.totalPrice, deal.rentStartDate, deal.rentEndDate);
    }

    function getRentStartDate() contractMembersOnly public view returns (uint) {
        return deal.rentStartDate;
    }

    function getRentEndDate() contractMembersOnly public view returns (uint) {
        return deal.rentEndDate;
    }

    function getTimeLeft() contractMembersOnly public view returns (uint) {
        return deal.rentEndDate - block.timestamp;
    }

    /*
        Function is called by tenant only.
        @returns the number of days for which the apartment was rented
    */
    function rentApartment() tenantOnly public payable returns (uint256) {
        // Checks that the tenant is sending the right amount for requested nights
        if (
            msg.value > 0 &&
            msg.value > deal.apartment.priceInWeiPerNight
        ) {
            // Calculate how many days the tenant has just paid for
            uint256 numberOfNightsPaid = msg.value / deal.apartment.priceInWeiPerNight;

            // setting nights count & total price
            deal.nightsCount = numberOfNightsPaid;
            deal.totalPrice = numberOfNightsPaid * deal.apartment.priceInWeiPerNight;

            // setting rent dates: start & end
            deal.rentStartDate = block.timestamp;
            deal.rentEndDate = block.timestamp + deal.nightsCount * 24 * 60 * 60;

            // if amount was greater then needed -> transfer extra amount back to tenant
            if (msg.value > deal.totalPrice) {
                deal.tenant.tenantAddress.transfer(msg.value - deal.totalPrice);
            }

            // transfer paid amount to landlord
            deal.landlord.landlordAddress.transfer(deal.totalPrice);

            return numberOfNightsPaid;

            // if tenant send not enough amount -> send all money back
        } else {
            deal.tenant.tenantAddress.transfer(msg.value);

            // return the number of days paid (0)
            return 0;
        }
    }
}
