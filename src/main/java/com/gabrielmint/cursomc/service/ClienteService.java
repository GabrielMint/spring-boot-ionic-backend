package com.gabrielmint.cursomc.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.gabrielmint.cursomc.domain.Cidade;
import com.gabrielmint.cursomc.domain.Cliente;
import com.gabrielmint.cursomc.domain.Endereco;
import com.gabrielmint.cursomc.domain.enums.Perfil;
import com.gabrielmint.cursomc.domain.enums.TipoCliente;
import com.gabrielmint.cursomc.dto.ClienteDTO;
import com.gabrielmint.cursomc.dto.ClienteNewDTO;
import com.gabrielmint.cursomc.repository.ClienteRepository;
import com.gabrielmint.cursomc.repository.EnderecoRepository;
import com.gabrielmint.cursomc.security.UserSS;
import com.gabrielmint.cursomc.service.exceptions.AuthorizationException;
import com.gabrielmint.cursomc.service.exceptions.DataIntegrityException;
import com.gabrielmint.cursomc.service.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository repo;
	@Autowired
	private EnderecoRepository enderecoRepository;
	@Autowired
	private BCryptPasswordEncoder pe;
	
	
	public Cliente find(Integer id) {
		
		UserSS user = UserService.authenticated();
		
		if(user != null && !user.hasRole(Perfil.ADMIN) && id != user.getId()) {
			throw new AuthorizationException("Acesso negado");
		}
		
		Optional<Cliente> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto Não Encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));
		
	}
	
	public Cliente insert(Cliente obj) {
		obj.setId(null);
		obj = repo.save(obj);
		enderecoRepository.saveAll(obj.getEnderecos());
		return obj;
	}
	
	public Cliente update(Cliente obj) {
		Cliente newObj = find(obj.getId());
		updateData(newObj, obj);
		return repo.save(newObj);
	}
	
	public void delete(Integer id) {
		find(id);
		try {
			repo.deleteById(id);
		}catch(DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possivel excluir um cliente que possui pedidos!");
		}
	}
	
	public List<Cliente> findAll(){
		return repo.findAll();
	}
	
	public Page<Cliente> findPage(Integer page, Integer linerPerPage, String orderBy, String direction){
		PageRequest pageRequest = PageRequest.of(page, linerPerPage, Direction.valueOf(direction), orderBy);
		return repo.findAll(pageRequest);
	}
	
	public Cliente fromDTO(ClienteDTO objDto) {
		return new Cliente(objDto.getId(), objDto.getNome(), objDto.getEmail(), null, null, null);
	}
	
	public Cliente fromDTO(ClienteNewDTO objDto) {
		Cliente cli = new Cliente(null, objDto.getNome(), objDto.getEmail(), objDto.getCpfOuCnpj(),
								  TipoCliente.toEnum(objDto.getTipo()), pe.encode(objDto.getSenha()));
		
		Cidade cid = new Cidade (objDto.getCidadeId(), null, null);
		
		Endereco end = new Endereco(null, objDto.getLogradouro(), objDto.getNumero(),
									objDto.getComplemento(), objDto.getBairro(), objDto.getCep(), cid, cli);
		cli.getEnderecos().add(end);
		cli.getTelefones().add(objDto.getTelefone1());
		if(objDto.getTelefone2() != null) cli.getTelefones().add(objDto.getTelefone2());
		if(objDto.getTelefone3() != null) cli.getTelefones().add(objDto.getTelefone3());
		return cli;
	}
	
	private void updateData(Cliente newObj, Cliente obj) {
		newObj.setNome(obj.getNome());
		newObj.setEmail(obj.getEmail());
	}
	
}
