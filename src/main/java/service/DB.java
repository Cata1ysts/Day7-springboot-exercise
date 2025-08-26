package service;

import com.oocl.training.Util.DataBase;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class DB {
    public final DataBase db; // 假设 Database 是你的数据库访问对象

    public DB(DataBase db) {
        this.db = db;
    }


    // 其他数据库操作的方法
}