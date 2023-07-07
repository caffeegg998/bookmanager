package com.megane.usermanager.service.impl;

import com.megane.usermanager.dto.*;
import com.megane.usermanager.entity.Bill;
import com.megane.usermanager.entity.BillItem;
import com.megane.usermanager.entity.User;
import com.megane.usermanager.repo.BillRepo;
import com.megane.usermanager.repo.BookRepo;
import com.megane.usermanager.repo.UserRepo;
import com.megane.usermanager.service.interf.BillService;
import jakarta.persistence.NoResultException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BillServiceImpl implements BillService {

    @Autowired
    BillRepo billRepo;

    @Autowired
    UserRepo userRepo;

    @Autowired
    BookRepo bookRepo;

    @Override
    public void create(BillDTO billDTO) {
        User user = userRepo.findById(billDTO.getUser().getId()).orElseThrow(NoResultException::new);

        Bill bill = new Bill();
        bill.setUser(user);
        List<BillItem> billItems = new ArrayList<>();

        for (BillItemDTO billItemDTO : billDTO.getBillItems()) {
            BillItem billItem = new BillItem();
            billItem.setBill(bill);
            billItem.setBook(
                    bookRepo.findById(billItemDTO.getBook().getId()).orElseThrow(NoResultException::new));

            billItem.setPrice(billItemDTO.getPrice());
            billItem.setQuantity(billItemDTO.getQuantity());

            billItems.add(billItem);
        }

        bill.setBillItems(billItems);
        billRepo.save(bill);
        billDTO.setId(bill.getId());
    }

    @Override
    public void update(BillDTO billDTO) {
        User user = userRepo.findById(billDTO.getUser().getId()).orElseThrow(NoResultException::new);

        Bill bill = billRepo.findById(billDTO.getId()).orElseThrow(NoResultException::new);

//		bill.getBillItems().remove(0);//xoa het billitem
//		for (BillItemDTO billItemDTO : billDTO.getBillItems()) {
//			BillItem billItem = new BillItem();
//			billItem.setBill(bill);
//			billItem.setProduct(
//					productRepo.findById(billItemDTO.getProduct().getId()).orElseThrow(NoResultException::new));
//
//			billItem.setPrice(billItemDTO.getPrice());
//			billItem.setQuantity(billItemDTO.getQuantity());
//
//			bill.getBillItems().add(billItem);
//		}

        bill.setUser(user);

        billRepo.save(bill);
    }

    @Override
    public void delete(int id) {
        billRepo.deleteById(id);
    }

    @Override
    public PageDTO<List<BillDTO>> search(SearchDTO searchDTO) {
        Pageable pageable = PageRequest.of(searchDTO.getCurrentPage(), searchDTO.getSize());

        Page<Bill> pageRS = billRepo.findAll(pageable);

        PageDTO<List<BillDTO>> pageDTO = new PageDTO<>();
        pageDTO.setTotalPages(pageRS.getTotalPages());
        pageDTO.setTotalElements(pageRS.getTotalElements());

        // java 8 : lambda, stream
        List<BillDTO> billDTOs = pageRS.get().map(b -> new ModelMapper().map(b, BillDTO.class))
                .collect(Collectors.toList());

        pageDTO.setData(billDTOs);// set vao pagedto
        return pageDTO;
    }
    private BillDTO convert(Bill bill) {
        return new ModelMapper().map(bill, BillDTO.class);
    }
    @Override
    public BillDTO getById(int id) {
        Bill bill = billRepo.findById(id).orElseThrow(NoResultException::new);// java8 lambda
        return new ModelMapper().map(bill, BillDTO.class);
    }

    @Override
    public PageDTO<List<BillStatisticDTO>> statistic() {
        List<Object[]> list = billRepo.thongKeBill();

        PageDTO<List<BillStatisticDTO>> pageDTO = new PageDTO<>();
        pageDTO.setTotalPages(1);
        pageDTO.setTotalElements(list.size());

        List<BillStatisticDTO> billStatisticDTOs = new ArrayList<>();

        for (Object[] arr : list) {
            BillStatisticDTO billStatisticDTO = new BillStatisticDTO((long) (arr[0]),
                    String.valueOf(arr[1]) + "/" + String.valueOf(arr[2]));

            billStatisticDTOs.add(billStatisticDTO);
        }

        pageDTO.setData(billStatisticDTOs);

        return pageDTO;
    }
}
