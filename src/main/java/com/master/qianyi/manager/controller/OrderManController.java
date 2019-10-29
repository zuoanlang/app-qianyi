package com.master.qianyi.manager.controller;

import com.master.qianyi.manager.service.OrderManService;
import com.master.qianyi.order.form.OrderForm;
import com.master.qianyi.utils.EasyUIDataGridResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orderMan")
public class OrderManController {

    @Autowired
    private OrderManService orderManServiceImpl;


    @GetMapping("/getOrderList")
    @ResponseBody
    public EasyUIDataGridResult getOrderList(@RequestParam(value = "page") int pageNum,
                                              @RequestParam(value = "rows") int pageSize,
                                             OrderForm form) {
        return orderManServiceImpl.getOrderList(pageNum, pageSize, form);
    }

}
