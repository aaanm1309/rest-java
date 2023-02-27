package br.com.adrianomenezes.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.adrianomenezes.model.Book;

public interface BookRepository extends JpaRepository<Book, Long>{

}
