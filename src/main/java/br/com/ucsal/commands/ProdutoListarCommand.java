package br.com.ucsal.commands;

import java.io.IOException;
import java.util.List;

import br.com.ucsal.annotations.Inject;
import br.com.ucsal.annotations.Rota;
import br.com.ucsal.controller.Command;
import br.com.ucsal.model.Produto;
import br.com.ucsal.service.ProdutoService;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Rota("/listar")
public class ProdutoListarCommand implements Command {

	@Inject
	private ProdutoService produtoService;

	public ProdutoListarCommand() {
	}

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<Produto> produtos = produtoService.listarProdutos();
		request.setAttribute("produtos", produtos);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/produtolista.jsp");
		dispatcher.forward(request, response);
	}

}
