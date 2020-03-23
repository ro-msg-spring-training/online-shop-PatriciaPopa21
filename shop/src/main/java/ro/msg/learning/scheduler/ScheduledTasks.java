package ro.msg.learning.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import ro.msg.learning.service.interfaces.RevenueService;

@Component
@RequiredArgsConstructor
public class ScheduledTasks {

	private final RevenueService revenueService;

	private static final Logger LOG = LoggerFactory.getLogger(ScheduledTasks.class);

	/* this will perform the job starting at 23:00:00, every day */
	@Scheduled(cron = "0 0 23 * * *")
	public void performRevenueUpdateJob() {
		LOG.info("Starting to perform revenue update job...");
		revenueService.processTodaysRevenue();
		LOG.info("Perform revenue update finalized successfully.");
	}
}