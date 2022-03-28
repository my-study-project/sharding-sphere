package com.js.config;

import com.google.common.base.Preconditions;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.StringUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.shardingsphere.encrypt.strategy.impl.AESEncryptor;
import org.apache.shardingsphere.encrypt.strategy.spi.Encryptor;
import org.springframework.context.annotation.Configuration;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Properties;

/**
 * <p>
 * Shardingsphere加密处理器
 * </p>
 */
@Configuration
@NoArgsConstructor
@Data
@Slf4j
public class MyShardingEncryptor implements Encryptor {

    // AES KEY
    private static final String AES_KEY = "aes.key.value";

    private Properties properties = new Properties();


    @Override
    public void init() {

    }

    @Override
    public String encrypt(Object plaintext) {
        try {
            byte[] result = this.getCipher(1).doFinal(StringUtils.getBytesUtf8(String.valueOf(plaintext)));
            log.debug("[MyShardingEncryptor]>>>> 加密: {}", Base64.encodeBase64String(result));
            return Base64.encodeBase64String(result);
        } catch (Exception ex) {
            log.error("[MyShardingEncryptor]>>>> 加密异常：", ex);
        }
        return null;

    }

    @Override
    public Object decrypt(String ciphertext) {
        try {
            if (null == ciphertext) {
                return null;
            } else {
                byte[] result = this.getCipher(2).doFinal(Base64.decodeBase64(ciphertext));
                log.debug("[MyShardingEncryptor]>>>> 解密: {}", new String(result));
                return new String(result);
            }
        } catch (Exception ex) {
            log.error("[MyShardingEncryptor]>>>> 解密异常：", ex);
        }
        return null;
    }

    @Override
    public String getType() {
        return "mySharding";
        // 和yml配置一致
    }

    @Override
    public Properties getProperties() {
        return this.properties;
    }

    @Override
    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    /**
     * 加解密算法
     *
     * @param decryptMode 1-加密，2-解密，还有其他类型可以点进去看源码。
     */
    private Cipher getCipher(int decryptMode) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException {
        Preconditions.checkArgument(this.properties.containsKey("aes.key.value"), "No available secret key for `%s`.", AESEncryptor.class.getName());
        Cipher result = Cipher.getInstance("AES");
        result.init(decryptMode, new SecretKeySpec(this.createSecretKey(), "AES"));
        return result;
    }

    /**
     * 创建密钥，规则根据自己需要定义。
     * -- PS: 生产环境规范要求不能打印出密钥相关日志，以免发生意外泄露情况。
     */
    private byte[] createSecretKey() {
        // yml中配置的原始密钥
        String oldKey = this.properties.get("aes.key.value").toString();
        Preconditions.checkArgument(null != oldKey, String.format("%s can not be null.", "aes.key.value"));
        /*
         * 将原始密钥和自定义的盐一起再次加密生成新的密钥返回.
         * 注意，因为我们用的AES加解密方式最终密钥必须16位，否则AES会报错，
         * 而application.yml中配置的aes.key.value是10位字符组合，所以这里才substring(0,5)，否则最终没有返回16位会抛AES异常，可以自己试验下。
         */
        String secretKey = DigestUtils.sha1Hex(oldKey + AES_KEY).toUpperCase().substring(0, 5) + "!" + oldKey;
        // 密钥打印在上线前一定要删掉，避免泄露引起安全事故。
        log.debug("[MyShardingEncryptor]>>>> 密钥: {}", secretKey);
        return secretKey.getBytes();
    }

}
