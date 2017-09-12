package com.textbookvalet.rest.converters;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractGenericHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.util.Assert;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.reflect.TypeToken;
import com.textbookvalet.commons.annotations.CustomResponse;

public class CustomJsonConverter extends AbstractGenericHttpMessageConverter<Object> {

	public static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");
	public static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX";

	private Gson gson;

	private String jsonPrefix;

	/**
	 * Construct a new {@code GsonHttpMessageConverter}.
	 */
	public CustomJsonConverter() {
		super(MediaType.APPLICATION_JSON, new MediaType("application", "*+json"));
		this.setDefaultCharset(DEFAULT_CHARSET);

		JsonDeserializer<Integer> integerDeserializer = new JsonDeserializer<Integer>() {
			@Override
			public Integer deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
					throws JsonParseException {
				if (json == null) {
					return null;
				}

				try {
					return Integer.parseInt(json.getAsString());
				} catch (Exception e) {
					return null;
				}
			}
		};

		JsonDeserializer<Long> longDeserializer = new JsonDeserializer<Long>() {
			@Override
			public Long deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
					throws JsonParseException {
				if (json == null) {
					return null;
				}

				try {
					return Long.parseLong(json.getAsString());
				} catch (Exception e) {
					return null;
				}
			}
		};

		JsonSerializer<Date> dateSerializer = new JsonSerializer<Date>() {
			@Override
			public JsonElement serialize(Date src, Type typeOfSrc, JsonSerializationContext context) {
				return src == null ? null : new JsonPrimitive(new SimpleDateFormat(DATE_FORMAT).format(src));
			}
		};

		JsonDeserializer<Date> dateDeserializer = new JsonDeserializer<Date>() {
			@Override
			public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
					throws JsonParseException {
				return json == null ? null : new Date(json.getAsLong());
			}
		};

		this.gson = new GsonBuilder()
				.serializeNulls() 
				.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
				.registerTypeAdapter(Date.class, dateSerializer).registerTypeAdapter(Date.class, dateDeserializer)
				.registerTypeAdapter(Integer.class, integerDeserializer)
				.registerTypeAdapter(int.class, integerDeserializer).registerTypeAdapter(Long.class, longDeserializer)
				.registerTypeAdapter(long.class, longDeserializer)
				.create(); 
	}
	
	@Override
	protected boolean supports(Class<?> clazz) {
		/*
		 This hack is needed in order to make Swagger works with our CustomJsonConverter.
		 Swagger need to use only native Converter 'MappingJackson2HttpMessageConverter' and can't work with any other converters. 
		 */
		if (clazz.getCanonicalName().contains("springfox.documentation")) {
			return false;
		}
		
		return true;
	}

	/**
	 * Set the {@code Gson} instance to use. If not set, a default
	 * {@link Gson#Gson() Gson} instance is used.
	 * <p>
	 * Setting a custom-configured {@code Gson} is one way to take further
	 * control of the JSON serialization process.
	 */
	public void setGson(Gson gson) {
		Assert.notNull(gson, "'gson' is required");
		this.gson = gson;
	}

	/**
	 * Return the configured {@code Gson} instance for this converter.
	 */
	public Gson getGson() {
		return this.gson;
	}

	/**
	 * Specify a custom prefix to use for JSON output. Default is none.
	 * 
	 * @see #setPrefixJson
	 */
	public void setJsonPrefix(String jsonPrefix) {
		this.jsonPrefix = jsonPrefix;
	}

	/**
	 * Indicate whether the JSON output by this view should be prefixed with
	 * ")]}', ". Default is {@code false}.
	 * <p>
	 * Prefixing the JSON string in this manner is used to help prevent JSON
	 * Hijacking. The prefix renders the string syntactically invalid as a
	 * script so that it cannot be hijacked. This prefix should be stripped
	 * before parsing the string as JSON.
	 * 
	 * @see #setJsonPrefix
	 */
	public void setPrefixJson(boolean prefixJson) {
		this.jsonPrefix = (prefixJson ? ")]}', " : null);
	}

	@Override
	public Object read(Type type, Class<?> contextClass, HttpInputMessage inputMessage)
			throws IOException, HttpMessageNotReadableException {

		TypeToken<?> token = getTypeToken(type);
		return readTypeToken(token, inputMessage);
	}

	@Override
	protected Object readInternal(Class<?> clazz, HttpInputMessage inputMessage)
			throws IOException, HttpMessageNotReadableException {

		TypeToken<?> token = getTypeToken(clazz);
		return readTypeToken(token, inputMessage);
	}

	/**
	 * Return the Gson {@link TypeToken} for the specified type.
	 * <p>
	 * The default implementation returns {@code TypeToken.get(type)}, but this
	 * can be overridden in subclasses to allow for custom generic collection
	 * handling. For instance:
	 * 
	 * <pre class="code">
	 * protected TypeToken<?> getTypeToken(Type type) {
	 * 	if (type instanceof Class && List.class.isAssignableFrom((Class<?>) type)) {
	 * 		return new TypeToken<ArrayList<MyBean>>() {
	 * 		};
	 * 	} else {
	 * 		return super.getTypeToken(type);
	 * 	}
	 * }
	 * </pre>
	 * 
	 * @param type
	 *            the type for which to return the TypeToken
	 * @return the type token
	 * @deprecated as of Spring Framework 4.3.8, in favor of signature-based
	 *             resolution
	 */
	@Deprecated
	protected TypeToken<?> getTypeToken(Type type) {
		return TypeToken.get(type);
	}

	private Object readTypeToken(TypeToken<?> token, HttpInputMessage inputMessage) throws IOException {
		Reader json = new InputStreamReader(inputMessage.getBody(), getCharset(inputMessage.getHeaders()));
		try {
			return this.gson.fromJson(json, token.getType());
		} catch (JsonParseException ex) {
			throw new HttpMessageNotReadableException("JSON parse error: " + ex.getMessage(), ex);
		}
	}

	private Charset getCharset(HttpHeaders headers) {
		if (headers == null || headers.getContentType() == null || headers.getContentType().getCharset() == null) {
			return DEFAULT_CHARSET;
		}
		return headers.getContentType().getCharset();
	}

	@Override
	protected void writeInternal(Object o, Type type, HttpOutputMessage outputMessage)
			throws IOException, HttpMessageNotWritableException {

		Object obj = o;

		if (type != null) {
			// This is to handle list of model object and model having
			// annotation
			if (o instanceof List) {
				List<?> childObj = (List<?>) o;

				if (!childObj.isEmpty()
						&& findAnnotation(CustomResponse.class, childObj.get(0).getClass().getAnnotations()) != null) {
					obj = new JsonWrapper(o);
				}
			} else if (findAnnotation(CustomResponse.class, o.getClass().getAnnotations()) != null) {
				obj = new JsonWrapper(o);
			}
		}

		/*
		 * if (type != null && findAnnotation(CustomResponse.class,
		 * o.getClass().getAnnotations()) != null) { obj = new JsonWrapper(o); }
		 */

		Charset charset = getCharset(outputMessage.getHeaders());
		OutputStreamWriter writer = new OutputStreamWriter(outputMessage.getBody(), charset);
		try {
			if (this.jsonPrefix != null) {
				writer.append(this.jsonPrefix);
			}
			/*
			 * if (type != null) { this.gson.toJson(obj, type, writer); }
			 */
			else {
				this.gson.toJson(obj, writer);
			}
			writer.close();
		} catch (JsonIOException ex) {
			throw new HttpMessageNotWritableException("Could not write JSON: " + ex.getMessage(), ex);
		}
	}

	protected Annotation findAnnotation(Class<? extends Annotation> clazz, Annotation[] annotations) {
		for (Annotation a : annotations) {
			if (clazz.isAssignableFrom(a.getClass())) {
				return a;
			}
		}
		return null;
	}

}
