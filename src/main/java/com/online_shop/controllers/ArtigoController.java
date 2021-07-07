package com.online_shop.controllers;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.online_shop.models.Artigo;
import com.online_shop.models.Artigo_detalhes;
import com.online_shop.models.Categoria;
import com.online_shop.services.ArtigoDetalhesService;
import com.online_shop.services.ArtigoService;
import com.online_shop.services.CategoriaService;
import com.online_shop.utils.FileUploadUtil;

@Controller
@RequestMapping("artigos")
public class ArtigoController {

	@Autowired
	private ArtigoService artigoService;

	@Autowired
	private CategoriaService categoriaService;

	@Autowired
	private ArtigoDetalhesService detalheService;

	@GetMapping
	public String listar(Model model) {
		model.addAttribute("artigos", artigoService.buscarTodos());
		return "/artigo/lista";
	}
	
	@PostMapping("/pesquisar")
	public String listarPesquisa(Model model, String search) {
		
		
		if(search!=null) {
			model.addAttribute("artigos", artigoService.findBySearch(search));
		}
		else 
		{
			model.addAttribute("artigos", artigoService.buscarTodos());
		}
		return "index";
	}
	@PostMapping("/pesquisar/admin")
	public String listarPesquis(Model model, String search) {
		
		
		if(search!=null) {
			model.addAttribute("artigos", artigoService.findBySearch(search));
		}
		else 
		{
			model.addAttribute("artigos", artigoService.buscarTodos());
		}
		return "/artigo/lista";
	}


	@GetMapping("/carrinho")
	public String verCarrinho(Model model) {
		model.addAttribute("artigos", artigoService.buscarTodos());
		return "/artigo/carrinho";
	}

	@GetMapping("/cadastrar")
	public String cadastrar(Model model) {
		model.addAttribute("artigo", new Artigo());
		return "/artigo/cadastro";
	}

	@PostMapping("/processar")
	public String salvar(@Validated Artigo artigo, BindingResult result,@RequestParam("image") MultipartFile multipartFile) throws IOException {
		
		if (result.hasErrors()) {
			return "/artigo/cadastro";
		}
		
		 Artigo artigoSalvo = artigoService.salvar(artigo);
		artigoSalvo.setFoto("444");
		artigoService.salvar(artigoSalvo);
		System.out.println("ola " + artigoSalvo.getId());

		String fileName = org.springframework.util.StringUtils.cleanPath(multipartFile.getOriginalFilename());
		String uploadDir = "user-photos/" + 1;
		FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);


		return "redirect:/artigos/";
	}

	@GetMapping("/editar/{id}")
	public String processarEditar(@PathVariable("id") Long id, Model model) {
		model.addAttribute("artigo", artigoService.buscarPorId(id));
		return "/artigo/cadastro";
	}

	@PostMapping("/editar")
	public String editar(@Validated Artigo artigo, BindingResult result) {
		if (result.hasErrors()) {
			return "/artigo/cadastro";
		}
		artigoService.salvar(artigo);
		return "redirect:/artigos/";
	}

	@GetMapping("/excluir/{id}")
	public String excluir(@PathVariable("id") Long id, Model model) {
		artigoService.excluir(id);
		return listar(model);
	}

	@GetMapping("/detalhes/{id}")
	public String salvarDetalhes(@PathVariable("id") Long id, Model model) {
		model.addAttribute("detalhe", new Artigo_detalhes(artigoService.buscarPorId(id)));
		model.addAttribute("detalhes", detalheService.buscarTodosPorArtigoId(id));
		return "/artigo/detalhes";
	}

	@GetMapping("/detalhes/editar/{id}")
	public String editar(@PathVariable("id") Long id, Model model) {

		model.addAttribute("detalhe",
				new Artigo_detalhes(artigoService.buscarPorId(detalheService.buscarPorId(id).getArtigo().getId())));
		model.addAttribute("detalhes", detalheService.buscarTodos());
		return "/artigo/detalhes";
	}

	@PostMapping("/editarDetalhes")
	public String editarDetalhes(@Validated Artigo_detalhes detalhes, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return "/artigo/detalhes";
		}
		detalheService.salvar(detalhes);
		return salvarDetalhes(detalhes.getArtigo().getId(), model);
	}

	@PostMapping("salvarDetlhes")
	public String salvarDetalhes(@Validated Artigo_detalhes detalhes, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return "/artigo/detalhes";
		}
		detalheService.salvar(detalhes);
		return salvarDetalhes(detalhes.getArtigo().getId(), model);
	}

	@GetMapping("/detalhes/excluir/{id}")
	public String excluirDetalhe(@PathVariable("id") Long id, Model model) {
		Artigo_detalhes d = detalheService.buscarPorId(id);
		detalheService.excluir(id);

		model.addAttribute("detalhe", new Artigo_detalhes(d.getArtigo()));
		model.addAttribute("detalhes", detalheService.buscarTodos());
		return "/artigo/detalhes";
	}

	@ModelAttribute("categorias")
	public List<Categoria> listaCategoria() {
		System.out.println(categoriaService.buscarTodos().size());
		return categoriaService.buscarTodos();
	}

	// ======DATATABLE=========
	@GetMapping("/datatables/server")
	public ResponseEntity<?> datatables(HttpServletRequest request) {
		Map<String, Object> data = artigoService.execute(request);
		return ResponseEntity.ok(data);
	}
}
