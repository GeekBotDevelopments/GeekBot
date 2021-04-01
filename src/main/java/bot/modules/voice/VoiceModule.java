package bot.modules.voice;

import edu.cmu.sphinx.api.Configuration;
import edu.cmu.sphinx.api.StreamSpeechRecognizer;

import java.io.IOException;

/**
 * Created by Robin Seifert on 3/16/2021.
 */
public class VoiceModule
{
    public static final Configuration config = new Configuration();
    public static StreamSpeechRecognizer speechRecognizer;

    public static void load() throws IOException
    {
        config.setAcousticModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us");
        config.setDictionaryPath(
                "resource:/edu/cmu/sphinx/models/en-us/cmudict-en-us.dict"
        );
        config.setLanguageModelPath(
                "resource:/edu/cmu/sphinx/models/en-us/en-us.lm.bin"
        );

        speechRecognizer = new StreamSpeechRecognizer(VoiceModule.config);
    }
}
