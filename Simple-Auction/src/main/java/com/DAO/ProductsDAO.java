package com.DAO;

import com.DTO.ProductsDTO;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;


public class ProductsDAO {
    MyConnectUnit connect;
    String strTableName = "tblproducts";

    /**
     * Lấy thông tin từ Database
     */
    public ArrayList<ProductsDTO> readDB(String condition, String orderBy) throws Exception {
        // kết nối CSDL
        connect = new MyConnectUnit();

        ResultSet result = this.connect.Select(strTableName, condition, orderBy);
        ArrayList<ProductsDTO> DTOs = new ArrayList<>();
        while (result.next()) {
            ProductsDTO DTO = new ProductsDTO();

            DTO.setStrProductName(result.getString("productName"));
            DTO.setIntStartingPrice(result.getInt("startingPrice"));
            DTO.setStrImageUrl(result.getString("imageUrl"));
            DTO.setBoolSoldStatus(result.getBoolean("soldStatus"));


            DTOs.add(DTO);
        }
        connect.Close();
        return DTOs;
    }

    public ArrayList<ProductsDTO> readDB(String condition) throws Exception {
        return readDB(condition, null);
    }

    public ArrayList<ProductsDTO> readDB() throws Exception {
        return readDB(null);
    }

    public Boolean update(ProductsDTO tk) throws Exception {
        connect = new MyConnectUnit();

        // tạo đối tượng truyền vào
        HashMap<String, Object> insertValues = new HashMap<>();
        insertValues.put("soldStatus", 1);

        String condition = " productName = '" + tk.getStrProductName() + "'";

        Boolean check = connect.Update(strTableName, insertValues, condition);

        connect.Close();
        return check;
    }
}
