package com.tm.controller;

import java.text.DateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tm.exception.CadastroTipoTransacaoException;
import com.tm.exception.TaxaException;
import com.tm.model.Conta;
import com.tm.model.Transacao;
import com.tm.repository.TransacaoRepository;



@RestController
@RequestMapping("agendamento")
public class TransacaoController {

	@Autowired
	TransacaoRepository transacaoRepository;
	
	Date dataAgendada;
	double dias;
	


	@GetMapping(value = "")
	public ResponseEntity<List<Transacao>> listarTransacoes(Conta conta) {

		List<Transacao> transacoes = listarContas();
		
		if (transacoes.isEmpty()) {
			ResponseEntity.notFound();
		}
		return ResponseEntity.ok(transacoes.stream().collect(Collectors.toList()));

	}
	
	@PostMapping(path = "/cadastrar")
	public ResponseEntity<Transacao> cadastrar(@RequestBody Transacao novaTransacao) {
		
		
		try {
			cadastrarTransacaoTipos(novaTransacao);
			return new ResponseEntity<>( HttpStatus.CREATED);
		} catch (CadastroTipoTransacaoException | TaxaException e) {
			
			e.printStackTrace();
		}
		return new ResponseEntity<>( HttpStatus.BAD_REQUEST);
		
	}
	
	
	public List<Transacao> listarContas() {

		List<Transacao> transacao = transacaoRepository.findAll();

		return transacao.stream().collect(Collectors.toList());

	}
	
	
	@GetMapping(value = "/conta/{id}")
	public ResponseEntity<List<Transacao>> listarTransacaoPorConta(@PathVariable("id") String id) {

		List<Transacao> transacoes = transacaoRepository.findByContaOrigem_id(Long.parseLong(id));

		return ResponseEntity.ok(transacoes.stream().collect(Collectors.toList()));

	}
	

	public void cadastrarTransacaoTipos(Transacao novoAgendamento) throws CadastroTipoTransacaoException, TaxaException {
		
		if(novoAgendamento.getTipo().equalsIgnoreCase("A") ) {
			cadastrarTransacaoTipoA(novoAgendamento);
		}
		else if(novoAgendamento.getTipo().equalsIgnoreCase("B")) {
			cadastrarTransacaoTipoB(novoAgendamento);
		}
		else if(novoAgendamento.getTipo().equalsIgnoreCase("C")) {
			cadastrarTransacaoTipoC(novoAgendamento);
		}
		else if(novoAgendamento.getTipo().equalsIgnoreCase("D")) {
			cadastrarTransacaoTipoD(novoAgendamento);
		}
		else {
			throw new CadastroTipoTransacaoException("Opção Inexistente");
		}
	}
	
	public void cadastrarTransacaoTipoA(Transacao novoAgendamento) throws CadastroTipoTransacaoException {
		
		dataAgendada = novoAgendamento.getDataTransferencia();		
		dias = dataAgendada.compareTo(novoAgendamento.getDataGendamento());
		
		if(dias < 1 || novoAgendamento.getTipo().equalsIgnoreCase("D") && novoAgendamento.getValorTransferencia() <=1000 ) {
			novoAgendamento.setTaxa(novoAgendamento.getValorTransferencia() * 0.03 + 3);
			novoAgendamento.setDataTransferencia(novoAgendamento.getDataGendamento());
			transacaoRepository.save(novoAgendamento);
		}else {
			
			throw new CadastroTipoTransacaoException("Operação não suportada");
		}
	}
	
	public void cadastrarTransacaoTipoB(Transacao novoAgendamento) throws CadastroTipoTransacaoException {
		
		dataAgendada = novoAgendamento.getDataTransferencia();
		dias = dataAgendada.compareTo(novoAgendamento.getDataGendamento());
		
		if(dias > 0 && dias <= 10 || novoAgendamento.getTipo().equalsIgnoreCase("D") && novoAgendamento.getValorTransferencia() > 1000 && novoAgendamento.getValorTransferencia() <= 1000) {
			novoAgendamento.setTaxa(12);
			transacaoRepository.save(novoAgendamento);
		}else {
			
			throw new CadastroTipoTransacaoException("Operação não suportada");
		}
		
	}
	
	public void cadastrarTransacaoTipoC(Transacao novoAgendamento) throws TaxaException {
		
		dataAgendada = novoAgendamento.getDataTransferencia();
		dias = dataAgendada.compareTo(novoAgendamento.getDataGendamento());
		
		if(dias > 10 && dias <= 20 || novoAgendamento.getTipo().equalsIgnoreCase("D") && novoAgendamento.getValorTransferencia() > 2000 ) {			
			novoAgendamento.setTaxa(novoAgendamento.getValorTransferencia() * 0.082);
		}
		else if(dias > 20 && dias >= 30 || novoAgendamento.getTipo().equalsIgnoreCase("D") && novoAgendamento.getValorTransferencia() > 2000  ) {
			novoAgendamento.setTaxa(novoAgendamento.getValorTransferencia() * 0.069);
		}
		else if(dias > 30 && dias >= 40 || novoAgendamento.getTipo().equalsIgnoreCase("D") && novoAgendamento.getValorTransferencia() > 2000  ) {
			novoAgendamento.setTaxa(novoAgendamento.getValorTransferencia() * 0.047);
		}
		else if(dias > 40 || novoAgendamento.getTipo().equalsIgnoreCase("D") && novoAgendamento.getValorTransferencia() > 2000  ) {
			novoAgendamento.setTaxa(novoAgendamento.getValorTransferencia() * 0.069);
		}
		else {
			throw new TaxaException("Não há taxa aplícavel");
			
		}

		transacaoRepository.save(novoAgendamento);
		
	}
	

	
	public Transacao cadastrarTransacaoTipoD(Transacao novoAgendamento) throws TaxaException, CadastroTipoTransacaoException {
		
		double valorTransferencia = novoAgendamento.getValorTransferencia();
		if(valorTransferencia <= 1000) {
			cadastrarTransacaoTipoA(novoAgendamento);
		}
		else if (valorTransferencia >= 1000 && valorTransferencia <= 2000) {
			cadastrarTransacaoTipoB(novoAgendamento);
		}
		else if (valorTransferencia > 2000) {
			cadastrarTransacaoTipoC(novoAgendamento);
		}
		else {
			throw new TaxaException("Não há taxa aplícavel");
			
		}
		
		return novoAgendamento;
		
	}

	
}
