package dev.am.am.services;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import dev.am.am.models.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import dev.am.am.dto.Bill;
import dev.am.am.exceptions.ApiRequestException;
import dev.am.am.models.BillModel;
import dev.am.am.repositories.BillRepository;

@Service
public class BillService {

    @Autowired
    private BillRepository billRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AuthorizationService authorizationService;

    private Bill convertToDTO(BillModel billModel) {
        return modelMapper.map(billModel, Bill.class);
    }

    public Bill save(Bill bill) {
        User user = authorizationService.getAuthenticatedUser();
        bill.setUserId(user.getId());
        BillModel billRegister = billRepository.save(modelMapper.map(bill, BillModel.class));
        return convertToDTO(billRegister);
    }

    public Bill findById(UUID id) {
        BillModel bill = billRepository.findById(id)
                .orElseThrow(() -> new ApiRequestException("Bill Not Found", HttpStatus.NOT_FOUND));

        return convertToDTO(bill);
    }

    public void deleteById(UUID id) {
        billRepository.findById(id)
                .orElseThrow(() -> new ApiRequestException("Bill Not Found", HttpStatus.NOT_FOUND));
        billRepository.deleteById(id);
    }

    public Bill update(Bill bill) {
        BillModel billModel = billRepository.findById(bill.getId())
                .orElseThrow(() -> new ApiRequestException("Bill Not Found", HttpStatus.NOT_FOUND));

        billModel.setName(bill.getName());
        billModel.setType(bill.getType());
        billModel.setEstimatedValue(bill.getEstimatedValue());
        billModel.setPaidValue(bill.getPaidValue());
        BillModel billUpdated = billRepository.save(modelMapper.map(billModel, BillModel.class));

        return convertToDTO(billUpdated);
    }

    public List<Bill> findAll() {
        User user = authorizationService.getAuthenticatedUser();
        List<BillModel> billModels = billRepository.findAllByUserId(user.getId());
        return billModels.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

}
