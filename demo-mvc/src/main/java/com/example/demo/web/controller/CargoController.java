package com.example.demo.web.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.domain.Cargo;
import com.example.demo.domain.Departamento;
import com.example.demo.service.*;

@Controller
@RequestMapping("/cargos")
public class CargoController {
	
	@Autowired
	private CargoService cservice;
	@Autowired
	private DepartamentoService dservice;
	
	@GetMapping("/cadastrar")
	public String cadastrar(Cargo cargo){
		return "cargo/cadastro";
	}
	
	@GetMapping("/listar")
	public String listar(ModelMap model) {
		model.addAttribute("cargos", cservice.buscarTodos());
		return "cargo/lista";
	}
	
	@PostMapping("/salvar")
	public String salvar(@Valid Cargo cargo, BindingResult result, RedirectAttributes at) {
		
		if(result.hasErrors()) 
			return "cargo/cadastro";
		
		cservice.salvar(cargo);
		at.addFlashAttribute("success", "Salvo com sucesso");
		return "redirect:/cargos/cadastrar";
	}
	
	@GetMapping("/editar/{id}")
	public String preEditar(@PathVariable("id") Long id, ModelMap model) {
		model.addAttribute("cargo", cservice.buscarporID(id));
		return "cargo/cadastro";
	}
	
	@PostMapping("/editar")
	public String editar(@Valid Cargo cargo, BindingResult result, RedirectAttributes at) {
		
		if(result.hasErrors()) 
			return "cargo/cadastro";
		
		cservice.editar(cargo);
		at.addFlashAttribute("success", "Editado com sucesso");
		return "redirect:/cargos/cadastrar";
	}
	
	@GetMapping("/excluir/{id}")
	public String excluir(@PathVariable("id") Long id, ModelMap model) {
		if(!cservice.temDependencia(id)) {
			cservice.excluir(id);
			model.addAttribute("success", "Excluído com sucesso!");
		}else {
			model.addAttribute("fail", "Possui funcionários vinculados");
		}
		
		return listar(model);
	}
	
	@ModelAttribute("departamentos")
	public List<Departamento> listarDepartamentos(){
		return dservice.buscarTodos();
	}

}
