package ro.msg.learning.service.impl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import ro.msg.learning.entity.Location;
import ro.msg.learning.entity.Revenue;
import ro.msg.learning.repository.RevenueRepository;
import ro.msg.learning.repository.RevenueRepository.UnprocessedRevenueInfo;
import ro.msg.learning.service.interfaces.LocationService;
import ro.msg.learning.service.interfaces.RevenueService;

@Service
@RequiredArgsConstructor
public class RevenueServiceImpl implements RevenueService {
	private static final String SQL_PLACEHOLDER = "%";
	
	private final RevenueRepository revenueRepository;
	private final LocationService locationService;

	@Override
	public List<Revenue> getRevenuesForDate(final LocalDate date) {
		return revenueRepository.getRevenuesForDate(getDateAsSqlString(date));
	}

	@Override
	public void processTodaysRevenue() {
		final LocalDate todaysDate = LocalDate.now();

		final List<UnprocessedRevenueInfo> unprocessedRevenueData = revenueRepository
				.getRevenueInfoForDate(getDateAsSqlString(LocalDate.now()));

		final Map<Integer, List<UnprocessedRevenueInfo>> revenueDataGroupedByLocation = getRevenueDataGroupedByLocation(
				unprocessedRevenueData);

		final Map<Integer, BigDecimal> todaysProfitsPerLocation = computeTodaysProfitsForEachLocation(
				revenueDataGroupedByLocation);

		persistTodaysRevenues(todaysDate, todaysProfitsPerLocation);
	}

	private String getDateAsSqlString(final LocalDate date) {
		final StringBuilder formattedDate = new StringBuilder();
		formattedDate.append(date.toString());
		formattedDate.append(SQL_PLACEHOLDER);

		return formattedDate.toString();
	}
	
	private Map<Integer, List<UnprocessedRevenueInfo>> getRevenueDataGroupedByLocation(
			final List<UnprocessedRevenueInfo> unprocessedRevenueData) {
		final Map<Integer, List<UnprocessedRevenueInfo>> revenueDataGroupedByLocation = new HashMap<>();

		for (final UnprocessedRevenueInfo currentRevenueInfoRow : unprocessedRevenueData) {
			final Integer currentLocationId = currentRevenueInfoRow.getLocationId();

			if (!revenueDataGroupedByLocation.containsKey(currentLocationId)) {
				revenueDataGroupedByLocation.put(currentLocationId, new ArrayList<>());
			}

			final List<UnprocessedRevenueInfo> revenueInfoForCurrentLocation = revenueDataGroupedByLocation
					.get(currentLocationId);
			revenueInfoForCurrentLocation.add(currentRevenueInfoRow);
		}

		return revenueDataGroupedByLocation;
	}

	private Map<Integer, BigDecimal> computeTodaysProfitsForEachLocation(
			final Map<Integer, List<UnprocessedRevenueInfo>> revenueDataGroupedByLocation) {
		final Map<Integer, BigDecimal> todaysProfitsPerLocation = new HashMap<>();

		for (final Integer locationId : revenueDataGroupedByLocation.keySet()) {
			final List<UnprocessedRevenueInfo> revenueInfoForCurrentLocation = revenueDataGroupedByLocation
					.get(locationId);

			final BigDecimal todaysTotalProfitForCurrentLocation = computeTodaysTotalProfitForCurrentLocation(
					revenueInfoForCurrentLocation);

			todaysProfitsPerLocation.put(locationId, todaysTotalProfitForCurrentLocation);
		}

		return todaysProfitsPerLocation;
	}

	private BigDecimal computeTodaysTotalProfitForCurrentLocation(
			final List<UnprocessedRevenueInfo> unprocessedRevenueData) {
		BigDecimal todaysTotalProfitForCurrentLocation = new BigDecimal(0);

		for (final UnprocessedRevenueInfo unprocessedRevenueInfo : unprocessedRevenueData) {
			final BigDecimal pricePerUnitOfCurrentProduct = unprocessedRevenueInfo.getPricePerUnit();
			final BigDecimal quantityForCurrentProduct = new BigDecimal(unprocessedRevenueInfo.getQuantity());
			final BigDecimal newProfit = pricePerUnitOfCurrentProduct.multiply(quantityForCurrentProduct);
			todaysTotalProfitForCurrentLocation = todaysTotalProfitForCurrentLocation.add(newProfit);
		}
		return todaysTotalProfitForCurrentLocation;
	}

	private void persistTodaysRevenues(final LocalDate todaysDate,
			final Map<Integer, BigDecimal> todaysProfitsPerLocation) {
		for (final Integer locationId : todaysProfitsPerLocation.keySet()) {
			final Location location = locationService.getLocation(locationId);
			final Revenue newRevenueEntry = new Revenue(location, todaysDate, todaysProfitsPerLocation.get(locationId));
			revenueRepository.save(newRevenueEntry);
		}
	}
}
