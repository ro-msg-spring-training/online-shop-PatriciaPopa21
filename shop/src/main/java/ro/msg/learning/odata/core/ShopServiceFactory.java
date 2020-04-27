//package ro.msg.learning.odata.core;
//
//import org.apache.olingo.odata2.api.ODataService;
//import org.apache.olingo.odata2.api.ODataServiceFactory;
//import org.apache.olingo.odata2.api.edm.provider.EdmProvider;
//import org.apache.olingo.odata2.api.processor.ODataContext;
//import org.apache.olingo.odata2.api.processor.ODataSingleProcessor;
//import org.springframework.stereotype.Component;
//
//import lombok.AllArgsConstructor;
//import lombok.NoArgsConstructor;
//
//@Component("CoreODataServiceFactory")
//@NoArgsConstructor
//@AllArgsConstructor
//public class ShopServiceFactory extends ODataServiceFactory {
//	private EdmProvider edmProvider;
//	private ODataSingleProcessor shopProcessor;
//
//	@Override
//	public ODataService createService(final ODataContext ctx) {
//		return createODataSingleProcessorService(edmProvider, shopProcessor);
//	}
//}