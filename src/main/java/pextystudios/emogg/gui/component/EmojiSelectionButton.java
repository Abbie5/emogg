package pextystudios.emogg.gui.component;

import net.minecraft.client.gui.GuiGraphics;
import pextystudios.emogg.emoji.resource.Emoji;
import pextystudios.emogg.emoji.EmojiHandler;

public class EmojiSelectionButton extends Button {
    private Emoji displayableEmoji = null, prevDisplayableEmoji = null;

    public EmojiSelectionButton(int x, int y) {
        this(x, y, EmojiHandler.EMOJI_DEFAULT_RENDER_SIZE);
    }

    public EmojiSelectionButton(int x, int y, int size) {
        super(
                x,
                y,
                size,
                size
        );

        changeDisplayableEmoji();
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float dt) {
        if (!visible) return;

        var hasBeenHovered = isHovered;

        super.render(guiGraphics, mouseX, mouseY, dt);

        if (!hasBeenHovered && isHovered)
            changeDisplayableEmoji();
    }

    @Override
    public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float dt) {
        if (displayableEmoji == null) {
            disableHint();
            return;
        }

        setHint(displayableEmoji.getEscapedCode());

        int renderX = x, renderY = y, renderSize = width;

        if (isHovered) {
            renderX -= 1;
            renderY -= 1;
            renderSize += 2;
        }

        displayableEmoji.render(renderX, renderY, renderSize, guiGraphics);
    }

    protected void changeDisplayableEmoji() {
        prevDisplayableEmoji = displayableEmoji;

        EmojiHandler.getInstance()
                .getRandomEmoji(true)
                .ifPresent(emoji -> displayableEmoji = emoji);

        if (displayableEmoji == null || prevDisplayableEmoji == null) return;

        while (displayableEmoji.getName().equals(prevDisplayableEmoji.getName()))
            EmojiHandler.getInstance()
                    .getRandomEmoji(true)
                    .ifPresent(emoji -> displayableEmoji = emoji);
    }
}
