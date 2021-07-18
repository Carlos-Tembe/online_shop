package com.online_shop.controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.online_shop.models.Artigo;
import com.online_shop.models.Artigo_detalhes;
import com.online_shop.models.Artigo_movimento;
import com.online_shop.models.Categoria;
import com.online_shop.models.Fornecedor;
import com.online_shop.services.ArtigoDetalhesService;
import com.online_shop.services.ArtigoService;
import com.online_shop.services.CategoriaService;
import com.online_shop.services.FornecedorService;

@Controller
@RequestMapping("artigos")
public class ArtigoController {

	@Autowired
	private ArtigoService artigoService;

	@Autowired
	private CategoriaService categoriaService;

	@Autowired
	private ArtigoDetalhesService detalheService;

	@Autowired
	private FornecedorService fornecedorService;

	@GetMapping
	public String listar(ModelMap model) {
		model.addAttribute("artigos", artigoService.buscarTodos());
		return "/artigo/lista";
	}

	@PostMapping("/pesquisar")
	public String listarPesquisa(Model model, String search) {

		if (search != null) {
			model.addAttribute("artigos", artigoService.findBySearch(search));
		} else {
			model.addAttribute("artigos", artigoService.buscarTodos());
		}
		return "index";
	}

	@GetMapping("/pesquisar")
	public String listarPesquis(Model model, String search) {

		if (search != null) {
			model.addAttribute("artigos", artigoService.findBySearch(search));
		} else {
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
	public String salvar(@Validated Artigo artigo, BindingResult result, MultipartFile file, RedirectAttributes attr)
			throws IOException {

		if (result.hasErrors()) {
			return "/artigo/cadastro";
		}

		boolean fileOk = false;
		byte[] bytes = file.getBytes();
		String fileName = file.getOriginalFilename();
		Path path = Paths.get("src/main/resources/static/img/fotos/" + fileName);

		if (fileName.endsWith("jpg") || fileName.endsWith("png") || fileName.endsWith("jpeg")
				|| fileName.endsWith("PNG")) {
			fileOk = true;
		}

		if (!fileOk) {
			attr.addFlashAttribute("fail", " A imagem de ser jpg ou png");
		} else {
			artigo.setFoto(fileName);
			artigoService.salvar(artigo);
			Files.write(path, bytes);
			attr.addFlashAttribute("success", "artigo " + artigo.getDescricao() + " registado com sucesso");
		}

		return "redirect:/artigos/";
	}

	@GetMapping("/editar/{id}")
	public String processarEditar(@PathVariable("id") Long id, Model model) {
		model.addAttribute("artigo", artigoService.buscarPorId(id));
		return "/artigo/cadastro";
	}

	@PostMapping("/editar")
	public String editar(@Validated Artigo artigo, BindingResult result, RedirectAttributes attr, MultipartFile file)
			throws IOException {
		if (result.hasErrors()) {
			attr.addFlashAttribute("fail", "Ocorreu um erro");
			return "/artigo/cadastro";
		}

		boolean fileOk = false;
		byte[] bytes = file.getBytes();
		String fileName = file.getOriginalFilename();
		Path path = Paths.get("src/main/resources/static/img/fotos/" + fileName).toAbsolutePath().normalize();
		/// CopyOption(file.getInputStream(),path);

		if (fileName.endsWith("jpg") || fileName.endsWith("png") || fileName.endsWith("jpeg")
				|| fileName.endsWith("PNG")) {
			fileOk = true;
		}

		if (!fileOk) {
			attr.addFlashAttribute("fail", " A imagem deve ser jpg ou png");
		} else {
			artigo.setFoto(fileName);
			Files.write(path, bytes);
			artigoService.salvar(artigo);

			attr.addFlashAttribute("success", "artigo " + artigo.getDescricao() + " actualizado com sucesso");
		}
		return "redirect:/artigos/";
	}

	@GetMapping("/excluir/{id}")
	public String excluir(@PathVariable("id") Long id, ModelMap model) {
		if (artigoService.artigoTemDetalhes(id)) {
			model.addAttribute("fail", "Impossivel remover artigos com historico no sistema");
		} else {
			artigoService.excluir(id);
			model.addAttribute("success", "Artigo removido com sucesso");
		}
		return "redirect:/artigos/";
	}

	// ---------DETALHES DO ARTIGO -----

	@GetMapping("/detalhes/{id}")
	public String salvarDetalhes(@PathVariable("id") Long id, Model model) {
		model.addAttribute("detalhe", new Artigo_detalhes(artigoService.buscarPorId(id)));
		model.addAttribute("detalhes", detalheService.buscarTodosPorArtigoId(id));
		model.addAttribute("entrada", new Artigo_movimento());
		return "/artigo/detalhes";
	}

	@PostMapping("/detalhes/salvar")
	public String editarDetalhes(@Validated Artigo_detalhes detalhes, BindingResult result, Model model) {
		if (result.hasErrors()) {

		}
		if (detalhes.getId() == 0) {
			detalheService.salvar(detalhes);
		} else {
			detalheService.editar(detalhes);
		}
		return "redirect:/artigos/detalhes/" + detalhes.getArtigo().getId();
	}

	@PostMapping("/detalhes/abateLoja")
	public String abateLoja(@Validated Artigo_detalhes detalhes, BindingResult result, RedirectAttributes attr) {

		if (detalheService.removerDaLoja(detalhes.getId(), detalhes.getQuant_loja())) {
			attr.addFlashAttribute("success", (long) detalhes.getQuant_loja() + " items foram movidos para o armazem");
		} else {
			attr.addFlashAttribute("fail", "Ocorreu um erro, verifique as quantidades por favor!");
		}
		return "redirect:/artigos/detalhes/" + detalhes.getArtigo().getId();
	}

	@PostMapping("/detalhes/abateStock")
	public String abateStock(@Validated Artigo_detalhes detalhes, BindingResult result, RedirectAttributes attr) {
		if (detalheService.removerDoStock(detalhes.getId(), detalhes.getQuant_stock())) {
			attr.addFlashAttribute("success", (long) detalhes.getQuant_stock() + " items foram movidos para loja");
		} else {
			attr.addFlashAttribute("fail", "Ocorreu um erro, verifique as quantidades por favor!");
		}

		return "redirect:/artigos/detalhes/" + detalhes.getArtigo().getId();
	}

	@GetMapping("/detalhes/excluir/{id}")
	public String excluirDetalhe(@PathVariable("id") Long id, Model model) {
		Artigo_detalhes d = detalheService.buscarPorId(id);
		Artigo artigo = d.getArtigo();
		detalheService.excluir(id);

		model.addAttribute("detalhe", new Artigo_detalhes(artigo));
		model.addAttribute("detalhes", detalheService.buscarTodosPorArtigoId(artigo.getId()));
		return "redirect:/artigos/detalhes/" + d.getArtigo().getId();
	}

	@GetMapping("/detalhes/estado/{id}")
	public String mudarEstado(@PathVariable("id") Long id, Model model, RedirectAttributes attr) {
		Artigo_detalhes detalhes = detalheService.buscarPorId(id);
		Artigo artigo = detalhes.getArtigo();

		;
		if (detalheService.mudarStatus(detalhes)) {
			attr.addFlashAttribute("success",
					"Artigo " + ((detalhes.getEstado() == true) ? "activo" : "desactivado") + " com sucesso!");
		} else {
			attr.addFlashAttribute("fail", "Ocorreu um erro, verifique se o artigo possui preco!");
		}

		model.addAttribute("detalhe", new Artigo_detalhes(artigo));
		model.addAttribute("detalhes", detalheService.buscarTodosPorArtigoId(artigo.getId()));
		return "redirect:/artigos/detalhes/" + artigo.getId();
	}

	@RequestMapping("/detalhes/findById")
	@ResponseBody
	public Optional<Artigo_detalhes> findById(Long id) {
		return Optional.of(detalheService.buscarPorId(id));
	}

	@ModelAttribute("categorias")
	public List<Categoria> listaCategoria() {
		return categoriaService.buscarTodos();
	}

	@ModelAttribute("fornecedores")
	public List<Fornecedor> listaFornecedor() {
		return fornecedorService.buscarTodos();
	}

	// ======DATATABLE=========
	@GetMapping("/datatables/server")
	public ResponseEntity<?> datatables(HttpServletRequest request) {
		Map<String, Object> data = artigoService.execute(request);
		return ResponseEntity.ok(data);
	}
}
