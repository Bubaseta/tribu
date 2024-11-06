package com.mayorista.nadir.repositorio;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mayorista.nadir.entidades.Usuario;

public interface UsuarioRepositorio extends JpaRepository<Usuario,String> {
    Optional<Usuario> findByUsername(String username); 
}