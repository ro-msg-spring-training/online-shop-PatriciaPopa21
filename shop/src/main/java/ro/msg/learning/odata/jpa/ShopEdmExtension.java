//package ro.msg.learning.odata.jpa;
//
//import java.io.InputStream;
//
//import org.apache.olingo.odata2.jpa.processor.api.model.JPAEdmExtension;
//import org.apache.olingo.odata2.jpa.processor.api.model.JPAEdmSchemaView;
//import org.springframework.stereotype.Component;
//
//import lombok.SneakyThrows;
//
//@Component
//public class ShopEdmExtension implements JPAEdmExtension {
//
//	@Override
//	public void extendWithOperation(final JPAEdmSchemaView jpaEdmSchemaView) {
//		// not used
//	}
//
//	@Override
//	public void extendJPAEdmSchema(final JPAEdmSchemaView jpaEdmSchemaView) {
//		// not used
//	}
//
//	@Override
//	@SneakyThrows
//	public InputStream getJPAEdmMappingModelStream() {
//		return ShopEdmExtension.class.getClassLoader().getResourceAsStream("jpa-edm-mapping-model.xml");
//	}
//}