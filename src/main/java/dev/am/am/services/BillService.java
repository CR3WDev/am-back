package dev.am.am.services;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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

    private Bill convertToDTO(BillModel billModel) {
        return modelMapper.map(billModel, Bill.class);
    }

    public Bill save(Bill bill) {
        BillModel billRegister = billRepository.save(modelMapper.map(bill, BillModel.class));
        return convertToDTO(billRegister);
    }

    public Bill findById(UUID id) {
        BillModel bill = billRepository.findById(id)
                .orElseThrow(() -> new ApiRequestException("Animal Not Found", HttpStatus.NOT_FOUND));

        return convertToDTO(bill);
    }

    public void deleteById(UUID id) {
        billRepository.findById(id)
                .orElseThrow(() -> new ApiRequestException("Animal Not Found", HttpStatus.NOT_FOUND));
    }

    public Bill update(Bill bill) {
        billRepository.findById(bill.getId())
                .orElseThrow(() -> new ApiRequestException("Animal Not Found", HttpStatus.NOT_FOUND));

        BillModel billUpdated = billRepository.save(modelMapper.map(bill, BillModel.class));
        return convertToDTO(billUpdated);
    }

    public List<Bill> findAll() {
        List<BillModel> billModels = billRepository.findAll();
        return billModels.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

}
