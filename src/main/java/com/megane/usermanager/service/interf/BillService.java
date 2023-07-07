package com.megane.usermanager.service.interf;

import com.megane.usermanager.dto.BillDTO;
import com.megane.usermanager.dto.BillStatisticDTO;
import com.megane.usermanager.dto.PageDTO;
import com.megane.usermanager.dto.SearchDTO;

import java.util.List;

public interface BillService {

    public void create(BillDTO billDTO);

    public void update(BillDTO billDTO);
    public void delete(int id);

    public PageDTO<List<BillDTO>> search(SearchDTO searchDTO);
    public BillDTO getById(int id);
    public PageDTO<List<BillStatisticDTO>> statistic();
}
