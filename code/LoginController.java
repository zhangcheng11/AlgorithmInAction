package com.dfssi.zuul.controller;

import com.dfssi.dataplatform.cloud.common.annotation.LogAudit;
import com.dfssi.dataplatform.cloud.common.entity.ResponseObj;
import com.dfssi.zuul.entity.UserEntity;
import com.dfssi.zuul.restful.IRouteLoginFeign;
import com.dfssi.zuul.util.CheckImageServlet;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

@Controller
@RequestMapping("/login")
public class LoginController {
    @Autowired
    private IRouteLoginFeign routeloginFeign;

    @Autowired
    private CheckImageServlet checkImageServlet;

    //账号密码登入
    @RequestMapping(value = "/validate",method = RequestMethod.POST)
    @ResponseBody
    @LogAudit
    public ResponseObj login(HttpServletRequest request, HttpServletResponse response, String checkId, UserEntity userEntity) {
        ResponseObj responseObj=ResponseObj.createResponseObj();
        //获取验证码并判断  需要在同一个session里调用验证码 才能获取到sCheckId
        String sCheckId= (String) request.getSession().getAttribute("checkcode_session");
        sCheckId = null==sCheckId?"":sCheckId;
        if (!sCheckId.equalsIgnoreCase(checkId)){
            responseObj.setData("");
            responseObj.setStatus("-2","失败","验证码错误");
            return responseObj;
        }
        Map<String ,String> usermap =  new HashMap<String,String>();
        usermap.put("userName", userEntity.getUName());
        usermap.put("userPassword", userEntity.getUPsword());
        //账号密码核对
        responseObj = routeloginFeign.login(usermap);
        return responseObj;
    }

    /**
     * 注销登录
     * @author wanlong
     * @param request
     * @return
     */
    @RequestMapping(value = "/loginOut",method = RequestMethod.POST)
    @ResponseBody
    @LogAudit
    public ResponseObj loginOut(HttpServletRequest request){
        ResponseObj result = ResponseObj.createResponseObj();
        String globalAccessToken = request.getHeader("userAuth");
        if (StringUtils.isEmpty(globalAccessToken)) {
//            result.put("code", "1001");
//            result.put("descp", "头信息中未携带用户授权令牌");
            result.setData("");
            result.setStatus("1001","失败","头信息中未携带用户授权令牌");
            return result;
        }
        //获取当前会话下注册过的微服务
        Set<String> registerServices = (Set<String>) request.getSession().getAttribute("userAuth");
        if(null!=registerServices){
            Iterator<String> ite = registerServices.iterator();
//            while (ite.hasNext()) {
//                //遍历注册的微服务列表,通知每个微服务注销自己的会话上下文信息
//                String[] tmpService = ite.next().split(",");
//                log.info("调用RabbitMQ向指定Topic推送一条注销令牌消息");
//                log.info("注销的微服务ID为:" + tmpService[0] + ",访问令牌为:" + tmpService[1]);
//            }
        }
        request.getSession().invalidate();
//        result.put("code", "1000");
//        result.put("descp", "注销成功");
        result.setData("");
        result.setStatus("1000","成功","");
        return result;
    }


    //验证码的获取
    @ApiOperation(value = "验证码的获取",notes = "点击验证码刷新后返回新的验证码")
    @RequestMapping(value = "/check",method = RequestMethod.GET)
    public void getCheck(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
        checkImageServlet.doGet(request,response);
    }
}
