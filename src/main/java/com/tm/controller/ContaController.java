package com.tm.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.tm.exception.ContaException;
import com.tm.model.Conta;
import com.tm.repository.ContaRepository;

@RestController
@RequestMapping("conta")
public class ContaController {

	@Autowired
	ContaRepository contaRepository;

	@GetMapping(value = "")
	public ResponseEntity<List<Conta>> listarContas() {

		List<Conta> contas = contaRepository.findAll();

		if (contas.isEmpty()) {
			ResponseEntity.notFound();
		}
		return ResponseEntity.ok(contas.stream().collect(Collectors.toList()));

	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<Optional<List<Conta>>> listarContaPorId(@PathVariable("id") String id) {

		Optional<List<Conta>> conta = contaRepository.findAllById(Long.parseLong(id));

		return ResponseEntity.ok(conta);

	}

	@PostMapping(value="/cadastrar")
	public void CadastrarConta(@RequestBody Conta novaConta) throws ContaException {

		List<Conta> contas = contaRepository.findAll();
		Conta contaValidacao = contas.stream()
				.filter(conta -> novaConta.getNumeroAgencia() == conta.getNumeroAgencia()
						&& novaConta.getNumeroBanco() == conta.getNumeroBanco()
						&& novaConta.getNumeroAgencia() == conta.getNumeroAgencia())
				.findAny().orElse(null);

		if (contaValidacao == null) {
			contaRepository.save(novaConta);
		} else {
			throw new ContaException("Já existe uma conta cadastrada");
		}

	}

}
