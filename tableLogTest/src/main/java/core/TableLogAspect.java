package core;

import demo.UserSession;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import util.ReflectUtil;

import java.util.*;

//1、解析xml配置
//2、进入切面
//3、获取更新前的数据
//4、执行更新操作
//5、获取更新后的数据
//5、将变化写入log表
@Aspect
@Component
public class TableLogAspect {

    @Autowired
    private TableLogConfig logConfig;

    @Autowired
    @Qualifier("tableLogExecutor")
    private ThreadPoolTaskExecutor taskExecutor;

    @Autowired
    private ApplicationContextProxy applicationContextProxy;

    @Pointcut("@annotation(core.TableLog)))")
    public void cut() { }

    @Around("cut()")
    public Object log(ProceedingJoinPoint jp) throws Throwable {
        //获取注解
        TableLog ann = ReflectUtil.getMethod(jp).getAnnotation(TableLog.class);
        //获取方法参数中需要的那个
        Object arg = jp.getArgs()[ann.argIndex()];
        //获取要修改的表的配置
        TableLogConfig.TableConfig tableConfig = logConfig.get(ann.cause());

        String sql = prepareSql(tableConfig, arg);
        Map<String, Object> prevData = getData(sql);
        //TODO 执行失败怎么弄？
        Object result = jp.proceed(jp.getArgs());
        Map<String, Object> postData = getData(sql);
        logDifference(ann.cause(), tableConfig.getTableName(),
                tableConfig.getColumns(), prevData, postData);
        return result;
    }

    private String prepareSql(TableLogConfig.TableConfig tableConfig, Object arg){
        String tableName = tableConfig.getTableName();
        String idColumn = tableConfig.getIdColumn();
        String idValue = ReflectUtil.getFieldValue(tableConfig.getIdColumn(), arg).toString();
        List<String> columns = tableConfig.getColumns();

        if (tableName == null || idColumn == null || idValue == null || columns == null) {
            return null;
        }

        //拼接sql
        StringBuilder sql = new StringBuilder("SELECT ");
        for (int i = 0; i < columns.size(); i++) {
            sql.append(columns.get(i));
            if (i < (columns.size() - 1)) {
                sql.append(",");
            }
        }
        sql.append(" from ")
                .append(tableName)
                .append(" where ")
                .append(idColumn)
                .append(" = '")
                .append(idValue)
                .append("' ");
        return sql.toString();
    }

    public Map<String, Object> getData(String sql) {
        JdbcTemplate jdbcTemplate = (JdbcTemplate)applicationContextProxy.getBean("jdbcTemplate");
        Map<String, Object> data = null;
        try {
            data = jdbcTemplate.queryForMap(sql);
        } catch (EmptyResultDataAccessException e) {
        }
        return data;
    }

//主表
//  cause
//  table
//  content
//  creator_id
//  create_name
//  create_time
    private void logDifference(String cause, String tableName,List<String> columns,
                               Map<String, Object> prevData,
                               Map<String, Object> postData){
        if(prevData == null && postData == null){
            return;
        }
        //替换成自己的session实现
        UserSession userSession = new UserSession(123, "XXX");
        String content = null;
        if(prevData == null){         //插入
            content = buildContent("插入", columns, postData);
        } else if(postData == null){  //删除
            content = buildContent("删除", columns, prevData);
        }else{                        //更新
            Map<String, Object> difference = new HashMap<>();
            for(String column : columns){
                Object prev = prevData.get(column) == null ? "" :prevData.get(column);
                Object post = postData.get(column) == null ? "" :postData.get(column);
                if(!prev.equals(post)){
                    difference.put(column, prev + "->" + post);
                }
            }
            if(difference.isEmpty()) return;
            content = buildContent("更新", columns, difference);
        }
        //写入数据库
        JdbcTemplate jdbcTemplate = (JdbcTemplate)applicationContextProxy.getBean("jdbcTemplate");
        String sql = "insert into  table_log(cause,table_name,content,creator_id,create_name,create_time) VALUES(?,?,?,?,?,?)";
        jdbcTemplate.update(sql, cause, tableName, content, userSession.getId(), userSession.getName(), new Date());
    }

    private String buildContent(String operation, List<String> columns, Map<String, Object> data){
        //替换成自己的session实现
        UserSession userSession = new UserSession(123, "XXX");

        StringBuilder content = new StringBuilder(userSession.getName() + operation + "了数据:  ");
        for (int i = 0; i < columns.size(); i++) {
            String column = columns.get(i);
            content.append(data.get(column) == null ?"":column +":" + data.get(column)+",");
        }
        if(content.charAt(content.length()-1) == ','){
            return content.substring(0, content.length()-1);
        }
        return content.toString();
    }
}
