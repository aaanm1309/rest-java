package br.com.adrianomenezes.integrationstests.controller.withyml.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.type.TypeFactory;

import io.restassured.mapper.ObjectMapper;
import io.restassured.mapper.ObjectMapperDeserializationContext;
import io.restassured.mapper.ObjectMapperSerializationContext;

public class YmlMapper implements ObjectMapper{
	
	private com.fasterxml.jackson.databind.ObjectMapper objMapper;
	protected TypeFactory typeFactory;
	
	

	public YmlMapper() {
		objMapper = new com.fasterxml.jackson.databind.ObjectMapper();
		objMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		this.typeFactory = TypeFactory.defaultInstance();
	}

	@Override
	@SuppressWarnings("rawtypes")
	public Object deserialize(ObjectMapperDeserializationContext context) {
		try {
			String dataToDeserialize = context.getDataToDeserialize().asString();
			
			Class type = (Class) context.getType();
			return objMapper.readValue(dataToDeserialize, typeFactory.constructType(type));
			
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Object serialize(ObjectMapperSerializationContext context) {
		try {
			return objMapper.writeValueAsString(context.getObjectToSerialize());
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return null;
	}

}
