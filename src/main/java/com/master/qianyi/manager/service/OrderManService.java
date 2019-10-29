package com.master.qianyi.manager.service;

import com.master.qianyi.order.form.OrderForm;
import com.master.qianyi.utils.EasyUIDataGridResult;

public interface OrderManService {
    EasyUIDataGridResult getOrderList(int pageNum, int pageSize, OrderForm orderForm);
}
