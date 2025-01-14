package pextystudios.emogg.emoji.resource;

import net.minecraft.resources.ResourceLocation;
import oshi.util.tuples.Pair;
import pextystudios.emogg.Emogg;
import pextystudios.emogg.util.EmojiUtil;
import pextystudios.emogg.util.StringUtil;

import java.util.concurrent.ConcurrentHashMap;

public class AnimatedEmoji extends Emoji {
    protected ConcurrentHashMap<Integer, Pair<ResourceLocation, Integer>> framesData;
    protected int totalDelayTime;

    public AnimatedEmoji(String name) {
        super(name);
    }

    public AnimatedEmoji(ResourceLocation resourceLocation) {
        super(resourceLocation);
    }

    public AnimatedEmoji(String name, String fileName) {
        super(name, fileName);
    }

    public AnimatedEmoji(String name, ResourceLocation resourceLocation) {
        super(name, resourceLocation);
    }

    @Override
    public ResourceLocation getRenderResourceLocation() {
        if (framesData.size() == 1)
            return framesData.get(0).getA();

        var currentPart = (int)(System.currentTimeMillis() / 10D % totalDelayTime);

        while (!framesData.containsKey(currentPart))
            currentPart--;

        return framesData.get(currentPart).getA();
    }

    @Override
    public boolean isAnimated() {
        return framesData.size() > 1;
    }

    @Override
    public boolean isValid() {
        return super.isValid() && totalDelayTime > 0;
    }

    @Override
    protected void load() {
        try {
            var imageData = EmojiUtil.splitGif(resourceLocation);

            width = imageData.getA().getA();
            height = imageData.getA().getB();
            totalDelayTime = imageData.getB();
            framesData = imageData.getC();
        } catch (Exception e) {
            Emogg.LOGGER.error("Failed to load: " + StringUtil.repr(resourceLocation), e);
        }
    }
}
