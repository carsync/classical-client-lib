package de.vispiron.carsync.classical.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import de.vispiron.carsync.classical.converter.modelmapper.CustomInstantToStringConverter;
import de.vispiron.carsync.classical.converter.modelmapper.CustomStringToInstantConverter;
import de.vispiron.carsync.classical.converter.objectmapper.CustomBooleanDeserializer;
import de.vispiron.carsync.classical.domain.CarsyncDomain;
import de.vispiron.carsync.classical.domain.CarsyncServerException;
import de.vispiron.carsync.classical.dto.CarsyncDTO;
import de.vispiron.carsync.classical.dto.CarsyncServerExceptionDTO;
import de.vispiron.carsync.classical.dto.PaginationRangeRequest;
import de.vispiron.carsync.classical.dto.PaginationRangeResponse;
import org.modelmapper.ModelMapper;

import java.io.IOException;
import java.io.Serializable;
import java.net.*;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public abstract class RestServiceImpl<T extends CarsyncDomain> implements RestService<T> {

	protected static ModelMapper modelMapper;

	protected static HttpClient httpClient;

	private static ObjectMapper objectMapper;

	static {
		httpClient = HttpClient.newBuilder()
				.version(HttpClient.Version.HTTP_2)
				.followRedirects(HttpClient.Redirect.NORMAL)
				.connectTimeout(Duration.ofSeconds(20))
				.build();

		objectMapper = new ObjectMapper();
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		SimpleModule module = new SimpleModule();
		module.addDeserializer(Boolean.class, new CustomBooleanDeserializer());
		objectMapper.registerModule(module);

		modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setFieldMatchingEnabled(true);
		modelMapper.addConverter(new CustomInstantToStringConverter());
		modelMapper.addConverter(new CustomStringToInstantConverter());
	}

	protected final URL endpointUrl;

	protected final Class<T> domainClass;

	protected final Class<? extends CarsyncDTO> responseDtoClass;

	protected final Class<? extends CarsyncDTO> createDtoClass;

	protected final Class<? extends CarsyncDTO> updateDtoClass;

	private final CarsyncSession carsyncSession;

	public RestServiceImpl(CarsyncSession carsyncSession,
			String endpoint,
			Class<T> domainClass,
			Class<? extends CarsyncDTO> responseDtoClass,
			Class<? extends CarsyncDTO> createDtoClass,
			Class<? extends CarsyncDTO> updateDtoClass) {

		try {
			this.endpointUrl = new URL(new URL(carsyncSession.getBaseUrl()), endpoint);
		} catch (MalformedURLException e) {
			throw new RuntimeException("invalid URL");
		}
		this.carsyncSession = carsyncSession;
		this.domainClass = domainClass;
		this.responseDtoClass = responseDtoClass;
		this.createDtoClass = createDtoClass;
		this.updateDtoClass = updateDtoClass;
	}

	@Override
	public T save(T item) {
		return save(item, new HashMap<>());
	}

	@Override
	public T save(T item, Map<String, Serializable> params) {
		if (this.createDtoClass == null) {
			throw new RuntimeException("This API cannot be used to POST");
		}

		URI uri = getUri("", params);

		CarsyncDTO updateDto = modelMapper.map(item, this.createDtoClass);

		String body;
		try {
			body = objectMapper.writeValueAsString(updateDto);
		} catch (JsonProcessingException e) {
			throw new RuntimeException("IOException occurred: Cannot serialise payload");
		}

		HttpRequest.Builder request = HttpRequest.newBuilder(uri).POST(HttpRequest.BodyPublishers.ofString(body));

		HttpResponse<String> httpResponse = getStringHttpResponse(request);

		CarsyncDTO responseDto;
		try {
			responseDto = objectMapper.readValue(httpResponse.body(), this.responseDtoClass);
		} catch (IOException ex) {
			throw new RuntimeException(
					"IOException occurred: The response from the service is invalid");
		}

		modelMapper.map(responseDto, item);
		item.setDatabaseState(responseDto);
		return item;
	}

	@Override
	public T get(Integer id) {
		return get(id, new HashMap<>());
	}

	@Override
	public T get(Integer id, Map<String, Serializable> params) {
		return get(String.valueOf(id), params);
	}

	@Override
	public T get(String id) {
		return get(id, new HashMap<>());
	}

	@Override
	public T get(String id, Map<String, Serializable> params) {
		if (this.responseDtoClass == null) {
			throw new RuntimeException("This API cannot be used to GET");
		}

		URI uri = getUri("/" + id, params);

		HttpRequest.Builder request = HttpRequest.newBuilder(uri).GET();

		HttpResponse<String> httpResponse = getStringHttpResponse(request);

		CarsyncDTO responseDto;
		try {
			responseDto = objectMapper.readValue(httpResponse.body(), this.responseDtoClass);
		} catch (IOException ex) {
			throw new RuntimeException(
					"IOException occurred: The response from the service is invalid");
		}
		T domain = modelMapper.map(responseDto, this.domainClass);
		domain.setDatabaseState(responseDto);
		return domain;
	}

	@Override
	public Page<T> getList() {
		return getList(new PaginationRangeRequest(0, 49));
	}

	@Override
	public Page<T> getList(Map<String, Serializable> params) {
		return getList(params, new PaginationRangeRequest(0, 49));
	}

	@Override
	public Page<T> getList(PaginationRangeRequest range) {
		return getList(new HashMap<>(), range);
	}

	@Override
	public Page<T> getList(Map<String, Serializable> params, PaginationRangeRequest range) {
		if (this.responseDtoClass == null) {
			throw new RuntimeException("This API cannot be used to GET");
		}

		URI uri = getUri("", params);

		HttpRequest.Builder request = HttpRequest.newBuilder(uri).GET();

		HttpResponse<String> httpResponse = getStringHttpResponse(request);

		List<CarsyncDTO> responseDtoList;
		CollectionType typeReference =
				TypeFactory.defaultInstance().constructCollectionType(List.class, this.responseDtoClass);
		try {
			responseDtoList = objectMapper.readValue(httpResponse.body(), typeReference);
		} catch (IOException ex) {
			throw new RuntimeException(
					"IOException occurred: The response from the service is invalid");
		}

		/*
		// this.domainClass.co
		// Type listType = TypeFactory.defaultInstance().constructCollectionType(List.class, this.domainClass);
		Type listType = new TypeToken<List<T>>(){}.getType();
		//Class<? extends CollectionType> rawCollectionClass = listType.getClass();

		//T[] responseList = modelMapper.map(responseDtoList, arrayType);
		List<T> responseList = modelMapper.map(responseDtoList, listType);
		T[] responseArray = modelMapper.map(responseDtoList, this.responseDtoClass);
		//List<T> responseList = modelMapper.map(responseDtoList, rawCollectionClass);

		 */
		List<T> responseList = responseDtoList
				.stream()
				.map(source -> {
					T domain = modelMapper.map(source, this.domainClass);
					domain.setDatabaseState(source);
					return domain;
				})
				.collect(Collectors.toList());

		PaginationRangeResponse rangeResponse = new PaginationRangeResponse(httpResponse.headers()
				.firstValue("Content-Range")
				.orElse(null));

		Page<T> page = new Page<>(responseList, rangeResponse.getFirst(), rangeResponse.getTotal());

		return page;
	}

	private URI getUri(String route, Map<String, Serializable> params) {
		URI uri;
		try {
			uri = this.endpointUrl.toURI();
		} catch (URISyntaxException e) {
			throw new RuntimeException("URISyntaxException");
		}

		if (params == null) {
			return uri;
		}

		String appendQuery = params.keySet()
				.stream()
				.map(key -> {
					if (params.get(key) instanceof String) {
						return key + "=" + URLEncoder.encode(String.valueOf(params.get(key)), StandardCharsets.UTF_8);
					} else {
						return key + "=" + params.get(key);
					}
				})
				.collect(Collectors.joining("&"));

		String newQuery = uri.getQuery();
		if (newQuery == null) {
			newQuery = appendQuery;
		} else {
			newQuery += "&" + appendQuery;
		}
		try {
			return new URI(uri.getScheme(), uri.getAuthority(),
					uri.getPath() + route, newQuery, uri.getFragment());
		} catch (URISyntaxException e) {
			throw new RuntimeException("URISyntaxException");
		}
	}

	private HttpResponse<String> getStringHttpResponse(HttpRequest.Builder requestBuilder) {
		HttpResponse<String> httpResponse;

		requestBuilder.setHeader("Cookie", "XDEBUG_SESSION=PHPSTORM;");

		if (this.carsyncSession.getSessionToken() != null) {
			requestBuilder.setHeader("Cookie",
					"XDEBUG_SESSION=PHPSTORM; PHPSESSID=" + this.carsyncSession.getSessionToken());
		} else if (this.carsyncSession.getUsername() != null && this.carsyncSession.getPassword() != null) {
			String authStr = this.carsyncSession.getUsername() + ":" + this.carsyncSession.getPassword();
			String authEncoded = Base64.getEncoder().encodeToString(authStr.getBytes());
			requestBuilder.setHeader("Authorization", "Basic " + authEncoded);
		} else {
			throw new RuntimeException("username or password missing");
		}

		requestBuilder.setHeader("Content-Type", "application/json");
		requestBuilder.setHeader("Accept", "application/json");

		HttpRequest request = requestBuilder.build();

		try {
			httpResponse = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
		} catch (IOException | InterruptedException e) {
			throw new RuntimeException("Error while fetching http response. Firewall? Proxy?");
		}

		if (httpResponse.statusCode() == 401 && this.carsyncSession.getSessionToken() != null) {
			// maybe session expired. retry to log in
			this.carsyncSession.setSessionToken(null);
			requestBuilder.setHeader("Cookie", null);
			return getStringHttpResponse(requestBuilder);
		}

		if (httpResponse.statusCode() >= 400) {
			// error in some way. Try to put it in a CarsyncServerException.
			CarsyncServerExceptionDTO serverExceptionDto;
			try {
				serverExceptionDto = objectMapper.readValue(httpResponse.body(), CarsyncServerExceptionDTO.class);
			} catch (JsonProcessingException e) {
				serverExceptionDto = new CarsyncServerExceptionDTO();
				serverExceptionDto.error = "unable to parse error response";
				serverExceptionDto.requestId = httpResponse.headers().firstValue("X-Request-Id").orElse(null);
			}

			throw new CarsyncServerException(serverExceptionDto.error,
					httpResponse.statusCode(),
					serverExceptionDto.requestId);
		}

		if (this.carsyncSession.getSessionToken() == null) {
			// just logged in
			String cookie = httpResponse.headers().firstValue("set-cookie").orElse("");
			Pattern cookieRegex = Pattern.compile("PHPSESSID\\s*=\\s*(?<sessionId>[\\da-zA-Z]+)\\s*;");
			Matcher cookieMatcher = cookieRegex.matcher(cookie);
			if (cookieMatcher.find()) {
				String sessionId = cookieMatcher.group("sessionId");
				this.carsyncSession.setSessionToken(sessionId);
			}
		}

		return httpResponse;
	}

	@Override
	public T update(T item) {
		return this.update(item, new HashMap<>());
	}

	@Override
	public T update(T item, Map<String, Serializable> params) {
		return this.update(item, item.getIdentifier(), params);
	}

	@Override
	public T update(T item, Integer id) {
		return this.update(item, String.valueOf(id), new HashMap<>());
	}

	@Override
	public T update(T item, Integer id, Map<String, Serializable> params) {
		return this.update(item, String.valueOf(id), params);
	}

	@Override
	public T update(T item, String id) {
		return this.update(item, id, new HashMap<>());
	}

	@Override
	public T update(T item, String id, Map<String, Serializable> params) {
		if (this.updateDtoClass == null) {
			throw new RuntimeException("This API cannot be used to PUT");
		}

		URI uri = getUri("/" + id, params);

		CarsyncDTO updateDto = modelMapper.map(item, this.updateDtoClass);
		CarsyncDTO updateDto2 = modelMapper.map(item.getDatabaseState(), this.updateDtoClass);

		String body;
		try {
			ObjectNode obj1 = objectMapper.valueToTree(updateDto);
			ObjectNode obj2 = objectMapper.valueToTree(updateDto2);
			ObjectNode changes = JsonDiff.diff(obj1, obj2);

			body = objectMapper.writeValueAsString(changes);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(
					"IOException occurred: Cannot serialise payload");
		}

		HttpRequest.Builder request = HttpRequest.newBuilder(uri).PUT(HttpRequest.BodyPublishers.ofString(body));

		HttpResponse<String> httpResponse = getStringHttpResponse(request);

		CarsyncDTO responseDto;
		try {
			responseDto = objectMapper.readValue(httpResponse.body(), this.responseDtoClass);
		} catch (IOException ex) {
			throw new RuntimeException(
					"IOException occurred: The response from the service is invalid");
		}

		modelMapper.map(responseDto, item);
		item.setDatabaseState(responseDto);
		return item;
	}

	@Override
	public void delete(T item) {
		this.delete(item, new HashMap<>());
	}

	@Override
	public void delete(T item, Map<String, Serializable> params) {
		this.delete(item.getIdentifier(), params);
	}

	@Override
	public void delete(Integer id) {
		this.delete(String.valueOf(id), new HashMap<>());
	}

	@Override
	public void delete(Integer id, Map<String, Serializable> params) {
		this.delete(String.valueOf(id), params);
	}

	@Override
	public void delete(String id) {
		this.delete(id, new HashMap<>());
	}

	@Override
	public void delete(String id, Map<String, Serializable> params) {
		// TODO
	}

}
