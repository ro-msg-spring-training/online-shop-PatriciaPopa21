//package ro.msg.learning.odata.jpa;
//
//import org.apache.olingo.odata2.api.processor.ODataSingleProcessor;
//import org.apache.olingo.odata2.jpa.processor.api.ODataJPAContext;
//import org.apache.olingo.odata2.jpa.processor.api.ODataJPAServiceFactory;
//import org.apache.olingo.odata2.jpa.processor.api.exception.ODataJPARuntimeException;
//import org.apache.olingo.odata2.jpa.processor.api.model.JPAEdmExtension;
//import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
//import org.springframework.stereotype.Component;
//
//import lombok.AllArgsConstructor;
//
//@Component("JpaODataServiceFactory")
//@AllArgsConstructor
//public class JpaServiceFactory extends ODataJPAServiceFactory {
//	private LocalContainerEntityManagerFactoryBean factory;
//	private JPAEdmExtension shopEdmExtension;
//	private ODataSingleProcessor shopODataProcessor;
//
//	public JpaServiceFactory() {
//		System.out.println("Hello there");
//	}
//
//	@Override
//	public ODataJPAContext initializeODataJPAContext() throws ODataJPARuntimeException {
//		final ODataJPAContext context = this.getODataJPAContext();
//		context.setEntityManagerFactory(factory.getObject());
//		context.setPersistenceUnitName("shop");
//		context.setODataProcessor(shopODataProcessor);
//		context.setJPAEdmExtension(shopEdmExtension);
//		context.setDefaultNaming(false);
//
//		return context;
//	}
//}