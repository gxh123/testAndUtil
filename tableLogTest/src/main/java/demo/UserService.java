package demo;

import core.ApplicationContextProxy;
import core.TableLog;
import core.TableLogType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service("userService")
public class UserService {

    @Autowired
    private ApplicationContextProxy applicationContextProxy;

    @TableLog(cause = "原因2", type = TableLogType.UPDATE)
    public void update(User user){
        JdbcTemplate jdbcTemplate = (JdbcTemplate)applicationContextProxy.getBean("jdbcTemplate");
        String sql = "UPDATE user SET name = ?, age = ?, school = ? WHERE id = ?";
        jdbcTemplate.update(sql, user.getName(), user.getAge(), user.getSchool(), user.getId());
    }

    @TableLog(cause = "原因2", type = TableLogType.INSERT)
    public void insert(User user){
        JdbcTemplate jdbcTemplate = (JdbcTemplate)applicationContextProxy.getBean("jdbcTemplate");
        String sql = "insert into user(name,age,school) values(?,?,?)";
        jdbcTemplate.update(sql, user.getName(), user.getAge(), user.getSchool(), user.getId());
    }

    @TableLog(cause = "原因2", type = TableLogType.DELETE)
    public void delete(int id){
        JdbcTemplate jdbcTemplate = (JdbcTemplate)applicationContextProxy.getBean("jdbcTemplate");
        String sql = "DELETE from user WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    //TODO 目前只支持更新，插入删除不支持
    //根据条件更新 ？
    //根据条件删除
    //多条记录？

}
