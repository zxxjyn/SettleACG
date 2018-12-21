package com.sinovoice.example.tts;

import com.sinovoice.example.sys.AccountInfo;
import com.sinovoice.example.sys.HciCloudSysHelper;
import com.sinovoice.hcicloudsdk.common.HciErrorCode;

/**
 * @author sinovoice
 */
public class HciCloudTtsMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
	    /**
	     * 加载用户信息工具类
	     */
	    AccountInfo mAccountInfo;
	    
	    /**
	     * HciCloud帮助类，可完成灵云系统初始化，释放操作。
	     */
	    HciCloudSysHelper mHciCloudSysHelper;

	    /**
	     * TTS帮助类， 可完成TTS能力的初始化，开始合成，释放操作。
	     */
	    HciCloudTtsHelper mHciCloudTtsHelper;
		
        mAccountInfo = AccountInfo.getInstance();
        boolean loadResult = mAccountInfo.loadAccountInfo();
        if (loadResult) {
            // 加载信息成功进入主界面
        	System.out.println("加载灵云账号成功");
        } else {
            // 加载信息失败，显示失败界面
        	System.out.println("加载灵云账号失败！请在assets/AccountInfo.txt文件中填写正确的灵云账户信息，账户需要从www.hcicloud.com开发者社区上注册申请。");
            return;
        }
        
        mHciCloudSysHelper = HciCloudSysHelper.getInstance();
        mHciCloudTtsHelper = HciCloudTtsHelper.getInstance();

        // 此方法是线程阻塞的，当且仅当有结果返回才会继续向下执行。
        // 此处只是演示合成能力用法，没有对耗时操作进行处理。需要开发者放入后台线程进行初始化操作
        // 必须首先调用HciCloudSys的初始化方法
        int sysInitResult = mHciCloudSysHelper.init();
        if (sysInitResult != HciErrorCode.HCI_ERR_NONE) {
        	System.out.println("hci init error, error code = " + sysInitResult);
            return;
        } else {
        	System.out.println("hci init success");
        }
        
        // 此方法是线程阻塞的，当且仅当有结果返回才会继续向下执行。
        // 此处只是演示合成能力用法，没有对耗时操作进行处理。需要开发者放入后台线程进行初始化操作
        // 只有HciCloudSys初始化成功后，才能调用asr的初始化方法
        int ttsInitResult = mHciCloudTtsHelper.init();
        if (ttsInitResult != HciErrorCode.HCI_ERR_NONE) {
        	System.out.println("tts init error " + ttsInitResult);
            return;
        } else {
        	System.out.println("tts init success");
        }

        mHciCloudTtsHelper.synth();

		// -------------反初始化------------------------------------------
		// 终止TTs能力
        int ttsReleaseResult = mHciCloudTtsHelper.release();
        if (ttsReleaseResult != HciErrorCode.HCI_ERR_NONE) {
        	System.out.println("hciTtsRelease failed:" + ttsReleaseResult);
        	mHciCloudSysHelper.release();
            return;
        } else {
        	System.out.println("hciTtsRelease success");
        }
        
		// 终止 灵云 系统
		int sysReleaseRet = mHciCloudSysHelper.release();
		if(HciErrorCode.HCI_ERR_NONE != sysReleaseRet) {
			System.out.println("hciRelease failed:" + sysReleaseRet);
		}
		System.out.println("hciRelease Success");
	}
}
