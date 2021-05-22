package br.com.sporttads.controller;

import br.com.sporttads.model.ImagemModel;
import br.com.sporttads.model.ProdutoModel;
import br.com.sporttads.service.ImagemService;
import br.com.sporttads.service.ProdutoService;
import br.com.sporttads.utils.FileUploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.List;


@Controller
@RequestMapping("/produtos")
public class ProdutoController {

	@Autowired
	private ProdutoService produtoService;
	
	@Autowired
	private ImagemService imagemService;

	@GetMapping("/listaproduto")
	public ModelAndView getAll() {
		List<ProdutoModel> produtos = produtoService.getAll();
		ModelAndView andView = new ModelAndView("Produto/ListaProduto");
		andView.addObject("produtos",produtos);
		return andView;
	}
	
	@GetMapping("**/editarproduto/{idproduto}")
	public ModelAndView editar(@PathVariable("idproduto") Integer idproduto) {
		ProdutoModel produto = produtoService.getById(idproduto);
		ModelAndView andView = new ModelAndView("Produto/AlteraProduto");
		andView.addObject("produtoObj", produto);
		
		return andView;
	}

	
	@GetMapping("**/inativaativarproduto/{idproduto}")
	public ModelAndView inativaAtiva(@PathVariable("idproduto") Integer idproduto) {
		ProdutoModel produto = produtoService.getById(idproduto);
		ModelAndView andView = new ModelAndView("Produto/InativaAtivaProduto");
		andView.addObject("produtoObj", produto);
		
		return andView;
	}

	@PostMapping("**/inativarreativarproduto")
	public ModelAndView ativaReativar(ProdutoModel produto) {
		ProdutoModel p = produtoService.getById(produto.getId());
		produto.setLogo(p.getLogo());
		produtoService.save(produto);
		ModelAndView andView = new ModelAndView("Produto/ListaProduto");
		List<ProdutoModel> produtos = produtoService.getAll();
		andView.addObject("produtos",produtos);
		return andView;
	}

	@PostMapping("**/pesquisarproduto")
	public ModelAndView pesquisarproduto(@RequestParam("nomepesquisa") String nomepesquisa) {
		ModelAndView andView = new ModelAndView("Produto/ListaProduto");
		andView.addObject("produtos", produtoService.findProdutoByName(nomepesquisa));

		return andView;
	}

	@GetMapping("/cadastroproduto")
	public ModelAndView telaCadastro() {
		ModelAndView andView = new ModelAndView("Produto/CadastroProduto");
		return andView;
	}
	
	@PostMapping("/alterarproduto")
	public ModelAndView telaAltera(ProdutoModel produto) {
		ProdutoModel produtoEditado = produtoService.save(produto);
		ModelAndView andView = new ModelAndView("Produto/AlterarImagemProduto");
		andView.addObject("produto",produtoEditado);
		return andView;
	}
	
	@PostMapping("/save")
	public ModelAndView saveProduto(@ModelAttribute(name = "produto") ProdutoModel produto,
			@RequestParam("arquivoImagem") MultipartFile multipartfile) throws IOException{
		
		String nomeArquivo = "NotFile"; 
		
		if(!multipartfile.isEmpty()) {
			nomeArquivo = StringUtils.cleanPath(StringUtils.cleanPath(multipartfile.getOriginalFilename()));
		}
		
		produto.setLogo(nomeArquivo);
		
		ProdutoModel produtoSalvo = produtoService.save(produto);
		
		if(!multipartfile.isEmpty()) {
			String uploadDiretorio = "./imagem-principal/"+produtoSalvo.getId();
			FileUploadUtil.saveFile(uploadDiretorio, multipartfile, nomeArquivo);		
		}

		ModelAndView andView = new ModelAndView("Produto/CadastrarImagemProduto");
		andView.addObject("produto",produtoSalvo);
		return andView;
	}
	
	@PostMapping("/alterar")
	public ModelAndView alterarProduto(@ModelAttribute(name = "produto") ProdutoModel produto,
			@RequestParam("arquivoImagem") MultipartFile multipartfile) throws IOException{
		ProdutoModel p = produtoService.getById(produto.getId());
		
		String nomeArquivo = p.getLogo(); 
		
		if(!multipartfile.isEmpty()) {
			nomeArquivo = StringUtils.cleanPath(StringUtils.cleanPath(multipartfile.getOriginalFilename()));
		}
		
		produto.setLogo(nomeArquivo);
		ProdutoModel produtoSalvo = produtoService.save(produto);
		
		if(!multipartfile.isEmpty()) {
			String uploadDiretorio = "./imagem-principal/"+produtoSalvo.getId();
			FileUploadUtil.saveFile(uploadDiretorio, multipartfile, nomeArquivo);		
		}
		ImagemModel imagem = imagemService.findByIdProduto(produtoSalvo.getId());
		ModelAndView andView = new ModelAndView("Produto/AlterarImagemProduto");
		andView.addObject("imagem",imagem);
		
		return andView;
	}

	@GetMapping("/comprar-produto/{idproduto}")
	public ModelAndView consultaCompra(@PathVariable("idproduto") Integer idproduto) {
		ProdutoModel produto = produtoService.getById(idproduto);
		ImagemModel imagens = imagemService.findByIdProduto(idproduto);
		ModelAndView andView = new ModelAndView("Produto/DetalhesProdutoCompra");
		andView.addObject("produto", produto);
		andView.addObject("imagens", imagens);
		return andView;
	}

	@GetMapping("/consultar-produto/{idproduto}")
	public ModelAndView visualizarProduto(@PathVariable("idproduto") Integer idproduto) {
		ProdutoModel produto = produtoService.getById(idproduto);
		ImagemModel imagens = imagemService.findByIdProduto(idproduto);
		ModelAndView andView = new ModelAndView("Produto/ConsultarProduto");
		andView.addObject("produto", produto);
		andView.addObject("imagens", imagens);
		return andView;
	}

	@GetMapping("/listaprodutosqtde")
	public ModelAndView listaQtdeProdutos(){
		List<ProdutoModel> produtos = produtoService.buscaQtdeProdutos();
		ModelAndView andView = new ModelAndView("Produto/ListaProduto");
		andView.addObject("produtos",produtos);
		return andView;
	}

	@PostMapping("/alterar-qtde")
	public ModelAndView alterarQtde (@ModelAttribute(name = "produto") ProdutoModel produto,
									 @PathVariable("quantidade") Integer quantidade) {
		ProdutoModel produtoSalvo = produtoService.editQtde(produto, quantidade);
		ModelAndView andView = new ModelAndView("Produto/AlteraProduto");
		andView.addObject("produto",produtoSalvo);
		return andView;
	}

}
