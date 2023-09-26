package useless.prismaticlibe.mixin;

import com.google.common.collect.ImmutableMap;
import net.fabricmc.loader.api.FabricLoader;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;
import useless.prismaticlibe.PrismaticLibe;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

public class PrismaticLibeMixinPlugin implements IMixinConfigPlugin {

	private static final Supplier<Boolean> TRUE = () -> true;

	private static final String ATLAS_LIB_STRING = "atlas_lib";

	private static final Map<String, Supplier<Boolean>> CONDITIONS = ImmutableMap.<String, Supplier<Boolean>> builder()
		.put("useless.prismaticlibe.mixin.ItemRendererMixin",                           () -> !FabricLoader.getInstance().isModLoaded(ATLAS_LIB_STRING))
		.put("useless.prismaticlibe.mixin.ItemEntityRendererMixin",                     () -> !FabricLoader.getInstance().isModLoaded(ATLAS_LIB_STRING))
		.build();

	@Override
	public void onLoad(String mixinPackage) {
		PrismaticLibe.LOGGER.info(ATLAS_LIB_STRING + " found, managing mixins.");
	}

	@Override
	public String getRefMapperConfig() {
		return null;
	}

	@Override
	public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
		//boolean ret = CONDITIONS.getOrDefault(mixinClassName, TRUE).get();
		//System.out.print("SHOULD_MIXIN: " + ret + ", " + FabricLoader.getInstance().isModLoaded("atlas_lib") + "\n");
		//return ret;
		return CONDITIONS.getOrDefault(mixinClassName, TRUE).get();
	}

	@Override
	public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {
	}

	@Override
	public List<String> getMixins() {
		return null;
	}

	@Override
	public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {
	}

	@Override
	public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {
	}

}
