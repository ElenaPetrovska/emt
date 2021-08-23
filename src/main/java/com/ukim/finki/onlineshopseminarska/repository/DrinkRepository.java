package com.ukim.finki.onlineshopseminarska.repository;

import com.ukim.finki.onlineshopseminarska.model.Drink;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public interface DrinkRepository extends JpaRepository<Drink, Long> {
    List<Drink> findAllByCategoryId(Long id);
    Optional<Drink> findById(Long id);
    Long countAllByQuantityGreaterThan(@Param("quantity") Long quantity);
    List<Drink> findAll();
    Drink save(Drink drink);
    void deleteById(Long id);

    @Query("select d from Drink d order by d.quantity asc")
    List<Drink> findAllByQuantityAsc();

    @Query("select d from Drink d order by d.quantity desc")
    List<Drink> findAllByQuantityDesc();
}
