package com.js;

import cn.hutool.core.lang.Snowflake;
import com.js.entity.User;
import com.js.service.UserService;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

@SpringBootTest
@MapperScan("com.js.mapper")
class ShardingConfigApplicationTests {

    @Autowired
    private UserService userService;

    @Test
    void contextLoads() {
        Snowflake snowflake = new Snowflake(1L, 1L);
        for (int i = 1000; i < 1100; i++) {
            System.out.println(snowflake.nextId());
            userService.insert(User.builder()
                    .id(snowflake.nextId())
                    .cityId(i)
                    .createTime(new Date())
                    .email("jjiangshuang@gmail.com")
                    .name("jiangshuang")
                    .password("jiangshuang")
                    .phone("18845721152")
                    .sex(i % 2)
                    .build());
        }
        userService.find(new User());

    }

    public String longestPalindrome(String s) {
        // 1、字符串长度小于2此时直接返回
        int len = s.length();
        if (len < 2) {
            return s;
        }
        // 定义回文串最大长度初始为1
        int maxLen = 1;
        int begin = 0;
        // dp[i][j] 表示 s[i..j] 是否是回文串
        boolean[][] dp = new boolean[len][len];
        // 初始化：所有长度为 1 的子串都是回文串
        for (int i = 0; i < len; i++) {
            dp[i][i] = true;
        }

        char[] charArray = s.toCharArray();
        // 递推开始
        // 先枚举子串长度
        for (int L = 2; L <= len; L++) {
            // 枚举左边界，左边界的上限设置可以宽松一些
            for (int i = 0; i < len; i++) {
                // 由 L 和 i 可以确定右边界，即 j - i + 1 = L 得
                int j = L + i - 1;
                // 如果右边界越界，就可以退出当前循环
                if (j >= len) {
                    break;
                }

                if (charArray[i] != charArray[j]) {
                    dp[i][j] = false;
                } else {
                    if (j - i < 3) {
                        dp[i][j] = true;
                    } else {
                        dp[i][j] = dp[i + 1][j - 1];
                    }
                }

                // 只要 dp[i][L] == true 成立，就表示子串 s[i..L] 是回文，此时记录回文长度和起始位置
                if (dp[i][j] && j - i + 1 > maxLen) {
                    maxLen = j - i + 1;
                    begin = i;
                }
            }
        }
        return s.substring(begin, begin + maxLen);
    }

}
