package com.uuit.community.service.system;

import com.alibaba.fastjson.JSONObject;
import com.uuit.community.common.util.Constant;
import com.uuit.community.common.util.JsonResult;
import com.uuit.community.service.BaseService;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by zhangbin on 2016/6/26.
 * 系统管理service
 */
@Service
public class SystemService extends BaseService {

    private static final Logger LOGGER = Logger.getLogger(SystemService.class);

    /**
     * @param type   设置类型
     * @param params 设置参数
     * @param opType 操作数据库类型，默认为update
     */
    public JsonResult config(String type, JSONObject params, String opType) {
        if (StringUtils.isBlank(opType)) {
            opType = Constant.DATABASE_OP_UPDATE;
        }
        //sql语句
        String sql = "";

        if (StringUtils.isNotBlank(type)) {
            if (type.equals(Constant.SYSTEM_CONFIG_BASE)) {
                List<String> fields = new ArrayList<String>();
                fields.add("id");
                fields.add("name");
                fields.add("addr");
                fields.add("employees");
                fields.add("main_business");
                fields.add("tel");
                fields.add("mobile_number");
                fields.add("legal_person");
                fields.add("create_date");
                fields.add("description");
                return opSql(opType,"config_company",fields,params);
            } else if (type.equals(Constant.SYSTEM_CONFIG_SECURITY)) {
                List<String> fields = new ArrayList<String>();
                fields.add("id");
                fields.add("white_list");
                fields.add("forbid_list");
                fields.add("max_conn");
                return opSql(opType,"config_security",fields,params);
            } else if (type.equals(Constant.SYSTEM_CONFIG_EMAIL)) {
                List<String> fields = new ArrayList<String>();
                fields.add("id");
                fields.add("model");
                fields.add("smtp_server");
                fields.add("account");
                fields.add("password");
                fields.add("receive_addr");
                return opSql(opType,"config_email",fields,params);
            }
        }
        return JsonResult.failure("对不起，操作失败！", null);
    }

    /**
     *
     * @param opType 操作方式（插入，更新，删除）
     * @param tableName 表名
     * @param fields 字段
     * @param params 参数
     * @return
     */
    private JsonResult opSql(String opType,String tableName,List fields,JSONObject params){
        String sql = "";
        if(opType.equals(Constant.DATABASE_OP_INSERT)){
            String opStr = "(";
            String valuesStr = " values (";
            ArrayList<String> arrayList = new ArrayList<String>();
            arrayList.add(0,UUID.randomUUID().toString());
            for (int i=0;i<fields.size();i++){
                opStr+=fields.get(i).toString();
                valuesStr+="?";
                if((i+1)==fields.size()){
                    opStr+=")";
                    valuesStr+=")";
                }else{
                    arrayList.add(i+1,params.getString(fields.get(i+1).toString()));
                    opStr+=",";
                    valuesStr+=",";
                }
            }
            sql = "INSERT INTO "+tableName+opStr+valuesStr;
            Object[] opParams = arrayList.toArray();
            return JsonResult.success(insert(sql,opParams));
        }else if(opType.equals(Constant.DATABASE_OP_UPDATE)){
            String opStr = "";
            ArrayList<String> arrayList = new ArrayList<String>();
            for (int i=0;i<fields.size();i++){
                if((i+1)!=fields.size()){
                    opStr+=fields.get(i+1).toString()+"=?,";
                    arrayList.add(i,params.getString(fields.get(i+1).toString()));
                }
            }
            opStr=opStr.substring(0,opStr.length()-1);
            sql = "UPDATE "+tableName+" SET "+opStr+" WHERE id='"+params.getString("id")+"'";
            Object[] opParams = arrayList.toArray();
            return JsonResult.success(update(sql,opParams));
        }else if(opType.equals(Constant.DATABASE_OP_SELECT)){
            sql = "SELECT * FROM "+tableName+" WHERE id=?";
            return JsonResult.success(find(sql,params.getString("id")));
        }

        return JsonResult.failure(null);
    }
}
