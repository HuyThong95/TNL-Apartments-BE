package com.codegym.tnlapartmentsbe.repository;

import com.codegym.tnlapartmentsbe.model.ApartmentOrders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ApartmentOrdersRepository extends JpaRepository<ApartmentOrders, Long> {
    boolean existsApartmentOrdersByCheckinGreaterThanEqualAndCheckinLessThanEqualAndApartmentId(Date checkin, Date checkout, Long ApartmentId);
    boolean existsApartmentOrdersByCheckoutGreaterThanEqualAndCheckoutLessThanEqualAndApartmentId(Date checkin, Date checkout, Long ApartmentId);
    boolean existsApartmentOrdersByCheckinLessThanEqualAndCheckoutGreaterThanEqualAndApartmentId(Date checkin, Date checkout, Long ApartmentId);
    boolean existsApartmentOrdersByCheckinGreaterThanEqualAndCheckoutLessThanEqualAndApartmentId(Date checkin, Date checkout, Long ApartmentId);
    List<ApartmentOrders> findApartmentOrdersByTenantId(Long id);
    List<ApartmentOrders> findApartmentOrdersByApartmentId(Long id);
}
