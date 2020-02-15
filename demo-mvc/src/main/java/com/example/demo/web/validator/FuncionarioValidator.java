package com.example.demo.web.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.example.demo.domain.Funcionario;

public class FuncionarioValidator implements Validator{

	@Override
	public boolean supports(Class<?> clazz) {
		return Funcionario.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Funcionario func = (Funcionario) target;
		
		if(func.getDataSaida() != null) {
			if(func.getDataSaida().isBefore(func.getDataEntrada())) {
				errors.rejectValue("dataSaida", "dataSaidaInvalida");
			}
		}
		
	}

}
