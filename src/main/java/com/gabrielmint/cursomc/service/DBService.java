package com.gabrielmint.cursomc.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.gabrielmint.cursomc.domain.Categoria;
import com.gabrielmint.cursomc.domain.Cidade;
import com.gabrielmint.cursomc.domain.Cliente;
import com.gabrielmint.cursomc.domain.Endereco;
import com.gabrielmint.cursomc.domain.Estado;
import com.gabrielmint.cursomc.domain.ItemPedido;
import com.gabrielmint.cursomc.domain.Pagamento;
import com.gabrielmint.cursomc.domain.PagamentoComBoleto;
import com.gabrielmint.cursomc.domain.PagamentoComCartao;
import com.gabrielmint.cursomc.domain.Pedido;
import com.gabrielmint.cursomc.domain.Produto;
import com.gabrielmint.cursomc.domain.enums.EstadoPagamento;
import com.gabrielmint.cursomc.domain.enums.Perfil;
import com.gabrielmint.cursomc.domain.enums.TipoCliente;
import com.gabrielmint.cursomc.repository.CategoriaRepository;
import com.gabrielmint.cursomc.repository.CidadeRepository;
import com.gabrielmint.cursomc.repository.ClienteRepository;
import com.gabrielmint.cursomc.repository.EnderecoRepository;
import com.gabrielmint.cursomc.repository.EstadoRepository;
import com.gabrielmint.cursomc.repository.ItemPedidoRepository;
import com.gabrielmint.cursomc.repository.PagamentoRepository;
import com.gabrielmint.cursomc.repository.PedidoRepository;
import com.gabrielmint.cursomc.repository.ProdutoRepository;

@Service
public class DBService {

	@Autowired
	private CategoriaRepository categoriaRepository;
	@Autowired
	private ProdutoRepository produtoRepository;
	@Autowired
	private EstadoRepository estadoRepository;
	@Autowired
	private CidadeRepository cidadeRepository;
	@Autowired
	private ClienteRepository clienteRepository;
	@Autowired
	private EnderecoRepository enderecoRepository;
	@Autowired
	private PedidoRepository pedidoRepository;
	@Autowired
	private PagamentoRepository pagamentoRepository;
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;
	@Autowired
	private BCryptPasswordEncoder pe;

	
	
	public void instantiateTestDatabase() throws ParseException{
		Categoria cat1 = new Categoria(null, "Informática");
		Categoria cat2 = new Categoria(null, "Escritório");
		Categoria cat3 = new Categoria(null, "Cama mesa e banho");
		Categoria cat4 = new Categoria(null, "Eletrônicos");
		Categoria cat5 = new Categoria(null, "Jardinagem");
		Categoria cat6 = new Categoria(null, "Decoração");
		Categoria cat7 = new Categoria(null, "Perfumaria");
		
		Produto p1 = new Produto(null, "Computador", 2000.00);
		Produto p2 = new Produto(null, "Impressora", 800.00);
		Produto p3 = new Produto(null, "Mouse", 80.00);
		Produto p4 = new Produto(null, "Mesa de escritório", 300.00);
		Produto p5 = new Produto(null, "Toalha", 50.00);
		Produto p6 = new Produto(null, "Colcha", 200.00);
		Produto p7 = new Produto(null, "TV true color", 1200.00);
		Produto p8 = new Produto(null, "Roçadeira", 800.00);
		Produto p9 = new Produto(null, "Abajour", 100.00);
		Produto p10 = new Produto(null, "Pendente", 180.00);
		Produto p11 = new Produto(null, "Shampoo", 90.00);
		
		
		
		cat1.getProdutos().addAll(Arrays.asList(p1, p2, p3));
		cat2.getProdutos().addAll(Arrays.asList(p2, p4));
		cat3.getProdutos().addAll(Arrays.asList(p5, p6));
		cat4.getProdutos().addAll(Arrays.asList(p1, p2, p3, p7));
		cat5.getProdutos().addAll(Arrays.asList(p8));
		cat6.getProdutos().addAll(Arrays.asList(p9, p10));
		cat7.getProdutos().addAll(Arrays.asList(p11));
		
		p1.getCategorias().addAll(Arrays.asList(cat1, cat4));
		p2.getCategorias().addAll(Arrays.asList(cat1, cat2, cat4));
		p3.getCategorias().addAll(Arrays.asList(cat1, cat4));
		p4.getCategorias().addAll(Arrays.asList(cat2));
		p5.getCategorias().addAll(Arrays.asList(cat3));
		p6.getCategorias().addAll(Arrays.asList(cat3));
		p7.getCategorias().addAll(Arrays.asList(cat4));
		p8.getCategorias().addAll(Arrays.asList(cat5));
		p9.getCategorias().addAll(Arrays.asList(cat6));
		p10.getCategorias().addAll(Arrays.asList(cat6));
		p11.getCategorias().addAll(Arrays.asList(cat7));
		
		categoriaRepository.saveAll(Arrays.asList(cat1, cat2, cat3, cat4, cat5, cat6, cat7));
		produtoRepository.saveAll(Arrays.asList(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11));
		
		Estado e1 = new Estado(null, "Minas Gerais");
		Estado e2 = new Estado(null, "São Paulo");
		
		Cidade c1 = new Cidade(null, "Uberlândia", e1);
		Cidade c2 = new Cidade(null, "São Paulo", e2);
		Cidade c3 = new Cidade(null, "Campinas", e2);
		
		e1.getCidades().addAll(Arrays.asList(c1));
		e2.getCidades().addAll(Arrays.asList(c2, c3));
		
		estadoRepository.saveAll(Arrays.asList(e1, e2));
		cidadeRepository.saveAll(Arrays.asList(c1, c2, c3));
		
		Cliente cli1 = new Cliente(null, "Maria Silva", "maria@gmail.com", "3637891237",
											TipoCliente.PESSOAFISICA, pe.encode("1234"));
		cli1.getTelefones().addAll(Arrays.asList("27363323", "93838393"));
		
		Cliente cli2 = new Cliente(null, "Ana Costa", "anacosta@gmail.com", "54674163757", 
											TipoCliente.PESSOAFISICA, pe.encode("abcd"));
		cli1.getTelefones().addAll(Arrays.asList("48571259", "996524123"));
		cli2.addPerfil(Perfil.ADMIN);
		
		
		
		Endereco end1 = new Endereco(null, "Rua Flores", "300", "apto 203", "Jardim", "38220834", c1, cli1);
		Endereco end2 = new Endereco(null, "Avenida Matos", "105", "Sala 800", "Centro", "38777012", c2, cli1);
		Endereco end3 = new Endereco(null, "Avenida Floriano", "106", "null", "Rubi Vermelho", "28777012", c2, cli2);
		
		cli1.getEnderecos().addAll(Arrays.asList(end1, end2));
		cli2.getEnderecos().addAll(Arrays.asList(end3));
		
		clienteRepository.saveAll(Arrays.asList(cli1, cli2));
		enderecoRepository.saveAll(Arrays.asList(end1, end2, end3));
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		
		Pedido ped1 = new Pedido(null, sdf.parse("30/09/2017 10:32"), cli1, end1);
		Pedido ped2 = new Pedido(null, sdf.parse("10/10/2017 19:35"), cli1, end2);
		
		Pagamento pag1 = new PagamentoComCartao(null, EstadoPagamento.QUITADO, ped1, 6);
		ped1.setPagamento(pag1);
		
		Pagamento pag2 = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE, ped2, sdf.parse("20/10/2017 23:59"), null);
		ped2.setPagamento(pag2);
		
		cli1.getPedidos().addAll(Arrays.asList(ped1, ped2));
		
		pedidoRepository.saveAll(Arrays.asList(ped1, ped2));
		pagamentoRepository.saveAll(Arrays.asList(pag1, pag2));
		
		ItemPedido it1 = new ItemPedido(ped1, p1, 0.00, 1, 2000.00);
		ItemPedido it2 = new ItemPedido(ped1, p3, 0.00, 2, 80.00);
		ItemPedido it3 = new ItemPedido(ped2, p2, 100.0, 1, 800.00);
		
		ped1.getItens().addAll(Arrays.asList(it1, it2));
		ped2.getItens().addAll(Arrays.asList(it3));
		
		p1.getItens().addAll(Arrays.asList(it1));
		p2.getItens().addAll(Arrays.asList(it3));
		p3.getItens().addAll(Arrays.asList(it2));
		
		itemPedidoRepository.saveAll(Arrays.asList(it1, it2, it3));
		
	}
	
}
