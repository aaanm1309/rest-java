package br.com.adrianomenezes.mapper;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;

//import com.github.dozermapper.core.DozerBeanMapperBuilder;
//import com.github.dozermapper.core.Mapper;

public class ModelMapperImpl {
	
	private static ModelMapper mapper = new ModelMapper();
	
	public static <O, D> D parseObject(O origin, Class<D> destination) {
		return mapper.map(origin, destination);
		
	}
	
	public static <O, D> List<D> parseListObjects(List<O> origin, Class<D> destination) {
		List<D> destObjects =  new ArrayList<D>();
		for (O o : origin) {
			destObjects.add(mapper.map(o, destination));
		}
		return destObjects;
		
	}

}
