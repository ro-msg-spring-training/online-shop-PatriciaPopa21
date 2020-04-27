//package ro.msg.learning.odata.core;
//
//import static ro.msg.learning.odata.core.ShopEdmProvider.ENTITY_SET_NAME_PRODUCTS;
//
//import java.net.URI;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.stream.Collectors;
//
//import org.apache.olingo.odata2.api.edm.EdmEntitySet;
//import org.apache.olingo.odata2.api.edm.EdmLiteralKind;
//import org.apache.olingo.odata2.api.edm.EdmProperty;
//import org.apache.olingo.odata2.api.edm.EdmSimpleType;
//import org.apache.olingo.odata2.api.ep.EntityProvider;
//import org.apache.olingo.odata2.api.ep.EntityProviderWriteProperties;
//import org.apache.olingo.odata2.api.ep.EntityProviderWriteProperties.ODataEntityProviderPropertiesBuilder;
//import org.apache.olingo.odata2.api.exception.ODataException;
//import org.apache.olingo.odata2.api.exception.ODataNotFoundException;
//import org.apache.olingo.odata2.api.exception.ODataNotImplementedException;
//import org.apache.olingo.odata2.api.processor.ODataResponse;
//import org.apache.olingo.odata2.api.processor.ODataSingleProcessor;
//import org.apache.olingo.odata2.api.uri.KeyPredicate;
//import org.apache.olingo.odata2.api.uri.info.GetEntitySetUriInfo;
//import org.apache.olingo.odata2.api.uri.info.GetEntityUriInfo;
//import org.springframework.stereotype.Component;
//
//import lombok.AllArgsConstructor;
//import ro.msg.learning.entity.Product;
//import ro.msg.learning.service.interfaces.ProductService;
//
//@Component
//@AllArgsConstructor
//public class ShopODataProcessor extends ODataSingleProcessor {
//	private final ProductService productService;
//
//	@Override
//	public ODataResponse readEntitySet(final GetEntitySetUriInfo uriInfo, final String contentType)
//			throws ODataException {
//
//		EdmEntitySet entitySet;
//
//		if (uriInfo.getNavigationSegments().isEmpty()) {
//			entitySet = uriInfo.getStartEntitySet();
//
//			if (ENTITY_SET_NAME_PRODUCTS.equals(entitySet.getName())) {
//				return EntityProvider.writeFeed(contentType, entitySet, getProductDataForAllProducts(),
//						EntityProviderWriteProperties.serviceRoot(getContext().getPathInfo().getServiceRoot()).build());
//			}
//
//			throw new ODataNotFoundException(ODataNotFoundException.ENTITY);
//
//		}
//
//		throw new ODataNotImplementedException();
//	}
//
//	@Override
//	public ODataResponse readEntity(final GetEntityUriInfo uriInfo, final String contentType) throws ODataException {
//
//		if (uriInfo.getNavigationSegments().isEmpty()) {
//			final EdmEntitySet entitySet = uriInfo.getStartEntitySet();
//
//			if (ENTITY_SET_NAME_PRODUCTS.equals(entitySet.getName())) {
//				final int id = getKeyValue(uriInfo.getKeyPredicates().get(0));
//				final Map<String, Object> data = getProductDataForGivenId(id);
//
//				if (data != null) {
//					final URI serviceRoot = getContext().getPathInfo().getServiceRoot();
//					final ODataEntityProviderPropertiesBuilder propertiesBuilder = EntityProviderWriteProperties
//							.serviceRoot(serviceRoot);
//
//					return EntityProvider.writeEntry(contentType, entitySet, data, propertiesBuilder.build());
//				}
//
//				throw new ODataNotFoundException(ODataNotFoundException.ENTITY);
//			}
//		}
//
//		throw new ODataNotImplementedException();
//	}
//
//	private List<Map<String, Object>> getProductDataForAllProducts() {
//		return productService.getAllProducts().stream().map(product -> getProductDataForGivenId(product.getId()))
//				.collect(Collectors.toList());
//	}
//
//	private Map<String, Object> getProductDataForGivenId(final int id) {
//		final Product product = productService.getProduct(id).get();
//		final Map<String, Object> data = new HashMap<>();
//		data.put("id", product.getId());
//		// data.put("productName", product.getProductName());
//		// data.put("productDescription", product.getProductDescription());
//		// data.put("price", product.getPrice());
//		// data.put("weight", product.getWeight());
//		data.put("imageUrl", product.getImageUrl());
//
//		return data;
//	}
//
//	private int getKeyValue(final KeyPredicate key) throws ODataException {
//		final EdmProperty property = key.getProperty();
//		final EdmSimpleType type = (EdmSimpleType) property.getType();
//		return type.valueOfString(key.getLiteral(), EdmLiteralKind.DEFAULT, property.getFacets(), Integer.class);
//	}
//}