package bot.modules.minecraft.mojang;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MinecraftVersion {

	private String id;
	private String type;
	private String url;
	private String time;
	private String releaseTime;
}
