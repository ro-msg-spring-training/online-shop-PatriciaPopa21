package ro.msg.learning.shop;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.fasterxml.jackson.core.JsonProcessingException;

import ro.msg.learning.entity.Address;
import ro.msg.learning.entity.Location;
import ro.msg.learning.entity.Product;
import ro.msg.learning.entity.ProductCategory;
import ro.msg.learning.entity.Stock;
import ro.msg.learning.entity.Supplier;
import ro.msg.learning.export.CsvExportManager;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class CsvExportManagerTests {
	private static final String CSV_EXPORT_AS_STRING = "locationName,country,city,county,streetAddress,productName,productDescription,price,weight,categoryName,categoryDescription,supplierName,imageUrl,quantity\n"
			+ "\"Cladirea A\",Romania,Timisoara,Timis,\"Str. Gh. Lazar nr. 2\",\"Jane Eyre\",\"A nice book\",25,40.0,Books,\"Classical literature\",Elefant.ro,/janeEyre,5\n"
			+ "\"Cladirea A\",Romania,Timisoara,Timis,\"Str. Gh. Lazar nr. 2\",\"GShock X33\",\"A nice watch\",200,120.9,Watches,\"Hand watch\",Emag,/gShockX33,0\n";
	@Autowired
	private CsvExportManager csvExportManager;

	@Test
	void testConversionFromJavaObjectToCsv() throws JsonProcessingException {
		final List<Stock> stocksToSerialize = getObjectsToBeSerialized();
		final OutputStream outputStream = new ByteArrayOutputStream();
		csvExportManager.toCsv(Stock.class, stocksToSerialize, outputStream);
		assertEquals(CSV_EXPORT_AS_STRING, outputStream.toString());
	}

	@Test
	void testConversionFromCsvToJavaObject() throws JsonProcessingException {
		final List<Stock> expected = getObjectsToBeSerialized();
		final InputStream inputStream = new ByteArrayInputStream(CSV_EXPORT_AS_STRING.getBytes());
		final List<Stock> actual = csvExportManager.fromCsv(Stock.class, inputStream);
		assertEquals(expected, actual);
	}

	private List<Stock> getObjectsToBeSerialized() {
		final Supplier supplier1 = new Supplier("Elefant.ro");
		final ProductCategory productCategory1 = new ProductCategory("Books", "Classical literature");
		final Product product1 = new Product("Jane Eyre", "A nice book", new BigDecimal(25), 40.0, productCategory1,
				supplier1, "/janeEyre");
		final Address address1 = new Address("Romania", "Timisoara", "Timis", "Str. Gh. Lazar nr. 2");
		final Location location1 = new Location("Cladirea A", address1);
		final Stock stock1 = new Stock(location1, product1, 5);

		final Supplier supplier2 = new Supplier("Emag");
		final ProductCategory productCategory2 = new ProductCategory("Watches", "Hand watch");
		final Product product2 = new Product("GShock X33", "A nice watch", new BigDecimal(200), 120.9, productCategory2,
				supplier2, "/gShockX33");
		final Stock stock2 = new Stock(location1, product2, 0);

		final List<Stock> stocksToSerialize = Arrays.asList(stock1, stock2);
		return stocksToSerialize;
	}
}
