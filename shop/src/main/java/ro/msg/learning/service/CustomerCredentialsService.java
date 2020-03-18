package ro.msg.learning.service;

import java.util.List;

import ro.msg.learning.entity.CustomerCredentials;

public interface CustomerCredentialsService {
	List<CustomerCredentials> getCredentialsForAllUsers();
}
