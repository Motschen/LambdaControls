/*
 * Copyright © 2021 LambdAurora <aurora42lambda@gmail.com>
 *
 * This file is part of midnightcontrols.
 *
 * Licensed under the MIT license. For more information,
 * see the LICENSE file.
 */

package eu.midnightdust.midnightcontrols.client.compat;

import eu.midnightdust.midnightcontrols.client.ButtonState;
import eu.midnightdust.midnightcontrols.client.MidnightControlsClient;
import eu.midnightdust.midnightcontrols.client.compat.mixin.EntryListWidgetAccessor;
import eu.midnightdust.midnightcontrols.client.compat.mixin.EntryWidgetAccessor;
import eu.midnightdust.midnightcontrols.client.controller.ButtonBinding;
import eu.midnightdust.midnightcontrols.client.controller.InputHandlers;
import eu.midnightdust.midnightcontrols.client.controller.PressAction;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.Selectable;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.EntryListWidget;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;
import org.aperlambda.lambdacommon.utils.LambdaReflection;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

import static eu.midnightdust.midnightcontrols.client.compat.CompatHandler.SlotPos.INVALID_SLOT;
import static org.lwjgl.glfw.GLFW.*;

/**
 * Represents a compatibility handler for REI.
 *
 * @author LambdAurora
 * @version 1.7.0
 * @since 1.2.0
 */
public class ReiCompat implements CompatHandler {
    private static EntryListWidget ENTRY_LIST_WIDGET;

    @Override
    public void handle(@NotNull MidnightControlsClient mod) {
        /*ButtonBinding.builder(new Identifier("rei", "category_back"))
                .buttons(GLFW_GAMEPAD_BUTTON_LEFT_BUMPER)
                .filter((client, binding) -> isViewingScreen(client.currentScreen))
                .action(handleTab(false))
                .cooldown(true)
                .register();
        ButtonBinding.builder(new Identifier("rei", "category_next"))
                .buttons(GLFW_GAMEPAD_BUTTON_RIGHT_BUMPER)
                .filter((client, binding) -> isViewingScreen(client.currentScreen))
                .action(handleTab(true))
                .cooldown(true)
                .register();

        ButtonBinding.builder(new Identifier("rei", "page_back"))
                .buttons(ButtonBinding.axisAsButton(GLFW_GAMEPAD_AXIS_RIGHT_X, false))
                .filter((client, binding) -> InputHandlers.inInventory(client, binding) || isViewingScreen(client.currentScreen))
                .action(handlePage(false))
                .cooldown(true)
                .register();
        ButtonBinding.builder(new Identifier("rei", "page_next"))
                .buttons(ButtonBinding.axisAsButton(GLFW_GAMEPAD_AXIS_RIGHT_X, true))
                .filter((client, binding) -> InputHandlers.inInventory(client, binding) || isViewingScreen(client.currentScreen))
                .action(handlePage(true))
                .cooldown(true)
                .register();

        ButtonBinding.builder(new Identifier("rei", "recipe_back"))
                .buttons(GLFW_GAMEPAD_BUTTON_DPAD_UP)
                .filter((client, binding) -> isViewingScreen(client.currentScreen))
                .action(handleRecipe(false))
                .cooldown(true)
                .register();
        ButtonBinding.builder(new Identifier("rei", "recipe_next"))
                .buttons(GLFW_GAMEPAD_BUTTON_DPAD_DOWN)
                .filter((client, binding) -> isViewingScreen(client.currentScreen))
                .action(handleRecipe(true))
                .cooldown(true)
                .register();

        // For some reasons this is broken.
        ButtonBinding.builder(new Identifier("rei", "show_usage"))
                .buttons(GLFW_GAMEPAD_BUTTON_RIGHT_THUMB)
                .filter((client, binding) -> InputHandlers.inInventory(client, binding) || isViewingScreen(client.currentScreen))
                .action(handleShowRecipeUsage(true))
                .cooldown(true)
                .register();

        ButtonBinding.builder(new Identifier("rei", "show_recipe"))
                .buttons(GLFW_GAMEPAD_BUTTON_LEFT_THUMB)
                .filter((client, binding) -> InputHandlers.inInventory(client, binding) || isViewingScreen(client.currentScreen))
                .action(handleShowRecipeUsage(false))
                .cooldown(true)
                .register();
         */
    }
    /*

    @Override
    public boolean requireMouseOnScreen(Screen screen) {
        return isViewingScreen(screen);
    }

    @Override
    public @Nullable CompatHandler.SlotPos getSlotAt(@NotNull Screen screen, int mouseX, int mouseY) {

        ScreenOverlayImpl overlay = ScreenOverlayImpl.getInstance();
        if (overlay.isInside(mouseX, mouseY)) {
            EntryListWidget widget = getEntryListWidget();
            if (widget == null)
                return null;

            return this.getSlotAt(widget, mouseX, mouseY, false);
        } else if (isViewingScreen(screen)) {
            for (Element element : screen.children()) {
                var slot = this.getSlotAt(element, mouseX, mouseY, true);
                if (slot != null)
                    return slot;
            }
        }
        return null;
    }

    private @Nullable CompatHandler.SlotPos getSlotAt(@NotNull Element element, int mouseX, int mouseY, boolean allowEmpty) {
        if (element instanceof EntryWidget) {
            EntryWidget entry = (EntryWidget) element;
            if (entry.containsMouse(mouseX, mouseY)) {
                if (!allowEmpty && entry.getEntries().isEmpty())
                    return INVALID_SLOT;
                return new SlotPos(entry.getBounds().getX() + 1, entry.getBounds().getY() + 1);
            }
        } else if (element instanceof EntryListWidget) {
            List<EntryListEntryWidget> entries = ((EntryListWidgetAccessor) element).getEntries();
            for (EntryListEntryWidget entry : entries) {
                var slot = this.getSlotAt(entry, mouseX, mouseY, allowEmpty);
                if (slot != null && slot != INVALID_SLOT)
                    return slot;
            }
        } else if (!(element instanceof ButtonWidget) && element instanceof WidgetWithBounds widgetWithBounds) {
            for (var child : widgetWithBounds.children()) {
                var slot = this.getSlotAt(child, mouseX, mouseY, allowEmpty);
                if (slot != null && slot != INVALID_SLOT)
                    return slot;
            }
        }
        return null;
    }

    private static boolean isViewingScreen(Screen screen) {
        return screen instanceof DefaultDisplayViewingScreen || screen instanceof CompositeDisplayViewingScreen;
    }

    @Override
    public boolean handleMenuBack(@NotNull MinecraftClient client, @NotNull Screen screen) {
        if (!isViewingScreen(screen))
            return false;

        MinecraftClient.getInstance().setScreen(REIRuntimeImpl.getInstance().getPreviousContainerScreen());
        REIRuntimeImpl.getInstance().getLastOverlay().init();
        return true;
    }

    private static EntryListWidget getEntryListWidget() {
        if (ENTRY_LIST_WIDGET == null) {
            ENTRY_LIST_WIDGET = LambdaReflection.getFirstFieldOfType(ContainerScreenOverlay.class, EntryListWidget.class)
                    .map(field -> (EntryListWidget) LambdaReflection.getFieldValue(null, field))
                    .orElse(null);
        }
        return ENTRY_LIST_WIDGET;
    }

    private static @Nullable AbstractEntryStack getCurrentStack(@NotNull MinecraftClient client) {
        double x = client.mouse.getX() * (double) client.getWindow().getScaledWidth() / (double) client.getWindow().getWidth();
        double y = client.mouse.getY() * (double) client.getWindow().getScaledHeight() / (double) client.getWindow().getHeight();

        if (isViewingScreen(client.currentScreen)) {
            for (Element element : client.currentScreen.children()) {
                EntryStack stack = getCurrentStack(element, x, y);
                if (stack != null)
                    return stack;
            }
        }

        Optional<ContainerScreenOverlay> overlay = REIRuntimeImpl.getInstance().getOverlay(false,false);
        if (!overlay.isPresent())
            return RecipeHelper.getInstance().getScreenFocusedStack(client.currentScreen);
        EntryListWidget widget = getEntryListWidget();
        if (widget == null)
            return ScreenOverlayImpl.getInstance().getInstance().getOverlayMenu()..getScreenFocusedStack(client.currentScreen);

        return getCurrentStack(widget, x, y);
    }

    private static @Nullable EntryStack getCurrentStack(@NotNull Element element, double mouseX, double mouseY) {
        if (element instanceof EntryWidget) {
            EntryWidget entry = (EntryWidget) element;
            if (entry.containsMouse(mouseX, mouseY))
                return ((EntryWidgetAccessor) entry).lambdacontrols_getCurrentEntry();
        } else if (element instanceof EntryListWidget) {
            List<EntryListEntryWidget> entries = ((EntryListWidgetAccessor) element).getEntries();
            for (EntryListEntryWidget entry : entries) {
                if (entry.containsMouse(mouseX, mouseY)) {
                    return ((EntryWidgetAccessor) entry).lambdacontrols_getCurrentEntry();
                }
            }
        } else if (!(element instanceof ButtonWidget) && element instanceof WidgetWithBounds) {
            for (Element child : ((WidgetWithBounds) element).children()) {
                EntryStack stack = getCurrentStack(child, mouseX, mouseY);
                if (stack != null)
                    return stack;
            }
        }
        return null;
    }

    private static PressAction handleShowRecipeUsage(boolean usage) {
        return (client, button, value, action) -> {
            if (action.isUnpressed())
                return false;

            EntryStack stack = RecipeHelper.getInstance().getScreenFocusedStack(client.currentScreen);
            if (stack == null) {
                stack = getCurrentStack(client);
            }

            if (stack != null && !stack.isEmpty()) {
                stack = stack.copy();
                if (usage) {
                    return ClientHelper.getInstance().openView(ClientHelper.ViewSearchBuilder.builder().addUsagesFor(stack).setInputNotice(stack).fillPreferredOpenedCategory());
                } else {
                    return ClientHelper.getInstance().openView(ClientHelper.ViewSearchBuilder.builder().addRecipesFor(stack).setOutputNotice(stack).fillPreferredOpenedCategory());
                }
            }

            return false;
        };
    }

    private static PressAction handlePage(boolean next) {
        return (client, button, value, action) -> {
            if (action == ButtonState.RELEASE)
                return false;

            Optional<ContainerScreenOverlay> overlay = ScreenHelper.getOptionalOverlay();
            if (!overlay.isPresent())
                return false;

            EntryListWidget widget = getEntryListWidget();
            if (widget == null)
                return false;

            if (next)
                widget.nextPage();
            else
                widget.previousPage();
            widget.updateEntriesPosition();

            return true;
        };
    }

    /**
     * Returns the handler for category tabs buttons.
     *
     * @param next True if the action is to switch to the next tab.
     * @return The handler.
     */
    /*
    private static PressAction handleTab(boolean next) {
        return (client, button, value, action) -> {
            if (action != ButtonState.RELEASE)
                return false;

            if (client.currentScreen instanceof RecipeViewingScreen) {
                RecipeViewingScreenAccessor screen = (RecipeViewingScreenAccessor) client.currentScreen;
                if (next)
                    screen.getCategoryNext().onClick();
                else
                    screen.getCategoryBack().onClick();
                return true;
            } else if (client.currentScreen instanceof VillagerRecipeViewingScreen) {
                VillagerRecipeViewingScreenAccessor screen = (VillagerRecipeViewingScreenAccessor) client.currentScreen;
                List<RecipeCategory<?>> categories = screen.getCategories();
                int currentTab = screen.getSelectedCategoryIndex();
                screen.setSelectedCategoryIndex(getNextIndex(currentTab, categories.size(), next));
                screen.setSelectedRecipeIndex(0);
                screen.lambdacontrols_init();
                return true;
            }
            return false;
        };
    }

    private static PressAction handleRecipe(boolean next) {
        return (client, button, value, action) -> {
            if (action.isUnpressed())
                return false;

            if (client.currentScreen instanceof RecipeViewingScreen) {
                RecipeViewingScreenAccessor screen = (RecipeViewingScreenAccessor) client.currentScreen;
                if (next)
                    screen.getRecipeNext().onClick();
                else
                    screen.getRecipeBack().onClick();
                return true;
            } else if (client.currentScreen instanceof VillagerRecipeViewingScreen) {
                VillagerRecipeViewingScreenAccessor screen = (VillagerRecipeViewingScreenAccessor) client.currentScreen;
                List<RecipeCategory<?>> categories = screen.getCategories();
                int currentTab = screen.getSelectedCategoryIndex();
                List<RecipeDisplay> recipes = screen.getCategoryMap().get(categories.get(currentTab));

                if (recipes.size() == 0)
                    return true;

                int currentRecipe = screen.getSelectedRecipeIndex();
                int nextRecipe = getNextIndex(currentRecipe, recipes.size(), next);

                if (nextRecipe == 0) {
                    screen.getScrolling().scrollTo(0.0, true);
                } else if (nextRecipe == recipes.size() - 1) {
                    screen.getScrolling().scrollTo(screen.getScrolling().getMaxScroll(), true);
                } else {
                    double scrollAmount = screen.getScrolling().getMaxScroll() / (float) recipes.size();
                    screen.getScrolling().offset(next ? scrollAmount : -scrollAmount, true);
                }

                screen.setSelectedRecipeIndex(nextRecipe);
                screen.lambdacontrols_init();

                return true;
            }

            return false;
        };
    }

    private static int getNextIndex(int currentIndex, int size, boolean next) {
        int nextIndex = currentIndex + (next ? 1 : -1);
        if (nextIndex < 0)
            nextIndex = size - 1;
        else if (nextIndex >= size)
            nextIndex = 0;
        return nextIndex;
    }
     */
}