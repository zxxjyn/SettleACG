import javax.sound.sampled.AudioFormat;

import org.jibble.pircbot.PircBot;

import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;
import com.sun.speech.freetts.audio.AudioPlayer;
import com.sun.speech.freetts.audio.JavaClipAudioPlayer;

public class SpeechBot extends PircBot {
	private Voice voice;

	public SpeechBot(String name) {
		setName(name);
		// Choose the voice for the speech synthesizer.
		// 选择语音合成器的话音
		String voiceName = "kevin16";
		VoiceManager voiceManager = VoiceManager.getInstance();
		voice = voiceManager.getVoice(voiceName);
		if (voice == null) {
			System.out.println("Voice not found.");
			System.exit(1);
		}
		voice.allocate();
		// Set up the output format.
		// 设置输出格式
		AudioPlayer voicePlayer = new JavaClipAudioPlayer();
		voicePlayer.setAudioFormat(new AudioFormat(8000, 16, 1, false, true));
		voice.setAudioPlayer(voicePlayer);
	}

	// public void onMessage(String channel, String sender, String login,
	// String hostname, String message) {
	// // Send all IRC messages to the voice synthesizer.
	// // 发送所有的IRC消息到语音合成器
	// message = message.trim();
	// String input = sender + " on " + channel + " says: " + message;
	// voice.speak(input);
	// }

	public void onMessage(String channel, String sender, String login,
			String hostname, String message) {
		// Send all IRC messages to the voice synthesizer.
		// 发送所有的IRC消息到语音合成器
		message = message.trim();
		String input = message;
		voice.speak(input);
	}

	// public static void main(String[] args) throws Exception {
	// if (args.length < 2) {
	// System.out.println("Usage: java SpeechBot ");
	// System.exit(1);
	// }
	// SpeechBot bot = new SpeechBot("SpeechBot");
	// bot.connect(args[0]);
	// bot.joinChannel(args[1]);
	// }

	public static void main(String args[]) {
		SpeechBot bot = new SpeechBot("SpeechBot");
		bot.onMessage("a", "a", "a", "a", "C P U overload 77%");
	}
}
