package com.cj.customwidget

import android.util.Base64
import android.util.Log
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import kotlin.math.log

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
//        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
//        assertEquals("com.cj.customwidget", appContext.packageName)

        val content="{\"traceId\":\"0b29d24449754098aef7474788b3e8fb\",\"signature\":\"DC681BF444FB06D5E7A90DC48C20\",\"signKey\":\"6iW0mC5lC8hX2jC5hQ0jT9cT8cB4mV\",\"page\":1,\"rows\":10,\"type\":0,\"timestamp\":\"1594781299769\",\"token\":\"eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIxMDk2NjUwMTI4MzEwMjM1MTM4IiwiaWF0IjoxNTk0MTk5NTMyfQ.yFXA2tWiISPjOB3bKtOxyrik76-ZcSqmnz3IPDhC2bk\"}"
//        EncryptUtils.doAesEncrypt(content, "zoxgBvQQXOHk2wC8t/Irrw==", "XLeeyndP7zP5B4Mipnpk6Q==")
        val atoken="khReyvlxzdIV6cBsxQwM6BnYkSsuaFkCp6MlmGWIMNRga9q5j5CW6VC19JxDoDXmxuKiapVVH/jMUMlOOcue5qEXncEMXqE3NclMbdCrkhj5ylowZAMlJQJVlhUhQKeyUTxN3s/2apEByi21oId480313Z0YzjfBMq4aqIlb7Minl2spv1Y30zCpvwxfqHLLg8ssSJayDOPeynd9afiDd/yJt7W6vXwGkK2XpErVqPa+JIQfDtnruI/QuNcsgnwNNVOQ0kCJstvI9yDy48GvluM7fo7WBd/iOoTor0eYh394F8XjFUSE7BU7HbZQI9E0X4p7GjZQMDMFlI7scDL2DSNKMaZG3/r1afvnrl68AyePCufOHaSOfpcEQKorNP/QYMtTW3c0m7sQ6VYYHhoN47RJiPGF46l91J19r8DP47kR8IWqJWnGI+UMWIlqmhWf"
        val doAesDecrypt = EncryptUtils.doAesDecrypt(
            atoken,
            Base64.decode("zoxgBvQQXOHk2wC8t/Irrw==", Base64.DEFAULT),
            Base64.decode("XLeeyndP7zP5B4Mipnpk6Q==", Base64.DEFAULT)
        )
        System.out.println(doAesDecrypt)
        Log.e("lucas",doAesDecrypt)
//        System.out.println("XLeeyndP7zP5B4Mipnpk6Q==".length)
//        System.out.println(String(Base64.decode("MDM5MjAzOTIwMzIwMTgxMg==", Base64.DEFAULT)).length)
//        System.out.println("0000000000000000".length)
    }
}