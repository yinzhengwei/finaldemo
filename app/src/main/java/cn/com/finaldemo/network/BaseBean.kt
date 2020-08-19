package cn.com.finaldemo.network

import java.io.Serializable

/**
 * Create by yinzhengwei on 2020-03-06
 * @Function
 */
open class BaseBean(val code: Int=-1,
                    val msg: String="",
                    val success: Boolean=false) :Serializable