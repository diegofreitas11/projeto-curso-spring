package com.example.demo.web.controller;

import java.time.LocalDate;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.domain.Cargo;
//import com.example.demo.domain.Departamento;
import com.example.demo.domain.Funcionario;
import com.example.demo.domain.UF;
import com.example.demo.service.CargoService;
import com.example.demo.service.FuncionarioService;
import com.example.demo.web.validator.FuncionarioValidator;

@Controller
@RequestMapping("/funcionarios")
public class FuncionarioController {
	
	@Autowired
	private CargoService cservice;
	
	@Autowired
	private FuncionarioService fservice;
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.addValidators(new FuncionarioValidator());
	}
	
	@GetMapping("/cadastrar")
	public String cadastrar(Funcionario funcionario){
		return "funcionario/cadastro";
	}
	
	@GetMapping("/listar")
	public String listar(ModelMap model) {
		model.addAttribute("funcionarios", fservice.buscarTodos());
		return "funcionario/lista";
	}
	
	@GetMapping("/demissao")
	public String preDemitir() {
		return "funcionario/demissao";
	}
	
	@GetMapping("/demitir")
	public String demitir(@RequestParam("id") Long id, @RequestParam("data") 
		@DateTimeFormat(iso = ISO.DATE) LocalDate saida, RedirectAttributes at) {
		
		Funcionario funcionario = fservice.buscarporID(id);
		
		if(funcionario.getDataEntrada().isBefore(saida)) {
			funcionario.setDataSaida(saida);
			fservice.editar(funcionario);
			at.addFlashAttribute("success", "Demissão registrada com sucesso");
		}else {
			at.addFlashAttribute("fail", "Data inválida");
			
		}
		
		return "redirect:/funcionarios/demissao";
	}
	
	@PostMapping("/salvar")
	public String salvar(@Valid Funcionario funcionario, BindingResult result,
			RedirectAttributes at) {
		
		if(result.hasErrors())
			return "funcionario/cadastro";
		
		fservice.salvar(funcionario);
		at.addFlashAttribute("success", "Salvo com sucesso");
		return "redirect:/funcionarios/cadastrar";
	}
	
	@ModelAttribute("funcionarios")
	public List<Funcionario> listarFuncionarios(){
		return fservice.buscarEmAtividade();
	}
	
	@ModelAttribute("cargos")
	public List<Cargo> listarCargos(){
		return cservice.buscarTodos();
	}
	
	@ModelAttribute("ufs")
	public UF[] listarUFS(){
		return UF.values();
	}
	
	@GetMapping("/editar/{id}")
	public String preEditar(@PathVariable("id") Long id, ModelMap model) {
		model.addAttribute("funcionario", fservice.buscarporID(id));
		return "funcionario/cadastro";
	}
	
	@PostMapping("/editar")
	public String editar(@Valid Funcionario funcionario, BindingResult result, 
			RedirectAttributes at) {
		
		if(result.hasErrors())
			return "funcionario/cadastro";
		
		fservice.editar(funcionario);
		at.addFlashAttribute("success", "Editado com sucesso");
		return "redirect:/funcionarios/cadastrar";
	}
	
	@GetMapping("/excluir/{id}")
	public String excluir(@PathVariable("id") Long id, ModelMap model) {
		
		fservice.excluir(id);
		model.addAttribute("success", "Excluído com sucesso!");
		
		
		return listar(model);
	}
	
	@GetMapping("/buscar/nome")
	public String listarPorNome(@RequestParam("nome") String nome, ModelMap model) {
		model.addAttribute("funcionarios", fservice.buscarPorNome(nome));
		return "funcionario/lista";
	}
	
	@GetMapping("/buscar/cargo")
	public String listarPorCargo(@RequestParam("cargo") Long cargo, ModelMap model) {
		model.addAttribute("funcionarios", fservice.buscarPorCargo(cargo));
		return "funcionario/lista";
	}
	
	@GetMapping("/buscar/data")
	public String listarPorData(
			@RequestParam("entrada") @DateTimeFormat(iso = ISO.DATE) LocalDate entrada,
			@RequestParam("saida") @DateTimeFormat(iso = ISO.DATE) LocalDate saida, 
			ModelMap model) {
		model.addAttribute("funcionarios", fservice.buscarPorData(entrada, saida));
		return "funcionario/lista";
	}
	
	


}
