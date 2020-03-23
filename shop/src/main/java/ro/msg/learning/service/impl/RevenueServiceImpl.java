package ro.msg.learning.service.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import ro.msg.learning.entity.Location;
import ro.msg.learning.entity.Revenue;
import ro.msg.learning.repository.RevenueRepository;
import ro.msg.learning.repository.RevenueRepository.ProfitPerLocation;
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

		final List<ProfitPerLocation> todaysProfitsPerLocation = revenueRepository
				.getRevenueInfoForDate(getDateAsSqlString(LocalDate.now()));

		persistTodaysRevenues(todaysDate, todaysProfitsPerLocation);
	}

	private String getDateAsSqlString(final LocalDate date) {
		final StringBuilder formattedDate = new StringBuilder();
		formattedDate.append(date.toString());
		formattedDate.append(SQL_PLACEHOLDER);

		return formattedDate.toString();
	}
	
	private void persistTodaysRevenues(final LocalDate todaysDate,
			final List<ProfitPerLocation> todaysProfitsPerLocation) {
		
		final List<Revenue> revenueEntries = new ArrayList<>();
		
		for (ProfitPerLocation profitPerLocation : todaysProfitsPerLocation) {
			final Location location = locationService.getLocation(profitPerLocation.getLocationId());
			final Revenue newRevenueEntry = new Revenue(location, todaysDate, profitPerLocation.getProfit());
			revenueEntries.add(newRevenueEntry);
		}
		
		revenueRepository.saveAll(revenueEntries);
	}
}
