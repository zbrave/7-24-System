package tr.edu.yildiz.ce.mapper;
 
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import tr.edu.yildiz.ce.model.UserInfo;
 
public class UserInfoMapper implements RowMapper<UserInfo> {
 
    @Override
    public UserInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
 
        String username = rs.getString("username");
        String password = rs.getString("password");
        Integer stdId = rs.getInt("std_id");
        Boolean enabled = rs.getBoolean("enabled");
        return new UserInfo(username, password, stdId, enabled);
    }
 
}