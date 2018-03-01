package com.dli.services;

import java.io.IOException;
import java.util.Map;

public interface VodService {

    Map getVodInfo(String title, String fileName) throws IOException;
}
