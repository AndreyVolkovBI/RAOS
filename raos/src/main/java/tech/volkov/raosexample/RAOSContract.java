package tech.volkov.raosexample;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tuples.generated.Tuple2;
import org.web3j.tuples.generated.Tuple4;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 3.6.0.
 */
public class RAOSContract extends Contract {
    private static final String BINARY = "608060405234801561001057600080fd5b5060028054600160a060020a03191633179055610c50806100326000396000f3006080604052600436106100ae5763ffffffff7c01000000000000000000000000000000000000000000000000000000006000350416630b3bb02481146100b3578063130df15c146101a65780635a1a105d146102475780636680b513146102615780638528b9441461029c57806389cca403146102b1578063bd97d7fd14610348578063c7e284b81461035d578063e107b04d14610372578063f438c16114610473578063f502236d1461050a575b600080fd5b3480156100bf57600080fd5b506100c861051f565b604051808060200180602001838103835285818151815260200191508051906020019080838360005b838110156101095781810151838201526020016100f1565b50505050905090810190601f1680156101365780820380516001836020036101000a031916815260200191505b50838103825284518152845160209182019186019080838360005b83811015610169578181015183820152602001610151565b50505050905090810190601f1680156101965780820380516001836020036101000a031916815260200191505b5094505050505060405180910390f35b3480156101b257600080fd5b506040805160206004803580820135601f810184900484028501840190955284845261024594369492936024939284019190819084018382808284375050604080516020601f818a01358b0180359182018390048302840183018552818452989b8a359b909a909994019750919550918201935091508190840183828082843750949750509335945061067a9350505050565b005b61024f6106c6565b60408051918252519081900360200190f35b34801561026d57600080fd5b506102766107f9565b604080519485526020850193909352838301919091526060830152519081900360800190f35b3480156102a857600080fd5b5061024f610847565b3480156102bd57600080fd5b506040805160206004803580820135601f810184900484028501840190955284845261024594369492936024939284019190819084018382808284375050604080516020601f89358b018035918201839004830284018301909452808352979a9998810197919650918201945092508291508401838280828437509497506108809650505050505050565b34801561035457600080fd5b5061024f6108c3565b34801561036957600080fd5b5061024f6108fb565b34801561037e57600080fd5b50610387610936565b604051808060200185815260200180602001848152602001838103835287818151815260200191508051906020019080838360005b838110156103d45781810151838201526020016103bc565b50505050905090810190601f1680156104015780820380516001836020036101000a031916815260200191505b50838103825285518152855160209182019187019080838360005b8381101561043457818101518382015260200161041c565b50505050905090810190601f1680156104615780820380516001836020036101000a031916815260200191505b50965050505050505060405180910390f35b34801561047f57600080fd5b506040805160206004803580820135601f810184900484028501840190955284845261024594369492936024939284019190819084018382808284375050604080516020601f89358b018035918201839004830284018301909452808352979a999881019791965091820194509250829150840183828082843750949750610aa79650505050505050565b34801561051657600080fd5b506100c8610b08565b6002546060908190600160a060020a03163314806105475750600954600160a060020a031633145b151561055257600080fd5b6007805460408051602060026001851615610100026000190190941693909304601f8101849004840282018401909252818152600892909184918301828280156105dd5780601f106105b2576101008083540402835291602001916105dd565b820191906000526020600020905b8154815290600101906020018083116105c057829003601f168201915b5050845460408051602060026001851615610100026000190190941693909304601f81018490048402820184019092528181529597508694509250840190508282801561066b5780601f106106405761010080835404028352916020019161066b565b820191906000526020600020905b81548152906001019060200180831161064e57829003601f168201915b50505050509050915091509091565b600254600160a060020a0316331461069157600080fd5b83516106a4906003906020870190610b96565b50600483905581516106bd906005906020850190610b96565b50600655505050565b6009546000908190600160a060020a031633146106e257600080fd5b6000341180156106f3575060065434115b156107b6576006543481151561070557fe5b04600a8190556006548102600b81905542600c81905562015180830201600d5590915034111561077157600954600b54604051600160a060020a0390921691349190910380156108fc02916000818181858888f1935050505015801561076f573d6000803e3d6000fd5b505b600254600b54604051600160a060020a039092169181156108fc0291906000818181858888f193505050501580156107ad573d6000803e3d6000fd5b508091506107f5565b600954604051600160a060020a03909116903480156108fc02916000818181858888f193505050501580156107ef573d6000803e3d6000fd5b50600091505b5090565b600254600090819081908190600160a060020a03163314806108255750600954600160a060020a031633145b151561083057600080fd5b5050600a54600b54600c54600d5492959194509250565b600254600090600160a060020a031633148061086d5750600954600160a060020a031633145b151561087857600080fd5b50600c545b90565b600254600160a060020a0316331461089757600080fd5b81516108aa906000906020850190610b96565b5080516108be906001906020840190610b96565b505050565b600254600090600160a060020a03163314806108e95750600954600160a060020a031633145b15156108f457600080fd5b50600d5490565b600254600090600160a060020a03163314806109215750600954600160a060020a031633145b151561092c57600080fd5b50600d5442900390565b60025460609060009082908290600160a060020a03163314806109635750600954600160a060020a031633145b151561096e57600080fd5b6004546006546003805460408051602060026001851615610100026000190190941693909304601f8101849004840282018401909252818152929493600593909290918691830182828015610a045780601f106109d957610100808354040283529160200191610a04565b820191906000526020600020905b8154815290600101906020018083116109e757829003601f168201915b5050855460408051602060026001851615610100026000190190941693909304601f810184900484028201840190925281815295995087945092508401905082828015610a925780601f10610a6757610100808354040283529160200191610a92565b820191906000526020600020905b815481529060010190602001808311610a7557829003601f168201915b50505050509150935093509350935090919293565b600954600160a060020a031615610abd57600080fd5b8151610ad0906007906020850190610b96565b508051610ae4906008906020840190610b96565b50506009805473ffffffffffffffffffffffffffffffffffffffff19163317905550565b6002546060908190600160a060020a0316331480610b305750600954600160a060020a031633145b1515610b3b57600080fd5b600080546040805160206002600180861615610100026000190190951604601f8101829004820283018201909352828252909184918301828280156105dd5780601f106105b2576101008083540402835291602001916105dd565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f10610bd757805160ff1916838001178555610c04565b82800160010185558215610c04579182015b82811115610c04578251825591602001919060010190610be9565b506107f59261087d9250905b808211156107f55760008155600101610c105600a165627a7a7230582012e7448c30342df9a58b87ae805d53b5486cca3060f20c5aa9ef13df45a417a50029";

    public static final String FUNC_GETTENANT = "getTenant";

    public static final String FUNC_SETAPARTMENT = "setApartment";

    public static final String FUNC_RENTAPARTMENT = "rentApartment";

    public static final String FUNC_GETDEAL = "getDeal";

    public static final String FUNC_GETRENTSTARTDATE = "getRentStartDate";

    public static final String FUNC_SETLANDLORD = "setLandlord";

    public static final String FUNC_GETRENTENDDATE = "getRentEndDate";

    public static final String FUNC_GETTIMELEFT = "getTimeLeft";

    public static final String FUNC_GETAPARTMENT = "getApartment";

    public static final String FUNC_SETTENANT = "setTenant";

    public static final String FUNC_GETLANDLORD = "getLandlord";

    @Deprecated
    protected RAOSContract(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected RAOSContract(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected RAOSContract(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected RAOSContract(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteCall<Tuple2<String, String>> getTenant() {
        final Function function = new Function(FUNC_GETTENANT, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}));
        return new RemoteCall<Tuple2<String, String>>(
                new Callable<Tuple2<String, String>>() {
                    @Override
                    public Tuple2<String, String> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple2<String, String>(
                                (String) results.get(0).getValue(), 
                                (String) results.get(1).getValue());
                    }
                });
    }

    public RemoteCall<TransactionReceipt> setApartment(String name, BigInteger area, String apartmentAddress, BigInteger priceInWeiPerNight) {
        final Function function = new Function(
                FUNC_SETAPARTMENT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(name), 
                new org.web3j.abi.datatypes.generated.Uint256(area), 
                new org.web3j.abi.datatypes.Utf8String(apartmentAddress), 
                new org.web3j.abi.datatypes.generated.Uint256(priceInWeiPerNight)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> rentApartment(BigInteger weiValue) {
        final Function function = new Function(
                FUNC_RENTAPARTMENT, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function, weiValue);
    }

    public RemoteCall<Tuple4<BigInteger, BigInteger, BigInteger, BigInteger>> getDeal() {
        final Function function = new Function(FUNC_GETDEAL, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}));
        return new RemoteCall<Tuple4<BigInteger, BigInteger, BigInteger, BigInteger>>(
                new Callable<Tuple4<BigInteger, BigInteger, BigInteger, BigInteger>>() {
                    @Override
                    public Tuple4<BigInteger, BigInteger, BigInteger, BigInteger> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple4<BigInteger, BigInteger, BigInteger, BigInteger>(
                                (BigInteger) results.get(0).getValue(), 
                                (BigInteger) results.get(1).getValue(), 
                                (BigInteger) results.get(2).getValue(), 
                                (BigInteger) results.get(3).getValue());
                    }
                });
    }

    public RemoteCall<BigInteger> getRentStartDate() {
        final Function function = new Function(FUNC_GETRENTSTARTDATE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<TransactionReceipt> setLandlord(String name, String passport) {
        final Function function = new Function(
                FUNC_SETLANDLORD, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(name), 
                new org.web3j.abi.datatypes.Utf8String(passport)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<BigInteger> getRentEndDate() {
        final Function function = new Function(FUNC_GETRENTENDDATE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<BigInteger> getTimeLeft() {
        final Function function = new Function(FUNC_GETTIMELEFT, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<Tuple4<String, BigInteger, String, BigInteger>> getApartment() {
        final Function function = new Function(FUNC_GETAPARTMENT, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Uint256>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Uint256>() {}));
        return new RemoteCall<Tuple4<String, BigInteger, String, BigInteger>>(
                new Callable<Tuple4<String, BigInteger, String, BigInteger>>() {
                    @Override
                    public Tuple4<String, BigInteger, String, BigInteger> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple4<String, BigInteger, String, BigInteger>(
                                (String) results.get(0).getValue(), 
                                (BigInteger) results.get(1).getValue(), 
                                (String) results.get(2).getValue(), 
                                (BigInteger) results.get(3).getValue());
                    }
                });
    }

    public RemoteCall<TransactionReceipt> setTenant(String name, String passport) {
        final Function function = new Function(
                FUNC_SETTENANT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(name), 
                new org.web3j.abi.datatypes.Utf8String(passport)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<Tuple2<String, String>> getLandlord() {
        final Function function = new Function(FUNC_GETLANDLORD, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}));
        return new RemoteCall<Tuple2<String, String>>(
                new Callable<Tuple2<String, String>>() {
                    @Override
                    public Tuple2<String, String> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple2<String, String>(
                                (String) results.get(0).getValue(), 
                                (String) results.get(1).getValue());
                    }
                });
    }

    public static RemoteCall<RAOSContract> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(RAOSContract.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    public static RemoteCall<RAOSContract> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(RAOSContract.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<RAOSContract> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(RAOSContract.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<RAOSContract> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(RAOSContract.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    @Deprecated
    public static RAOSContract load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new RAOSContract(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static RAOSContract load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new RAOSContract(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static RAOSContract load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new RAOSContract(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static RAOSContract load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new RAOSContract(contractAddress, web3j, transactionManager, contractGasProvider);
    }
}
