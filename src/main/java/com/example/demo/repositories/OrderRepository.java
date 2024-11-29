package com.example.demo.repositories;

import com.example.demo.dto.ProductReportDTO;
import com.example.demo.models.OrderProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderRepository extends JpaRepository<OrderProduct, Long> {

    @Query("SELECT new com.example.demo.dto.ProductReportDTO(p.name, SUM(op.quantity)) " +
            "FROM OrderProduct op JOIN op.product p " +
            "WHERE op.order.orderDate BETWEEN :startDate AND :endDate " +
            "GROUP BY p.name " +
            "ORDER BY SUM(op.quantity) DESC")
    List<ProductReportDTO> findMostPopularProducts(String startDate, String endDate);
}
