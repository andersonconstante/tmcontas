package com.tm.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tm.model.Conta;

@Repository
public interface ContaRepository extends JpaRepository<Conta, Long>{

	Optional<List<Conta>> findAllById(long parseLong);

}
