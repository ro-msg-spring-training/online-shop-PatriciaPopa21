//package ro.msg.learning.export;
//
//import java.io.InputStream;
//import java.io.OutputStream;
//import java.util.List;
//
//import org.springframework.stereotype.Component;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.MappingIterator;
//import com.fasterxml.jackson.dataformat.csv.CsvMapper;
//import com.fasterxml.jackson.dataformat.csv.CsvSchema;
//
//import lombok.SneakyThrows;
//
//@Component
//public class CsvExportManager {
//	private final CsvMapper csvMapper;
//
//	public CsvExportManager(final CsvMapper csvMapper) {
//		this.csvMapper = csvMapper;
//	}
//
//	@SneakyThrows
//	public <T> void toCsv(final Class<T> clazz, final List<T> pojosToSerialize, final OutputStream outputStream) throws JsonProcessingException {
//		final CsvSchema schema = csvMapper.schemaFor(clazz).withHeader();
//		csvMapper.writer(schema).writeValue(outputStream, pojosToSerialize);
//	}
//
//	@SneakyThrows
//	public <T> List<T> fromCsv(final Class<T> clazz, final InputStream inputStream) {
//		final CsvSchema schema = csvMapper.schemaFor(clazz).withHeader();
//		final MappingIterator<T> it = csvMapper.readerFor(clazz).with(schema)
//				.readValues(inputStream);
//		final List<T> allPojos = it.readAll();
//		return allPojos;
//	}
//}
//
