package ro.msg.learning.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;

import lombok.SneakyThrows;
import ro.msg.learning.dto.OrderDto;
import ro.msg.learning.entity.Address;
import ro.msg.learning.entity.Location;
import ro.msg.learning.entity.LocationWithDistance;
import ro.msg.learning.entity.OrderDetail;
import ro.msg.learning.entity.OrderDetailDto;
import ro.msg.learning.entity.Product;
import ro.msg.learning.exception.InsufficientStockException;
import ro.msg.learning.repository.OrderDetailRepository;
import ro.msg.learning.service.interfaces.LocationService;
import ro.msg.learning.service.interfaces.OrderDetailService;
import ro.msg.learning.service.interfaces.ProductService;
import ro.msg.learning.service.interfaces.StockService;

@Service
public class ClosestLocationStrategy extends OrderDetailService {

	private static final String CLOSEST_LOCATION = "closest_location";
	private static final String DOUBLE_QUOTE = "\"";
	private static final String SEPARATOR = ", ";
	private static final String POSTFIX = " ], \"options\": { \"allToAll\": false, \"manyToOne\" : true } }";
	private static final String PREFIX = "{ \"locations\": [ ";
	private static final String API_KEY = "e2KT3HtWFzTasnfAMt0LBeVDOlqs6YQR";
	private static final String REQUEST_URL = "http://www.mapquestapi.com/directions/v2/routematrix?key=";

	public ClosestLocationStrategy(final LocationService locationService, final ProductService productService,
			final OrderDetailRepository orderDetailRepository, final StockService stockService) {
		this.locationService = locationService;
		this.productService = productService;
		this.orderDetailRepository = orderDetailRepository;
		this.stockService = stockService;
	}

	@Override
	public String getName() {
		return CLOSEST_LOCATION;
	}

	@Override
	@SneakyThrows
	protected List<OrderDetail> generateOrderDetails(final OrderDto orderDto) {
		final List<OrderDetailDto> orderDetailDtos = orderDto.getOrderDetailDtos();

		final List<LocationWithDistance> locationsWithDistances = computeLocationProcessingOrder(orderDto);

		final List<OrderDetail> orderDetails = computeOrderDetails(orderDetailDtos, locationsWithDistances);

		return orderDetails;
	}

	private List<LocationWithDistance> computeLocationProcessingOrder(final OrderDto orderDto)
			throws JsonProcessingException, JsonMappingException, IOException {
		final List<Location> allShippingLocations = locationService.getAllLocations();

		final List<Double> distances = getDistancesFromTarget(orderDto.getAddress(), allShippingLocations);

		final List<LocationWithDistance> locationsWithDistances = generateLocationsWithDistances(allShippingLocations,
				distances);

		orderLocationsByClosenessToDestination(locationsWithDistances);

		return locationsWithDistances;
	}

	private List<Double> getDistancesFromTarget(final Address targetAddress, final List<Location> allShippingLocations)
			throws JsonProcessingException, JsonMappingException, IOException {
		final RestTemplate restTemplate = new RestTemplate();

		final HttpEntity<String> requestBody = generateRequestBody(targetAddress, allShippingLocations);

		final ResponseEntity<String> response = restTemplate.postForEntity(REQUEST_URL + API_KEY, requestBody,
				String.class);

		final List<Double> distances = extractDistancesFromResponse(response);

		return distances;
	}

	private void orderLocationsByClosenessToDestination(final List<LocationWithDistance> locationsWithDistances) {
		final Comparator<LocationWithDistance> locationWithDistanceComparator = Comparator
				.comparing(LocationWithDistance::getDistanceFromTargetLocation);
		Collections.sort(locationsWithDistances, locationWithDistanceComparator);
	}

	private List<LocationWithDistance> generateLocationsWithDistances(final List<Location> allShippingLocations,
			final List<Double> distances) {
		final List<LocationWithDistance> locationsWithDistances = new ArrayList<>();

		for (int i = 0; i < allShippingLocations.size(); i++) {
			final LocationWithDistance locationWithDistance = new LocationWithDistance(allShippingLocations.get(i),
					distances.get(i));
			locationsWithDistances.add(locationWithDistance);
		}

		return locationsWithDistances;
	}

	private List<Double> extractDistancesFromResponse(final ResponseEntity<String> response)
			throws JsonProcessingException, JsonMappingException, IOException {
		final ObjectMapper mapper = new ObjectMapper();
		final JsonNode root = mapper.readTree(response.getBody());
		final JsonNode distancesNode = root.path("distance");
		final ObjectReader reader = mapper.readerFor(new TypeReference<List<Double>>() {
		});
		final List<Double> distances = reader.readValue(distancesNode);
		removeDistanceFromTargetToItself(distances);

		return distances;
	}

	private void removeDistanceFromTargetToItself(final List<Double> distances) {
		distances.remove(0);
	}

	private HttpEntity<String> generateRequestBody(final Address targetAddress,
			final List<Location> allShippingLocations) {
		final String json = generateRequestInput(allShippingLocations, targetAddress);
		final HttpEntity<String> requestBody = new HttpEntity<>(json);
		return requestBody;
	}

	private void validateRequiredProductsAndQuantitiesCanBeShipped(
			final Map<Integer, Integer> productsAndCorrespondingQuantities) {
		if (!productsAndCorrespondingQuantities.isEmpty()) {
			throw new InsufficientStockException();
		}
	}

	private String generateRequestInput(final List<Location> allShippingLocations, final Address destinationAddress) {
		final StringBuilder json = new StringBuilder();

		json.append(PREFIX);
		json.append(getFormattedAddress(destinationAddress));
		json.append(SEPARATOR);
		json.append(getFormattedLocations(allShippingLocations));
		json.append(POSTFIX);

		return json.toString();
	}

	private String getFormattedLocations(final List<Location> allShippingLocations) {
		final StringBuilder locationsAsJson = new StringBuilder();

		final StringBuilder addressAsString = getFormattedAddress(allShippingLocations.get(0).getAddress());

		locationsAsJson.append(addressAsString);

		for (int i = 1; i < allShippingLocations.size(); i++) {
			locationsAsJson.append(SEPARATOR);
			locationsAsJson.append(getFormattedAddress(allShippingLocations.get(i).getAddress()));
		}

		return locationsAsJson.toString();
	}

	private StringBuilder getFormattedAddress(final Address addressToFormat) {
		final StringBuilder formattedAddress = new StringBuilder();

		formattedAddress.append(DOUBLE_QUOTE);
		formattedAddress.append(addressToFormat.getStreetAddress());
		formattedAddress.append(SEPARATOR);
		formattedAddress.append(addressToFormat.getCity());
		formattedAddress.append(SEPARATOR);
		formattedAddress.append(addressToFormat.getCounty());
		formattedAddress.append(DOUBLE_QUOTE);

		return formattedAddress;
	}

	private List<OrderDetail> computeOrderDetails(final List<OrderDetailDto> orderDetailDtos,
			final List<LocationWithDistance> locationsWithDistances) {
		final List<OrderDetail> orderDetails = new ArrayList<>();

		final Map<Integer, Integer> productsAndCorrespondingQuantities = orderDetailDtos.parallelStream()
				.collect(Collectors.toMap(OrderDetailDto::getProductId, OrderDetailDto::getQuantity));

		while (!productsAndCorrespondingQuantities.isEmpty() && !locationsWithDistances.isEmpty()) {
			final LocationWithDistance currentLocation = locationsWithDistances.get(0);

			final Iterator<Integer> iter = productsAndCorrespondingQuantities.keySet().iterator();
			while (iter.hasNext()) {
				processRequestForCurrentProduct(productsAndCorrespondingQuantities, orderDetails, currentLocation,
						iter);
			}

			locationsWithDistances.remove(currentLocation);
		}

		validateRequiredProductsAndQuantitiesCanBeShipped(productsAndCorrespondingQuantities);

		return orderDetails;
	}

	private void processRequestForCurrentProduct(final Map<Integer, Integer> productsAndCorrespondingQuantities,
			final List<OrderDetail> orderDetails, final LocationWithDistance currentLocation,
			final Iterator<Integer> iter) {
		final Integer productId = iter.next();

		final Integer totalDesiredQuantity = productsAndCorrespondingQuantities.get(productId);

		final Integer actualQuantityTakenFromCurrentLocation = locationService.getWithdrawnQuantity(productId,
				totalDesiredQuantity, currentLocation.getLocation());

		createNewOrderDetailWhenRequired(orderDetails, currentLocation, productId,
				actualQuantityTakenFromCurrentLocation);

		updateRemainingProductsAndQuantities(productsAndCorrespondingQuantities, productId, totalDesiredQuantity,
				actualQuantityTakenFromCurrentLocation, iter);
	}

	private void createNewOrderDetailWhenRequired(final List<OrderDetail> orderDetails,
			final LocationWithDistance currentLocation, final Integer productId,
			final Integer actualQuantityTakenFromCurrentLocation) {
		final boolean productWasAvailable = actualQuantityTakenFromCurrentLocation > 0;

		if (productWasAvailable) {
			final Product product = productService.getProduct(productId).get();
			final Location location = currentLocation.getLocation();
			orderDetails.add(new OrderDetail(null, product, location, actualQuantityTakenFromCurrentLocation));
		}
	}

	private void updateRemainingProductsAndQuantities(final Map<Integer, Integer> productsAndCorrespondingQuantities,
			final Integer productId, final Integer totalDesiredQuantity,
			final Integer actualQuantityTakenFromCurrentLocation, final Iterator<Integer> iter) {
		if (totalDesiredQuantity == actualQuantityTakenFromCurrentLocation) {
			iter.remove();
		} else {
			final int remainingQuantity = totalDesiredQuantity - actualQuantityTakenFromCurrentLocation;
			productsAndCorrespondingQuantities.replace(productId, remainingQuantity);
		}
	}
}
