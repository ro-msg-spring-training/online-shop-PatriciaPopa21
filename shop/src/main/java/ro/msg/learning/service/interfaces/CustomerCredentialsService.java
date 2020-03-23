package ro.msg.learning.service.interfaces;

import java.util.List;

import ro.msg.learning.entity.CustomerCredentials;

public interface CustomerCredentialsService {
	List<CustomerCredentials> getCredentialsForAllUsers();
}
