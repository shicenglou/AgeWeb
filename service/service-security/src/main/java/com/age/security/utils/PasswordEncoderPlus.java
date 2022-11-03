package com.age.security.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class PasswordEncoderPlus implements PasswordEncoder{

    PasswordEncoderPlus(int i){

    }

    PasswordEncoderPlus(){
        this(-1);
    }



//    @Override
    public static String encode1(CharSequence charSequence) {
        String mds = MdEncoder.encrypt((String) charSequence);
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode(mds);
    }

    /**
     * 加密，先用md5加密，再用BCryptPasswordEncoder加密
     * @param charSequence      用户密码明文
     * @return                  加密结果
     */
    @Override
    public String encode(CharSequence charSequence) {
        String mds = MdEncoder.encrypt((String) charSequence);
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode(mds);
    }

    /**
     * 密码校验
     * @param charSequence      用户传入的密码
     * @param s                 数据库中密码
     * @return                  校验结果
     */
    @Override
    public boolean matches(CharSequence charSequence, String s) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.matches(MdEncoder.encrypt((String) charSequence), s);
    }

    public static void main(String[] args) {
        String encode = encode1("123456");
        System.out.println(encode);
        System.out.println(encode1("123456"));
        System.out.println(encode1("123456"));
        System.out.println(encode1("123456"));
        System.out.println(encode1("123456"));
        System.out.println(encode1("123456"));
        System.out.println(encode1("123456"));
    }
}
