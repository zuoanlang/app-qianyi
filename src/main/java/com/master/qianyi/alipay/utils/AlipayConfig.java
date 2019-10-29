package com.master.qianyi.alipay.utils;

public class AlipayConfig {

    /** 1.支付宝支付业务：入参app_id */
    public static final String APPID = "2019032863737537";

    /** 2.支付宝网关*/
    public static final String GATE = "https://openapi.alipay.com/gateway.do";

    /** 3.(应用私钥)*/
    public static final String APP_PRIVATE_KEY = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCnVrFewmIB+h0t9L02r9qHDC2sitUuC3Z2lbVgmezot2eLE3XgWAQSex9OShZPGQDULnAK6bPuIgN7DioEQ6UB7R6rS2S3LpJ8/XdfT7/Tv/rxLiLpzi10sZEZw5FCtxrXcMHYHPccrnrtR+L/j3zPt89ekDy3L4ezr6MBiERJSqt+mo234ot4MFk4eWaYFmizEftauAoA3fiTYgNJay3wjPa8fxssVq+k4n8GIDVoau8NLrDhtw8areOdb8HI0Bplbqle5nDdXL1DAo87qMxgbpwiROZqo37nysfWt/5//cnD3gd7gNF1Pcnu751+f+HRXo7moNvUHR9C3qDQKcRTAgMBAAECggEBAIh3VLLYhyDhnkZKHryocOhNcDoUBoJEatmOgZpELUYju9dZiXnpjXic3Az2snbTBzACpvDfYhNDvd7u0CkmGT0W6Tn8hCg3XNN9yYn6+Y9wMu/ZFjPiJoPSA2klaiMkPEmR9SOPi57iU/GJ/H85vymO1xhhPIKPynUpOSAYxseVP55xEqeurM1pk12wW/OZgspYxzWkMmCc7IW8oTU7bHxy5rs78sc82tMxGSi6HTNqdHiORn+mYh0akHiSFtfLwD+Fj5BebMHyWU9AUdtwHCGXVZ8y/8/iqwraxZRLTGQtsG5QhQ96wPUMmD3Mt8yMZR8IlsP7l7oGhKBTqnePQNECgYEA6J1YhyU3tx7SH0ClvLK562RoPLkfLmwutYRA+YFYaSGEgSLNixdgKRn3FWPY2pua6KsoV97xNTdv+DA4Xd7mpRcV++gyY96RVXpl1uxkSLjod4g/Ca1JjCJyd9d1UE4vymoj7Uegtbsu7kv7hlCCuE+juiNGdTd9VPAupqUn+EcCgYEAuClhcSnPeEInjQ6Pl3INYMAs/VG23IjvABCNCTKlKJKu+BrVyx3Ev7H+7Mgc2YEWEpyGW2WETrmThFBvoslHyjl7ivBFQ9ZwdW1Y+eTTuWX2cNNEwhWi2v8GjoTk2lwZVG2oa82VZznQ3ua+r28wFvj/zm2aW1Gwqq/DUxTcJZUCgYA0QPRcF442gqPtBVa11uhQ6pNH4MYSZZbwi68U3WuA4lijsa++qRee5AhPM8jJAt9/mMNNSfWhYLy0YhCndaUWPONhXT3ElYDMVNx/tUap12/ROjf+8oBtDGfuZ+Ygjg/KRqGfcdG2QUA/o63y9C8JPEqpS7dOKZLr+7BfGwapjQKBgG1+/RbBmz+PZeCyhaGwCFA8MwOy8ByTtja9oH2VhSlofWu62N7Zpj+r3zx9htmzrKIIOsJgy5fbMuB6ysVpE6wjHVdwoF5DQ74t87cw3Mng2/QdSUZ0gs3ez9ExJrjGBbFzOa5PH8OuVogRfdLlMxyVk9Lyy9H78iSqOGJPhs05AoGBAKGRIz/8cIYXAZMiVCX/edPjsZoMzvd5jf3w11zHjyX4qdWL8Sn5lTDmeHrfCDYu5PYakQQgWYyAdPfjor+1PIDhMwj8Zj+pODHnFgUw3wuznWp1NdUpoAtG9mZia6YnKAWF93/4Q+A3/Jt4XdU6wprqbW54ItTI+8OKSXzLwV8+";

    /** 4.（应用公钥）*/
    public static final String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAp1axXsJiAfodLfS9Nq/ahwwtrIrVLgt2dpW1YJns6LdnixN14FgEEnsfTkoWTxkA1C5wCumz7iIDew4qBEOlAe0eq0tkty6SfP13X0+/07/68S4i6c4tdLGRGcORQrca13DB2Bz3HK567Ufi/498z7fPXpA8ty+Hs6+jAYhESUqrfpqNt+KLeDBZOHlmmBZosxH7WrgKAN34k2IDSWst8Iz2vH8bLFavpOJ/BiA1aGrvDS6w4bcPGq3jnW/ByNAaZW6pXuZw3Vy9QwKPO6jMYG6cIkTmaqN+58rH1rf+f/3Jw94He4DRdT3J7u+dfn/h0V6O5qDb1B0fQt6g0CnEUwIDAQAB";

    /** 5. 支付宝公钥 */
    public static final String PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAgCfneAVRNTHGERr6I6sdWbBxRJAzRIcHclYN7ltsyzBULo3pCTmMhF0WZVtcl48pNZ5Y6+n+QTFkHTC8vnuw3VpD0xZ9fJ8tWGNXtyrnRmFTYYH2/i0xcaks3tcaShBfldbVBsKQF0VV/8soeZld0uiqWc0B3IdLFudaIrLrpgCnTH7FAiuX+/GzuP7HidoM3CQ3tUiRJZUQ8HJVYsgMYjD1dkCDUOHt1Z2xbjPaCVkhRE+Vwvnf2OnLjvSY8kaA+hQzk9QOJLwGG8oxFm/EYPDG2V18gki0w4z0Kticw8F57zXq3EhfwAA089wr5PSfJdMqLdkqOZyJ68uP+K8C/QIDAQAB";

    /** 6.编码方式 */
    public static final String CHARSET = "UTF-8";

    // 7.返回格式
    public static String FORMAT = "json";

    // 8.加密类型
    public static String SIGNTYPE = "RSA2";

    // 9.服务器异步通知页面路径 需http://或者https://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String notify_url = "http://120.78.3.204/qianyi/alipay/notify_url.do";
}
