package hutool;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.symmetric.AES;
import cn.hutool.crypto.symmetric.SymmetricAlgorithm;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;

/**
 * @author zhangzhongyuan@szanfu.cn
 * @description
 * @since 2022/8/19 11:46
 */
public class Testjiami {


    public static void main(String[] args) {
//        String content = "test中文";
//
//        // 随机生成密钥 [B@4411d970
//        byte[] key = SecureUtil.generateKey(SymmetricAlgorithm.AES.getValue()).getEncoded();
//        System.out.println(key);
//
//
//        // 构建
//        AES aes = SecureUtil.aes(key);
//
//        // 加密为16进制表示 f2106f5c351f372b7f977022b65ba4df
//        String encryptHex = aes.encryptHex(content);
//        // 解密为字符串 test中文
//        String decryptStr = aes.decryptStr(encryptHex, CharsetUtil.CHARSET_UTF_8);
//
//
//        System.out.println("加密后" + encryptHex);
//        System.out.println("解密后："+decryptStr);
//        System.out.println(key);
        //生成密钥，并转为字符串，可以储存起来，解密时可直接使用
        for (int i = 0; i < 5; i++) {
            byte[] key = SecureUtil.generateKey(SymmetricAlgorithm.AES.getValue()).getEncoded();
            String secretKey = Base64.encode(key);
            System.out.println(secretKey);
        }

    }
}
