//package ro.msg.learning.odata.jpa;
//
//import org.apache.olingo.odata2.api.ODataServiceFactory;
//import org.apache.olingo.odata2.core.servlet.ODataServlet;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.stereotype.Component;
//
///* In the old version of the API for ODataServlet there was a method called getServiceFactory(), which was used for passing
// * a configured instance of the factory to the ODataServlet.
// *
// * In the new API, the logic of the service() method has changed; thus, we need to override the getInitParameter()
// * so that it returns the canonical name of our factory when the value of the input parameter is the one in
// * ODataServiceFactory.FACTORY_LABEL; additionally, we will need to provide a no-args constructor for our ServiceFactory,
// * as this is the one being called in ODataServlet.service() when setting the value of the serviceFactory attribute.
// * However, this means our ServiceFactory will no longer be configured with our JPAEdmExtension and ODataSingleProcessor,
// * which will lead to a NullPointerException when the method ODataServlet.handleRequest() executes this instruction:
// * service.getProcessor().setContext(context);
// *
// * Since service() calls some private methods, overriding it here is not possible; the only possible, but really nasty
// * alternative would be to extend HttpServlet ourselves and basically copy everything from ODataServlet into this new
// * implementation and adjust it to our needs */
//
//@Component
//class JpaODataServlet extends ODataServlet {
//	private final ODataServiceFactory factory;
//
//	public JpaODataServlet(@Qualifier("JpaODataServiceFactory") final ODataServiceFactory factory) {
//		this.factory = factory;
//	}
//
//	@Override
//	public String getInitParameter(final String name) {
//		if (ODataServiceFactory.FACTORY_LABEL.equals(name)) {
//			return JpaServiceFactory.class.getCanonicalName();
//		}
//
//		return super.getInitParameter(name);
//	}
//}