package com.example.demo.lib;

import com.sun.jna.Pointer;
public class hc{
// 网络连接恢复，设备重连成功回调
// 通过 CLIENT_SetAutoReconnect 设置该回调函数，当已断线的设备重连成功时，SDK会调用该函数
private static class HaveReConnect implements NetSDKLib.fHaveReConnect {
    public void invoke(NetSDKLib.LLong m_hLoginHandle, String pchDVRIP, int nDVRPort, Pointer dwUser) {
        //logger.info("ReConnect Device["+pchDVRIP+"] Port["+nDVRPort+"]",pchDVRIP, nDVRPort);
        System.out.printf("ReConnect Device[%s] Port[%d]\n", pchDVRIP, nDVRPort);


    }
}
}