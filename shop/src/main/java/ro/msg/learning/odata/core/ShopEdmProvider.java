//package ro.msg.learning.odata.core;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import org.apache.olingo.odata2.api.edm.EdmSimpleTypeKind;
//import org.apache.olingo.odata2.api.edm.FullQualifiedName;
//import org.apache.olingo.odata2.api.edm.provider.EdmProvider;
//import org.apache.olingo.odata2.api.edm.provider.EntityContainer;
//import org.apache.olingo.odata2.api.edm.provider.EntityContainerInfo;
//import org.apache.olingo.odata2.api.edm.provider.EntitySet;
//import org.apache.olingo.odata2.api.edm.provider.EntityType;
//import org.apache.olingo.odata2.api.edm.provider.Facets;
//import org.apache.olingo.odata2.api.edm.provider.Key;
//import org.apache.olingo.odata2.api.edm.provider.Property;
//import org.apache.olingo.odata2.api.edm.provider.PropertyRef;
//import org.apache.olingo.odata2.api.edm.provider.Schema;
//import org.apache.olingo.odata2.api.edm.provider.SimpleProperty;
//import org.apache.olingo.odata2.api.exception.ODataException;
//import org.springframework.stereotype.Component;
//
//import lombok.SneakyThrows;
//
//@Component
//public class ShopEdmProvider extends EdmProvider {
//
//	// Service Namespace
//	private static final String NAMESPACE = "ro.msg.learning.odata";
//
//	// EDM Container
//	public static final String CONTAINER_NAME = "Container";
//
//	// Entity Types Names
//	public static final String ENTITY_NAME_PRODUCT = "Product";
//	private static final FullQualifiedName ENTITY_TYPE_PRODUCT_FQN = new FullQualifiedName(NAMESPACE,
//			ENTITY_NAME_PRODUCT);
//
//	// Entity Set Names
//	public static final String ENTITY_SET_NAME_PRODUCTS = "Products";
//
////	private static final FullQualifiedName ASSOCIATION_CAR_MANUFACTURER = new FullQualifiedName(NAMESPACE,
////			"Car_Manufacturer_Manufacturer_Cars");
////
////	private static final String ROLE_1_1 = "Car_Manufacturer";
////	private static final String ROLE_1_2 = "Manufacturer_Cars";
//
//	// private static final String ASSOCIATION_SET = "Cars_Manufacturers";
//
//	@Override
//	public List<Schema> getSchemas() throws ODataException {
//		// create Schema
//		final List<Schema> schemas = new ArrayList<>();
//		final Schema schema = new Schema();
//		schema.setNamespace(NAMESPACE);
//
//		// add EntityTypes
//		final List<EntityType> entityTypes = new ArrayList<>();
//		entityTypes.add(getEntityType(ENTITY_TYPE_PRODUCT_FQN));
//		schema.setEntityTypes(entityTypes);
//
//		// add EntityContainer
//		final List<EntityContainer> entityContainers = new ArrayList<>();
//		final EntityContainer entityContainer = new EntityContainer();
//		entityContainer.setName(CONTAINER_NAME).setDefaultEntityContainer(true);
//
//		// add EntitySets
//		final List<EntitySet> entitySets = new ArrayList<>();
//		entitySets.add(getEntitySet(CONTAINER_NAME, ENTITY_SET_NAME_PRODUCTS));
//		entityContainer.setEntitySets(entitySets);
//
////		final List<Association> associations = new ArrayList<>();
////		associations.add(getAssociation(ASSOCIATION_CAR_MANUFACTURER));
////		schema.setAssociations(associations);
////
////		final List<AssociationSet> associationSets = new ArrayList<>();
////		associationSets.add(
////				getAssociationSet(ENTITY_CONTAINER, ASSOCIATION_CAR_MANUFACTURER, ENTITY_SET_NAME_PRODUCTS, ROLE_1_2));
////		entityContainer.setAssociationSets(associationSets);
//
//		entityContainers.add(entityContainer);
//		schema.setEntityContainers(entityContainers);
//
//		schemas.add(schema);
//
//		return schemas;
//	}
//
//	@Override
//	public EntityType getEntityType(final FullQualifiedName edmFQName) throws ODataException {
//		if (NAMESPACE.equals(edmFQName.getNamespace())) {
//
//			if (ENTITY_TYPE_PRODUCT_FQN.getName().equals(edmFQName.getName())) {
//
//				// Properties
//				final List<Property> properties = new ArrayList<>();
//				properties.add(new SimpleProperty().setName("Id").setType(EdmSimpleTypeKind.Int16)
//						.setFacets(new Facets().setNullable(false)));
////				properties.add(new SimpleProperty().setName("ProductName").setType(EdmSimpleTypeKind.String)
////						.setFacets(new Facets().setNullable(false).setMaxLength(100)).setCustomizableFeedMappings(
////								new CustomizableFeedMappings()/* .setFcTargetPath(EdmTargetPath.SYNDICATION_TITLE) */));
////				properties.add(new SimpleProperty().setName("ProductDescription").setType(EdmSimpleTypeKind.String)
////						.setFacets(new Facets().setNullable(false).setMaxLength(100)).setCustomizableFeedMappings(
////								new CustomizableFeedMappings()/* .setFcTargetPath(EdmTargetPath.SYNDICATION_TITLE) */));
//				// properties.add(new
//				// SimpleProperty().setName("Price").setType(EdmSimpleTypeKind.Int64)
//				// setFacets(new Facets().setNullable(false));
////				properties.add(new SimpleProperty().setName("Weight").setType(EdmSimpleTypeKind.Double));
//				properties.add(new SimpleProperty().setName("ImageUrl").setType(EdmSimpleTypeKind.String)
//				/*
//				 * .setFacets(new
//				 * Facets().setNullable(false).setMaxLength(100)).setCustomizableFeedMappings(
//				 * new CustomizableFeedMappings()
//				 * .setFcTargetPath(EdmTargetPath.SYNDICATION_TITLE) )
//				 */);
//
////				// Navigation Properties
////				final List<NavigationProperty> navigationProperties = new ArrayList<>();
////				navigationProperties.add(new NavigationProperty().setName("Cars")
////						.setRelationship(ASSOCIATION_CAR_MANUFACTURER).setFromRole(ROLE_1_2).setToRole(ROLE_1_1));
//
//				// Key
//				final List<PropertyRef> keyProperties = new ArrayList<>();
//				keyProperties.add(new PropertyRef().setName("Id"));
//				final Key key = new Key().setKeys(keyProperties);
//
//				return new EntityType().setName(ENTITY_NAME_PRODUCT).setProperties(properties).setKey(key)
//				/* .setNavigationProperties(navigationProperties) */;
//			}
//		}
//
//		return null;
//	}
//
//	@Override
//	public EntitySet getEntitySet(final String entityContainer, final String name) throws ODataException {
//		if (CONTAINER_NAME.equals(entityContainer)) {
//			if (ENTITY_SET_NAME_PRODUCTS.equals(name)) {
//				return new EntitySet().setName(name).setEntityType(ENTITY_TYPE_PRODUCT_FQN);
//			}
//		}
//		return null;
//	}
//
//	@SneakyThrows
//	public EntityContainer getEntityContainer() {
//
//		// create EntitySets
//		final List<EntitySet> entitySets = new ArrayList<>();
//		entitySets.add(getEntitySet(CONTAINER_NAME, ENTITY_SET_NAME_PRODUCTS));
//
//		// create EntityContainer
//		final EntityContainer entityContainer = new EntityContainer();
//		entityContainer.setName(CONTAINER_NAME);
//		entityContainer.setEntitySets(entitySets);
//
//		return entityContainer;
//	}
//
//	@Override
//	public EntityContainerInfo getEntityContainerInfo(final String name) throws ODataException {
//		if (name == null || CONTAINER_NAME.equals(name)) {
//			return new EntityContainerInfo().setName(CONTAINER_NAME).setDefaultEntityContainer(true);
//		}
//
//		return null;
//	}
//
////	@Override
////	public Association getAssociation(final FullQualifiedName edmFQName) throws ODataException {
////		if (NAMESPACE.equals(edmFQName.getNamespace())) {
////			if (ASSOCIATION_CAR_MANUFACTURER.getName().equals(edmFQName.getName())) {
////				return new Association().setName(ASSOCIATION_CAR_MANUFACTURER.getName())
////						.setEnd1(new AssociationEnd().setType(ENTITY_TYPE_1_1).setRole(ROLE_1_1)
////								.setMultiplicity(EdmMultiplicity.MANY))
////						.setEnd2(new AssociationEnd().setType(ENTITY_TYPE_1_2).setRole(ROLE_1_2)
////								.setMultiplicity(EdmMultiplicity.ONE));
////			}
////		}
////		return null;
////	}
//
////	@Override
////	public AssociationSet getAssociationSet(final String entityContainer, final FullQualifiedName association,
////			final String sourceEntitySetName, final String sourceEntitySetRole) throws ODataException {
////		if (ENTITY_CONTAINER.equals(entityContainer)) {
////			if (ASSOCIATION_CAR_MANUFACTURER.equals(association)) {
////				return new AssociationSet().setName(ASSOCIATION_SET).setAssociation(ASSOCIATION_CAR_MANUFACTURER)
////						.setEnd1(new AssociationSetEnd().setRole(ROLE_1_2).setEntitySet(ENTITY_SET_NAME_PRODUCTS))
////						.setEnd2(new AssociationSetEnd().setRole(ROLE_1_1).setEntitySet(ENTITY_SET_NAME_CARS));
////			}
////		}
////		return null;
////	}
//}