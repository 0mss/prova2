package br.com.ucsal.commands;

import java.io.IOException;

import br.com.ucsal.annotations.Inject;
import br.com.ucsal.annotations.Rota;
import br.com.ucsal.controller.Command;
import br.com.ucsal.service.ProdutoService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

//Adicionado o @Inject para injeção de dependência do ProdutoService
//Adicionado @Rota para mapear a rota utilizando a anotação, passando uma String como parâmetro para definir o path
@Rota("/excluir")
public class ProdutoExcluirCommand implements Command {

	@Inject
	private ProdutoService produtoService;

	public ProdutoExcluirCommand() {
	}

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Integer id = Integer.parseInt(request.getParameter("id"));
		produtoService.removerProduto(id);
		response.sendRedirect("listar");
	}

}
