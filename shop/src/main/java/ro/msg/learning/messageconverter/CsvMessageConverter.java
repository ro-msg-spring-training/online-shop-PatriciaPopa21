package ro.msg.learning.messageconverter;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractGenericHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.stereotype.Component;

import lombok.SneakyThrows;
import ro.msg.learning.export.CsvExportManager;

@Component
public class CsvMessageConverter<T> extends AbstractGenericHttpMessageConverter<List<T>> {
	private static final MediaType SUPPORTED_MEDIA_TYPE = new MediaType("text", "csv", Charset.forName("utf-8"));
	private CsvExportManager exportManager;

	public CsvMessageConverter(final CsvExportManager exportManager) {
		super(SUPPORTED_MEDIA_TYPE);
		this.exportManager = exportManager;
	}

	@Override
	@SneakyThrows
	public boolean canRead(final Type type, final Class<?> clazz, final MediaType mediaType) {
		if (type instanceof ParameterizedType) {
			final ParameterizedType parameterizedType = (ParameterizedType) type;
			return mediaType != null && super.canRead(mediaType) && mediaType.equals(SUPPORTED_MEDIA_TYPE)
					&& Class.forName(parameterizedType.getRawType().getTypeName()).isAssignableFrom(List.class);
		}
		return false;
	}

	@Override
	public boolean canWrite(final Type type, final Class<?> clazz, final MediaType mediaType) {
		final Class listType = type instanceof Class ? type.getClass()
				: (Class) ((ParameterizedType) type).getRawType();

		if (!listType.isAssignableFrom(List.class)) {
			return false;
		}

		return this.canWrite(clazz, mediaType);
	}

	@Override
	public List<T> read(final Type type, final Class<?> clazz, final HttpInputMessage input)
			throws IOException, HttpMessageNotReadableException {
		final Class listElementsType = (Class) ((ParameterizedType) type).getActualTypeArguments()[0];
		return exportManager.fromCsv(listElementsType, input.getBody());
	}

	@Override
	protected List<T> readInternal(final Class<? extends List<T>> clazz, final HttpInputMessage input)
			throws IOException, HttpMessageNotReadableException {
		return new ArrayList<>();
	}

	@Override
	protected void writeInternal(final List<T> objectsToBeSerialized, final Type type, final HttpOutputMessage output)
			throws IOException, HttpMessageNotWritableException {
		final Class listElementsType = (Class) ((ParameterizedType) type).getActualTypeArguments()[0];
		exportManager.toCsv(listElementsType, objectsToBeSerialized, output.getBody());
	}
}