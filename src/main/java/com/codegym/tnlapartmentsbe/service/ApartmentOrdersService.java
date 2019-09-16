package com.codegym.tnlapartmentsbe.service;

import com.codegym.tnlapartmentsbe.model.ApartmentOrders;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
@Service
public interface ApartmentOrdersService {
    List<ApartmentOrders> findAll();
    List<ApartmentOrders> findApartmentOrdersByTenantId( Long id );
    List<ApartmentOrders> findApartmentOrdersByApartmentId(Long id);
    List<Long> getOrderIdsByApartmentId(Long id);
    ApartmentOrders findById( Long id);
    boolean existsApartmentOrdersByCheckinGreaterThanEqualAndCheckinLessThanEqualAndApartmentId(Date checkin, Date checkout, Long ApartmentId);
    boolean existsApartmentOrdersByCheckoutGreaterThanEqualAndCheckoutLessThanEqualAndApartmentId(Date checkin, Date checkout, Long ApartmentId);
    boolean existsApartmentOrdersByCheckinLessThanEqualAndCheckoutGreaterThanEqualAndApartmentId(Date checkin, Date checkout, Long ApartmentId);
    boolean existsApartmentOrdersByCheckinGreaterThanEqualAndCheckoutLessThanEqualAndApartmentId(Date checkin, Date checkout, Long ApartmentId);
    void createApartmentOrders(ApartmentOrders apartmentOrders);
    void updateApartmentOrders(ApartmentOrders apartmentOrders);
    void deleteApartmentOrders(Long id);
    boolean existsApartmentStatusByStartDateGreaterThanEqualAndStartDateLessThanEqual(Date checkin, Date checkout, Long apartmentId);
    boolean existsApartmentStatusByEndDateGreaterThanEqualAndEndDateLessThanEqual(Date checkin, Date checkout, Long apartmentId);
    boolean existsApartmentStatusByStartDateLessThanEqualAndEndDateGreaterThanEqual(Date checkin, Date checkout, Long apartmentId);
    boolean existsApartmentStatusByStartDateGreaterThanEqualAndEndDateLessThanEqual(Date checkin, Date checkout, Long apartmentId);


}
