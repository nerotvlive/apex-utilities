package org.zyneonstudios.apex.utilities.json;

import com.alibaba.fastjson2.JSONObject;
import org.zyneonstudios.apex.utilities.storage.Storage;

public interface JsonData extends Storage {

    JSONObject getData();
    boolean reload();
}