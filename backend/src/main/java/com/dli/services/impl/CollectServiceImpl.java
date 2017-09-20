package com.dli.services.impl;


import com.dli.entities.Demo;
import com.dli.repositories.CollectRepo;
import com.dli.repositories.DemoRepo;
import com.dli.services.CollectService;
import com.dli.services.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CollectServiceImpl implements CollectService {

    @Autowired
    private CollectRepo collectRepo;


    @Override
    public void addCollection(int userid, String item_type, int item_id) {
        collectRepo.addCollection(userid, item_type, item_id);
    }

    @Override
    public void cancelCollection(int userid, String item_type, int item_id) {
        collectRepo.cancelCollection(userid, item_type, item_id);
    }

    @Override
    public boolean itemCollected(String itemtype, int itemid, int userid) {
        int count=  collectRepo.countCollection(itemtype, itemid, userid);
        if(count >0)
            return  true;
        else
            return   false;
    }
}
