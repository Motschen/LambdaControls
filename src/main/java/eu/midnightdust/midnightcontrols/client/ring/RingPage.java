/*
 * Copyright © 2021 LambdAurora <aurora42lambda@gmail.com>
 *
 * This file is part of midnightcontrols.
 *
 * Licensed under the MIT license. For more information,
 * see the LICENSE file.
 */

package eu.midnightdust.midnightcontrols.client.ring;

import com.electronwill.nightconfig.core.Config;
import eu.midnightdust.midnightcontrols.client.MidnightControlsClient;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.util.math.MatrixStack;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * Represents a ring page.
 *
 * @author LambdAurora
 * @version 1.5.0
 * @since 1.4.0
 */
public class RingPage extends DrawableHelper {
    public static final RingPage DEFAULT = new RingPage("Default");

    public final String name;
    private RingAction[] actions = new RingAction[8];

    public RingPage(@NotNull String name) {
        this.name = name;
        for (int i = 0; i < 8; i++) {
            this.actions[i] = null;
        }
        this.actions[0] = new DummyRingAction(null);
        this.actions[1] = new KeyBindingRingAction(null, MidnightControlsClient.BINDING_LOOK_UP);
        this.actions[2] = new KeyBindingRingAction(null, MidnightControlsClient.BINDING_LOOK_LEFT);
        this.actions[3] = new KeyBindingRingAction(null, MidnightControlsClient.BINDING_LOOK_RIGHT);
        this.actions[4] = new KeyBindingRingAction(null, MidnightControlsClient.BINDING_LOOK_DOWN);
    }

    /**
     * Renders the ring page.
     *
     * @param matrices the matrices
     * @param width the screen width
     * @param height the screen height
     * @param mouseX the mouse X-coordinate
     * @param mouseY the mouse Y-coordinate
     * @param tickDelta the tick delta
     */
    public void render(@NotNull MatrixStack matrices, @NotNull TextRenderer textRenderer, int width, int height, int mouseX, int mouseY, float tickDelta) {
        int centerX = width / 2;
        int centerY = height / 2;

        int offset = MidnightRing.ELEMENT_SIZE + (MidnightRing.ELEMENT_SIZE / 2) + 5;

        int y = centerY - offset;
        int x = centerX - offset;
        for (int i = 0; i < 3; i++) {
            var ringAction = this.actions[i];
            if (ringAction != null)
                ringAction.render(matrices, textRenderer, x, y, isHovered(x, y, mouseX, mouseY));
            x += 55;
        }
        y += 55;
        x = centerX - offset;
        for (int i = 3; i < 5; i++) {
            var ringAction = this.actions[i];
            if (ringAction != null)
                ringAction.render(matrices, textRenderer, x, y, isHovered(x, y, mouseX, mouseY));
            x += 55 * 2;
        }
        y += 55;
        x = centerX - offset;
        for (int i = 5; i < 8; i++) {
            var ringAction = this.actions[i];
            if (ringAction != null)
                ringAction.render(matrices, textRenderer, x, y, isHovered(x, y, mouseX, mouseY));
            x += 55;
        }
    }

    private static boolean isHovered(int x, int y, int mouseX, int mouseY) {
        return mouseX >= x && mouseY >= y && mouseX <= x + MidnightRing.ELEMENT_SIZE && mouseY <= y + MidnightRing.ELEMENT_SIZE;
    }
    /**
     * Renders the ring page.
     *
     * @param width the screen width
     * @param height the screen height
     * @param mouseX the mouse X-coordinate
     * @param mouseY the mouse Y-coordinate
     */
    public boolean onClick(int width, int height, int mouseX, int mouseY) {
        int centerX = width / 2;
        int centerY = height / 2;

        int offset = MidnightRing.ELEMENT_SIZE + (MidnightRing.ELEMENT_SIZE / 2) + 5;

        int y = centerY - offset;
        int x = centerX - offset;
        for (int i = 0; i < 3; i++) {
            var ringAction = this.actions[i];
            if (ringAction != null && isHovered(x,y,mouseX,mouseY)) {
                ringAction.activate(RingButtonMode.PRESS);
                return true;
            }
            x += 55;
        }
        y += 55;
        x = centerX - offset;
        for (int i = 3; i < 5; i++) {
            var ringAction = this.actions[i];
            if (ringAction != null && isHovered(x,y,mouseX,mouseY)) {
                ringAction.activate(RingButtonMode.PRESS);
                return true;
            }
            x += 55 * 2;
        }
        y += 55;
        x = centerX - offset;
        for (int i = 5; i < 8; i++) {
            var ringAction = this.actions[i];
            if (ringAction != null && isHovered(x,y,mouseX,mouseY)) {
                ringAction.activate(RingButtonMode.PRESS);
                return true;
            }
            x += 55;
        }
        return false;
    }

    /**
     * Tries to parse a ring page configuration.
     *
     * @param config the configuration
     * @return an optional ring page
     */
    public static @NotNull Optional<RingPage> parseRingPage(@NotNull Config config) {
        String name = config.get("name");
        if (name == null)
            return Optional.empty();

        var page = new RingPage(name);

        List<Config> actionConfigs = config.get("actions");


        return Optional.of(page);
    }
}
