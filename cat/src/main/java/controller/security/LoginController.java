package controller.security;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import domain.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.UserService;
import util.JSONUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/login")
public class LoginController extends HttpServlet {
    private static final Logger logger = LogManager.getLogger();
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        //创建JSON对象message，以便往前端响应信息
        JSONObject message = new JSONObject();
        try {
            User loggedUser = UserService.getInstance().login(username,password);
            if(loggedUser!=null){
                message.put("message", "登录成功");
                HttpSession session = request.getSession();
                //10分钟没有操作，则使session失效
                session.setMaxInactiveInterval(10 * 60);
                session.setAttribute("currentUser" , loggedUser);
                response.getWriter().println(message);
                //此处应重定向到索引页（菜单页）
                return;
            }else{
                message.put("message", "用户名或密码错误");
            }
        } catch (SQLException e) {
            message.put("message", "数据库操作异常");
            e.printStackTrace();
        } catch (Exception e) {
            message.put("message", "网络异常");
            e.printStackTrace();
        }
        //响应message到前端
        response.getWriter().println(message);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("doGet");
        this.doPost(request,response);
    }

    protected void doGet1(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String user_json = JSONUtil.getJSON(request);
        //将JSON字串解析为Degree对象
        User userToLogin = JSON.parseObject(user_json, User.class);

        //设置响应字符编码为UTF-8
        response.setContentType("text/html;charset=UTF-8");
        //创建JSON对象message，以便往前端响应信息
        JSONObject message = new JSONObject();
        try {
            User loggedUser = UserService.getInstance().login(userToLogin);
            if(loggedUser!=null){
//                message.put("message", "登录成功");
                HttpSession session = request.getSession();
                session.setAttribute("user" , loggedUser);
                response.getWriter().println(JSON.toJSONString(loggedUser));
                return;
            }else{
                message.put("message", "用户名或密码错误");
            }
        } catch (SQLException e) {
            message.put("message", "数据库操作异常");
            e.printStackTrace();
        } catch (Exception e) {
            message.put("message", "网络异常");
            e.printStackTrace();
        }
        //响应message到前端
        response.getWriter().println(message);
    }

}
