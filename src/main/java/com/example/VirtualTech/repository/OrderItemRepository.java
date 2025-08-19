// OrderItemRepository.java
package com.example.VirtualTech.repository;

import com.example.VirtualTech.domain.OrderItem;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

  @Query("""
    select coalesce(sum(oi.quantity * oi.unitPrice), 0)
    from OrderItem oi
    where oi.order.orderDate >= :start and oi.order.orderDate < :end
  """)
  BigDecimal salesBetween(@Param("start") LocalDateTime start,
                          @Param("end")   LocalDateTime end);

  @Query("""
    select new map(c.name as category, sum(oi.quantity * oi.unitPrice) as total)
    from OrderItem oi join oi.product.category c
    where oi.order.orderDate >= :start and oi.order.orderDate < :end
    group by c.name
    order by total desc
  """)
  List<Map<String,Object>> salesByCategoryBetween(@Param("start") LocalDateTime start,
                                                  @Param("end")   LocalDateTime end);

  @Query("""
    select new map(b.name as brand, sum(oi.quantity * oi.unitPrice) as total)
    from OrderItem oi join oi.product.brand b
    where oi.order.orderDate >= :start and oi.order.orderDate < :end
    group by b.name
    order by total desc
  """)
  List<Map<String,Object>> salesByBrandBetween(@Param("start") LocalDateTime start,
                                               @Param("end")   LocalDateTime end);

  @Query("""
    select coalesce(sum(oi.quantity), 0)
    from OrderItem oi
    where oi.order.orderDate >= :start and oi.order.orderDate < :end
  """)
  Long unitsBetween(@Param("start") LocalDateTime start,
                    @Param("end")   LocalDateTime end);
}
