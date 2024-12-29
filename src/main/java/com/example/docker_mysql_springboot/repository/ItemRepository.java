package com.example.docker_mysql_springboot.repository;

import com.example.docker_mysql_springboot.models.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    // Si besoin, vous pouvez ajouter des méthodes de requêtes personnalisées ici
}
