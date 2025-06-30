package com.FullStack.GestionUsuarios.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.FullStack.GestionUsuarios.Model.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
