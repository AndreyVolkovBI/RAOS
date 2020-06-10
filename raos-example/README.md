# RAOS example

## Generate solidity binary

```bash
solc ./src/main/resources/solidity/RAOSContract.sol --bin --abi --optimize -o ./src/main/resources/output
```

## Generate solidity java

```bash
web3j solidity generate ./src/main/resources/output/RAOSContract.bin ./src/main/resources/output/RAOSContract.abi -o ./src/main/java -p tech.volkov.raosexample
```
