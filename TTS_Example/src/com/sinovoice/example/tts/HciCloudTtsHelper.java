package com.sinovoice.example.tts;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import com.sinovoice.example.sys.AccountInfo;
import com.sinovoice.hcicloudsdk.api.HciLibPath;
import com.sinovoice.hcicloudsdk.api.tts.HciCloudTts;
import com.sinovoice.hcicloudsdk.common.ApiInitParam;
import com.sinovoice.hcicloudsdk.common.HciErrorCode;
import com.sinovoice.hcicloudsdk.common.Session;
import com.sinovoice.hcicloudsdk.common.InitParam.LogParam;
import com.sinovoice.hcicloudsdk.common.tts.ITtsSynthCallback;
import com.sinovoice.hcicloudsdk.common.tts.TtsInitParam;
import com.sinovoice.hcicloudsdk.common.tts.TtsSynthResult;

public class HciCloudTtsHelper {
	String sPath = System.getProperty("user.dir");
    private String mCapKey = AccountInfo.getInstance().getCapKey();
    private static HciCloudTtsHelper mInstance;
    
    private FileOutputStream mFos;
    
    static {
		String arch = System.getProperty("sun.arch.data.model");
    	String win_ver = "\\win_x86";
    	if (arch.equals("64")) {
			win_ver = "\\win_x64";
		}
		// 灵云sdk中dll文件目录
		String libPath = System.getProperty("user.dir") + "\\libs" + win_ver;
		// 指定dll文件路径，顺序表示加载顺序
		String ttsLibPath[] = new String[] { 
				libPath + "/libhci_curl.dll",
				libPath + "/jtopus.dll", 
				libPath + "/jtspeex.dll",
				libPath + "/hci_sys.dll", 
				libPath + "/hci_tts.dll",
				libPath + "/hci_tts_cloud_synth.dll",
				libPath + "/hci_tts_local_synth.dll",
				libPath + "/hci_tts_local_synth_sing.dll",
				libPath + "/hci_tts_jni.dll", 
				};
		// 再加载hci_tts.dll
		HciLibPath.setTtsLibPath(ttsLibPath);
    }
    
    private HciCloudTtsHelper() {
       
    }

    public static HciCloudTtsHelper getInstance() {
        if (mInstance == null) {
            mInstance = new HciCloudTtsHelper();
        }
        return mInstance;
    }
    
    /**
     * Mt引擎初始化 辅助工具 : MtInitParam:该类的实例通过addParam(key, value)的方式添加Nlu初始化的参数,
     * 再通过getStringConfig() 获取初始化时需要的字符串参数 config 初始化方法:
     * HciCloudMt.hciMtInit(config)
     */
    public int init() {
    	
    	// 构造Mt初始化的帮助类的实例
		TtsInitParam ttsInitParam = new TtsInitParam();

		// 此处演示初始化的能力为AccountInfo.txt中所填能力
        String dataPath = sPath + "/data";
        ttsInitParam.addParam(ApiInitParam.PARAM_KEY_DATA_PATH, dataPath);
        ttsInitParam.addParam(ApiInitParam.PARAM_KEY_FILE_FLAG, "none");
        ttsInitParam.addParam(ApiInitParam.PARAM_KEY_INIT_CAP_KEYS, mCapKey);
		
    	// 调用初始化方法, 返回值为错误码:
		// 如果为HCI_ERR_NONE(0) 则表示MT初始化成功,否则请根据SDK帮助文档中"常量字段值"中的
		// 错误码的含义检查错误所在
		int initResult = HciCloudTts.hciTtsInit(ttsInitParam.getStringConfig());

        return initResult;
    }
    
	/**
     * Mt的反初始化和系统的反初始化, 在Init方法成功后, 执行结束时需要调用反初始化, 否则会导致引擎没有停止运行, 在下一次运行时会出现返回
     * HCI_ERR_SYS_ALREADY_INIT 或其他错误信息
     */
    public int release() {
        // Mt反初始化
        int nRet = HciCloudTts.hciTtsRelease();

        return nRet;
    }
    
    /**
     * 引擎合成过程中,每合成一段文字都会调用该回调方法通知外部并传回音频数据 音频数据保存在对象
     * TtsSynthResult中,通过该对象的getVoieceData()方法可以获取 合成是设定音频格式的音频数据
     */
    private ITtsSynthCallback mTtsSynthCallback = new ITtsSynthCallback() {
        @Override
        public boolean onSynthFinish(int errorCode, TtsSynthResult result) {
            // errorCode 为当前合成操作返回的错误码,如果返回值为HciErrorCode.HCI_ERR_NONE则表示合成成功
            if (errorCode != HciErrorCode.HCI_ERR_NONE) {
                System.out.println("synth error, code = " + errorCode);
                return false;
            }
            
            // 此处可以使用方法
            // result.getCurrentSynthText() 来获取当前合文本
            // 可以使用方法
            // result.getVoiceData() 来获取合成的音频数据,一般直接用来播放,也可以保存到本地的音频文件中

            if (mFos == null) {
                initFileOutputStream();
            }

            // 将本次合成的数据写入文件
            // 每次合成的数据，可能不是需要合成文本的全部，需要多次写入
            if(result != null && result.getVoiceData() != null){
                int length = result.getVoiceData().length;
                if (length > 0) {
                    try {
                        mFos.write(result.getVoiceData(), 0, length);
                        mFos.flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            if (!result.isHasMoreData()) {
                flushOutputStream();
            }

            // 返回true表示处理结果成功,通知引擎可以继续合成并返回下一次的合成结果; 如果不希望引擎继续合成, 则返回false
            // 该方法在引擎中的同步的,即引擎会持续阻塞一直到该方法执行结束
            return true;
        }
    };
    
    /**
     * 演示合成
     * 
     * @param assetsPath
     *            需要合成的文本放在assets目录下，传入文件路径
     */
    public void synth() {
        System.out.println("synth start...");

        // Tts合成的配置字符串, 用户也可以借助帮助类 TtsConfig来生成该字符串
        String sConfig = "capKey=" + mCapKey;

        // 创建会话
        Session session = new Session();
        // 开始会话
        int nRet = HciCloudTts.hciTtsSessionStart(sConfig, session);
        if (nRet != HciErrorCode.HCI_ERR_NONE) {
            System.out.println("session start failed: " + nRet);
            return;
        }
        System.out.println("session start success");

        byte[] synthText = null;
        if ( mCapKey.equals("tts.local.synth.sing")) {
        	synthText = loadText("S3ML_sing.txt.enc");
        	sConfig += ",tagmode=s3ml_sing";
		}
        else {
        	synthText = loadText("tts.txt");
		}
        
        // 开始合成
        nRet = HciCloudTts.hciTtsSynthEx(session, synthText, sConfig,
                mTtsSynthCallback);
        if (nRet != HciErrorCode.HCI_ERR_NONE) {
            System.out.println("hciTtsSynth failed: " + nRet);

            HciCloudTts.hciTtsSessionStop(session);
            return;
        }
        System.out.println("hciTtsSynth success");

        // 停止会话
        // 合成结束后应该调用该方法通知引擎该会话已经结束
        nRet = HciCloudTts.hciTtsSessionStop(session);
        if (nRet != HciErrorCode.HCI_ERR_NONE) {
            System.out.println("session stop failed: " + nRet);

            return;
        }

        System.out.println("session stop success");
        System.out.println("synth stop...");
    }
    
    /**
     * TTS合成动作完毕，将合成的数据输出到文件中
     */
    private void flushOutputStream() {
        try {
            mFos.close();
            mFos = null;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 初始化FileOutputStream
     */
    private void initFileOutputStream() {
    	
    	String filePath = System.getProperty("user.dir") + "/testdata" + "/synth.pcm";
    	 
        try {
            File file = new File(filePath);
            File parentFile = file.getParentFile();
            if (!parentFile.exists()) {
                parentFile.mkdirs();
            }
            if (file.exists()) {
                file.delete();
            } else {
                file.createNewFile();
            }
            mFos = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
	public byte[] loadText(String fileName) {
		String file = sPath + "/testdata/" + fileName;
		File fileSrc = new File(file);
		if (!fileSrc.exists())
			return null;
		
		int size = (int)fileSrc.length();
		try {
			FileInputStream rd = new FileInputStream(fileSrc);
			byte[] data = new byte[size];			
			rd.read(data);
			return data;
		} catch (IOException e) {
			e.printStackTrace();
			System.out.print(e.getMessage());
			return null;
		}
	}
}
