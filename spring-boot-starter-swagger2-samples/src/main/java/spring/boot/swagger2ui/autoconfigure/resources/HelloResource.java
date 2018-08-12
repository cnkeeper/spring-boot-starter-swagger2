package spring.boot.swagger2ui.autoconfigure.resources;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.LoggerContext;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.impl.StaticLoggerBinder;
import org.springframework.stereotype.Component;
import spring.boot.swagger2ui.autoconfigure.entity.User;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 描述~
 *
 * @author <a href="1348555156@qq.com">LeiLi.Zhang</a>
 * @version 0.0.0
 * @date 2018/8/12
 */
@Component
@Path("hello")
@Api(value="Jersey-HelloWorld API 列表")
public class HelloResource {

    Logger logger = LoggerFactory.getLogger(HelloResource.class);

    private static Map<Integer, User> INIT_DATA;

    static {
        INIT_DATA = new HashMap<Integer, User>();
        final int count = 100;
        for (int i = 0; i < count; i++) {
            User user = new User();
            user.setId(Integer.toString(i));
            user.setName("name:" + i);
            INIT_DATA.put(i, user);
        }
    }

    @GET
    @Path("success")
    public Response success() {
        logger.info("info...");
        logger.warn("warn.....");
        logger.error("error......");
        return Response.status(Response.Status.OK).build();
    }

    @GET
    @Path("get/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "根据id查询信息",notes = "查询信息",response = User.class)
    @ApiImplicitParam(name = "id",value = "id",dataType = "String",paramType = "query",example = "1112")
    @ApiResponses({
            @ApiResponse(code=400,message = "请求参数没有填好"),
            @ApiResponse(code=404,message="请求路径没有找到")
    })
    public User getUserById(@PathParam("id") Integer id) {
        System.out.println(id);
        return INIT_DATA.get(id);
    }

    @GET
    @Path("loggers")
    @Produces(MediaType.APPLICATION_JSON)
    public Object logger() {
        LoggerContext factory = (LoggerContext) StaticLoggerBinder.getSingleton().getLoggerFactory();
        System.out.println(factory.getClass().getName());
        List<ch.qos.logback.classic.Logger> loggerList = factory.getLoggerList();
        System.out.println(loggerList);
        Map<String, String> loggerNameMap = new HashMap<String, String>();
        for (ch.qos.logback.classic.Logger logger : loggerList) {
            logger.setLevel(Level.ERROR);
            loggerNameMap.put(logger.getName(), logger.getLevel().toString());
        }
        return loggerNameMap;
    }

}
