package com.dli.services;


import com.dli.entities.Demo;

import java.util.Date;
import java.util.List;

public interface CollectService {

    void   addCollection( int userid, String item_type, int item_id);

    void   cancelCollection( int userid, String item_type, int item_id);

}
