package com.example.demo.web.conversor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.example.demo.domain.Departamento;
import com.example.demo.service.DepartamentoService;

@Component
public class StringToDepartamento implements Converter<String, Departamento>{
	
	@Autowired
	private DepartamentoService service;
	
	@Override
	public Departamento convert(String source) {
		if(!source.isEmpty()) {
			return service.buscarporID(Long.valueOf(source));
		}
		return null;
	}

}
