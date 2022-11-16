package com.age.security.controller;

import com.age.security.utils.VerifyUtil;
import com.age.servicebase.model.Result;
import com.age.servicebase.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;

@Slf4j
@RestController
@RequestMapping("/code")
public class CodeController {

    @Autowired
    private RedisUtil redisUtil;

    @GetMapping("/imageCode")
    public void getImageCode(HttpServletResponse response, HttpServletRequest request) throws IOException {

        // 获取到session
        HttpSession session = request.getSession();
        // 取到sessionid
        String id = session.getId();

        // 利用图片工具生成图片
        // 返回的数组第一个参数是生成的验证码，第二个参数是生成的图片
        Object[] image = VerifyUtil.createImage();

        // 将验证码存入Session
        session.setAttribute("SESSION_VERIFY_CODE_" + id, image[0]);

        log.info("当前id {} ，验证码 {}",id,image[0]);
        String codeKey = "CODE_IMAGE_"+id;
        redisUtil.set(codeKey,image[0],300);

        // 将图片输出给浏览器
        BufferedImage images = (BufferedImage) image[1];
        response.setContentType("image/png");
        OutputStream os = response.getOutputStream();
        ImageIO.write(images, "png", os);
    }

    @GetMapping("/verifyImageCode")
    public Result verifyImageCOde(@RequestParam String code,HttpServletRequest request){
        HttpSession session = request.getSession();
        String id = session.getId();

        String codeKey = "CODE_IMAGE_"+id;

        String realValue = (String) redisUtil.get(codeKey);

        if (StringUtils.isBlank(realValue)){
            return Result.error("验证码不存在或以失效");
        }

        if (realValue.equalsIgnoreCase(code)){
            redisUtil.del(codeKey);
            return Result.ok("验证成功");
        }else {
            redisUtil.del(codeKey);
            return Result.error("验证失败");
        }
    }
}
