package com.codegym.tnlapartmentsbe.service.Impl;

import com.codegym.tnlapartmentsbe.model.ApartmentOrders;
import com.codegym.tnlapartmentsbe.model.ApartmentStatus;
import com.codegym.tnlapartmentsbe.repository.ApartmentOrdersRepository;
import com.codegym.tnlapartmentsbe.repository.ApartmentStatusRepository;
import com.codegym.tnlapartmentsbe.service.ApartmentOrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ApartmentOrdersServiceImpl implements ApartmentOrdersService {
    @Autowired
    private ApartmentOrdersRepository apartmentOrdersRepository;
    @Autowired
    private ApartmentStatusRepository apartmentStatusRepository;

    @Override
    public List<ApartmentOrders> findAll() {
        return apartmentOrdersRepository.findAll();
    }

    @Override
    public List<ApartmentOrders> findApartmentOrdersByTenantId(Long id) {
        return apartmentOrdersRepository.findApartmentOrdersByTenantId(id);
    }

    @Override
    public List<ApartmentOrders> findApartmentOrdersByApartmentId(Long id) {
        return apartmentOrdersRepository.findApartmentOrdersByApartmentId(id);
    }

    @Override
    public List<Long> getOrderIdsByApartmentId(Long id) {
        List<Long> listOrderId=new ArrayList<>();
        List<ApartmentOrders> apartmentOrders = apartmentOrdersRepository.findApartmentOrdersByApartmentId(id);
        for (ApartmentOrders apartmentOrders1:apartmentOrders){
            listOrderId.add(apartmentOrders1.getId());
        }
        return listOrderId;
    }

    @Override
    public ApartmentOrders findById(Long id) {
        return apartmentOrdersRepository.findById(id).get();
    }

    @Override
    public boolean existsApartmentOrdersByCheckinGreaterThanEqualAndCheckinLessThanEqualAndApartmentId(Date checkin, Date checkout, Long ApartmentId) {
        return apartmentOrdersRepository.existsApartmentOrdersByCheckinGreaterThanEqualAndCheckinLessThanEqualAndApartmentId(checkin, checkout, ApartmentId);
    }

    @Override
    public boolean existsApartmentOrdersByCheckoutGreaterThanEqualAndCheckoutLessThanEqualAndApartmentId(Date checkin, Date checkout, Long ApartmentId) {
        return apartmentOrdersRepository.existsApartmentOrdersByCheckoutGreaterThanEqualAndCheckoutLessThanEqualAndApartmentId(checkin,checkout,ApartmentId);
    }

    @Override
    public boolean existsApartmentOrdersByCheckinLessThanEqualAndCheckoutGreaterThanEqualAndApartmentId(Date checkin, Date checkout, Long ApartmentId) {
        return apartmentOrdersRepository.existsApartmentOrdersByCheckinLessThanEqualAndCheckoutGreaterThanEqualAndApartmentId(checkin,checkout,ApartmentId);
    }

    @Override
    public boolean existsApartmentOrdersByCheckinGreaterThanEqualAndCheckoutLessThanEqualAndApartmentId(Date checkin, Date checkout, Long ApartmentId) {
        return apartmentOrdersRepository.existsApartmentOrdersByCheckinGreaterThanEqualAndCheckoutLessThanEqualAndApartmentId(checkin,checkout,ApartmentId);
    }

    @Override
    public void createApartmentOrders(ApartmentOrders apartmentOrders) {
        apartmentOrdersRepository.save(apartmentOrders);

    }

    @Override
    public void updateApartmentOrders(ApartmentOrders apartmentOrders) {
        apartmentOrdersRepository.save(apartmentOrders);

    }

    @Override
    public void deleteApartmentOrders(Long id) {
        apartmentOrdersRepository.deleteById(id);

    }

    @Override
    public boolean existsApartmentStatusByStartDateGreaterThanEqualAndStartDateLessThanEqual(Date checkin, Date checkout, Long apartmentId) {
        List<ApartmentStatus> apartmentStatuses = this.apartmentStatusRepository.findAllByApartmentId(apartmentId);
        for (int i = 0; i < apartmentStatuses.size(); i++){
            boolean startIn = apartmentStatuses.get(i).getStartDate().after(checkin);
            boolean startOut = apartmentStatuses.get(i).getStartDate().before(checkout);
            if (startIn && startOut){
                return true;
            };
        }
        return false;
    }

    @Override
    public boolean existsApartmentStatusByEndDateGreaterThanEqualAndEndDateLessThanEqual(Date checkin, Date checkout, Long apartmentId) {
        List<ApartmentStatus> apartmentStatuses = this.apartmentStatusRepository.findAllByApartmentId(apartmentId);
        for (int i = 0; i < apartmentStatuses.size(); i++){
            boolean endIn = apartmentStatuses.get(i).getEndDate().after(checkin);
            boolean endOut = apartmentStatuses.get(i).getEndDate().before(checkout);
            if (endIn && endOut){
                return true;
            };
        }
        return false;
    }

    @Override
    public boolean existsApartmentStatusByStartDateLessThanEqualAndEndDateGreaterThanEqual(Date checkin, Date checkout, Long apartmentId) {
        List<ApartmentStatus> apartmentStatuses = this.apartmentStatusRepository.findAllByApartmentId(apartmentId);
        for (int i = 0; i < apartmentStatuses.size(); i++){
            boolean startIn = apartmentStatuses.get(i).getStartDate().after(checkin);
            boolean endOut = apartmentStatuses.get(i).getEndDate().before(checkout);
            if (startIn && endOut){
                return true;
            };
        }
        return false;
    }

    @Override
    public boolean existsApartmentStatusByStartDateGreaterThanEqualAndEndDateLessThanEqual(Date checkin, Date checkout, Long apartmentId) {
        List<ApartmentStatus> apartmentStatuses = this.apartmentStatusRepository.findAllByApartmentId(apartmentId);
        for (int i = 0; i < apartmentStatuses.size(); i++){
            boolean endIn = apartmentStatuses.get(i).getEndDate().after(checkin);
            boolean startOut = apartmentStatuses.get(i).getStartDate().before(checkout);
            if (endIn && startOut){
                return true;
            };
        }
        return false;
    }
}
