package br.com.adrianomenezes.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.adrianomenezes.model.Person;

public interface PersonRepository extends JpaRepository<Person, Long>{

}
