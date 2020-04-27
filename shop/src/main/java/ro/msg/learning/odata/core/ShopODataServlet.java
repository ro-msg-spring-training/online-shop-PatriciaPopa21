//package ro.msg.learning.odata.core;
//
//import org.apache.olingo.odata2.api.ODataServiceFactory;
//import org.apache.olingo.odata2.core.servlet.ODataServlet;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.stereotype.Component;
//
//@Component
//class ShopODataServlet extends ODataServlet {
//	private final ODataServiceFactory factory;
//
//	public ShopODataServlet(@Qualifier("CoreODataServiceFactory") final ODataServiceFactory factory) {
//		this.factory = factory;
//	}
//
//	@Override
//	public String getInitParameter(final String name) {
//		if (ODataServiceFactory.FACTORY_LABEL.equals(name)) {
//			return factory.getClass().getCanonicalName();
//		}
//
//		return super.getInitParameter(name);
//	}
//}
