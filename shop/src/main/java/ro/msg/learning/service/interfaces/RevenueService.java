package ro.msg.learning.service.interfaces;

import java.time.LocalDate;
import java.util.List;

import ro.msg.learning.entity.Revenue;

public interface RevenueService {
	List<Revenue> getRevenuesForDate(LocalDate date);

	void processTodaysRevenue();
}
