package cn.bookcl.nfc_logger.tagdb;

import java.util.Date;
import java.util.UUID;

public class TagDatabaseStruct {

    public TagDatabaseStruct(UUID uuid) {
        var_id = uuid;
        var_payload = "";
        var_count = 0;
    }

    public TagDatabaseStruct() {
        this(UUID.randomUUID());
    }


    private UUID var_id;
    private String var_payload;
    private Date var_date;
    private long var_count;


    public UUID getVar_id()
    {
        return var_id;
    }

    public void setVar_id(UUID val) {
        var_id = val;
    }

    public String getVar_payload()
    {
        return var_payload;
    }

    public void setVar_payload (String val) {
        var_payload = val;
    }

    public Date getVar_date()
    {
        return var_date;
    }

    public void setVar_date(Date val) {
        var_date = val;
    }

    public long getVar_count()
    {
        return var_count;
    }

    public void setVar_count(long val) {
        var_count = val;
    }

}
