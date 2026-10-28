package com.lazydaze.weblog.jwt.filter;

import cloud.tianai.captcha.application.ImageCaptchaApplication;
import cloud.tianai.captcha.spring.plugins.secondary.SecondaryVerificationApplication;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lazydaze.weblog.jwt.exception.CaptchaVerificationFailedException;
import com.lazydaze.weblog.jwt.exception.UsernameOrPasswordNullException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * @author: LazyDaze101
 * @url: www.lazydaze101.dev
 * @date: 2026-08-24 9:36
 * @description: 用户认证过滤器
 **/
@Slf4j
public class JwtAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    /**
     * 验证码校验器
     */
    private ImageCaptchaApplication imageCaptchaApplication;

    /**
     * 指定用户登录的访问地址
     */
    public JwtAuthenticationFilter() {
        super(new AntPathRequestMatcher("/login", "POST"));
    }

    /**
     * 设置验证码校验器
     * @param imageCaptchaApplication 验证码校验器
     */
    public void setImageCaptchaApplication(ImageCaptchaApplication imageCaptchaApplication) {
        this.imageCaptchaApplication = imageCaptchaApplication;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        ObjectMapper mapper = new ObjectMapper();
        // 解析提交的 JSON 数据
        JsonNode jsonNode = mapper.readTree(request.getInputStream());
        JsonNode usernameNode = jsonNode.get("username");
        JsonNode passwordNode =  jsonNode.get("password");
        JsonNode captchaIdNode = jsonNode.get("captchaId");

        // 判断用户名、密码是否为空
        if (Objects.isNull(usernameNode) || Objects.isNull(passwordNode)
            || StringUtils.isBlank(usernameNode.textValue()) || StringUtils.isBlank(passwordNode.textValue())) {
            throw new UsernameOrPasswordNullException("用户名或密码不能为空");
        }

        // 二次校验验证码
        if (Objects.nonNull(imageCaptchaApplication)) {
            // 验证码 ID
            String captchaId = captchaIdNode != null ? captchaIdNode.textValue() : null;
            if (StringUtils.isBlank(captchaId)) {
                throw new CaptchaVerificationFailedException("验证码 ID 不能为空");
            }

            // 执行二次校验
            boolean verified = false;
            if (imageCaptchaApplication instanceof SecondaryVerificationApplication) {
                verified = ((SecondaryVerificationApplication) imageCaptchaApplication).secondaryVerification(captchaId);
            }
            if (!verified) {
                throw new CaptchaVerificationFailedException("验证码校验失败，请重新验证");
            }
        }

        String username = usernameNode.textValue();
        String password = passwordNode.textValue();

        // 将用户名、密码封装到 Token 中
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken
                = new UsernamePasswordAuthenticationToken(username, password);
        return getAuthenticationManager().authenticate(usernamePasswordAuthenticationToken);
    }
}
