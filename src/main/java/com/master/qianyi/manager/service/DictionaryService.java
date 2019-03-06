package com.master.qianyi.manager.service;

import com.master.qianyi.manager.pojo.EasyUITreeNode;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * project:itcast-microservice-user
 * function:
 * author:kangkang
 * date: 2019/3/1
 */
@Service
public interface DictionaryService {

    public List<EasyUITreeNode> getDictionary();
}
