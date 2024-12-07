package net.boostedbrightness.mixin;

import net.caffeinemc.mods.sodium.client.gui.options.OptionFlag;
import net.caffeinemc.mods.sodium.client.gui.options.OptionImpact;
import net.caffeinemc.mods.sodium.client.gui.options.OptionImpl;
import net.caffeinemc.mods.sodium.client.gui.options.binding.GenericBinding;
import net.caffeinemc.mods.sodium.client.gui.options.binding.OptionBinding;
import net.caffeinemc.mods.sodium.client.gui.options.control.Control;
import net.caffeinemc.mods.sodium.client.gui.options.storage.OptionStorage;
import net.minecraft.text.TranslatableTextContent;
import net.boostedbrightness.BoostedBrightness;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.EnumSet;
import java.util.function.BooleanSupplier;
import java.util.function.Function;

@Pseudo
@Mixin(OptionImpl.class)
public class MixinOptionImpl<S, T> {
    @Shadow(remap = false)
    @Final
    @Mutable
    private OptionBinding<S, T> binding;

    @Inject(at = @At("RETURN"), method = "<init>")
    private void init(OptionStorage<S> storage, Text name, Function<S, T> tooltip, OptionBinding<S, T> binding, Function<OptionImpl<S, T>, Control<T>> control, EnumSet<OptionFlag> flags, OptionImpact impact, BooleanSupplier enabled, CallbackInfo ci) {

        if (name.getContent() instanceof TranslatableTextContent content && content.getKey().equals("options.gamma")) {
            this.binding = new GenericBinding<S, T>((opt, val) -> BoostedBrightness.changeBrightness((Integer) val * 0.01D), binding::getValue);
        }
    }
}