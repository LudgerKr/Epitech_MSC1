package fr.epitech.cash.manager.repository;

import fr.epitech.cash.manager.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/*
Regroup all operations / query with the database, some function are already exist :
FindAll, FindOne, FindById, DeleteById etc ...)
 */
@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Integer> {

}