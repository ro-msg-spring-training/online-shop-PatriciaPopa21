package ro.msg.learning.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import ro.msg.learning.entity.Revenue;
import ro.msg.learning.service.interfaces.RevenueService;

@RestController
@RequiredArgsConstructor
public class RevenueController {
	private final RevenueService revenueService;

	@GetMapping(path = "/revenue", produces = "application/json")
	public List<Revenue> getRevenueForDate(
			@RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) final LocalDate date) {
		return revenueService.getRevenuesForDate(date);
	}
}
