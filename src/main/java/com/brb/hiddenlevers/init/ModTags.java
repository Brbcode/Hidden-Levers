package com.brb.brbmods.init;

import com.brb.brbmods.BrbMod;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;
import net.minecraft.util.ResourceLocation;

@SuppressWarnings("unused")
public class ModTags {
    public static final class Blocks {


        private Blocks() {}

        private static Tag<Block> tag(String name) {
            return new BlockTags.Wrapper(new ResourceLocation(BrbMod.MOD_ID, name));
        }

        private static Tag<Block> tag(String namespace, String name) {
            return new BlockTags.Wrapper(new ResourceLocation(namespace, name));
        }
    }

    public static final class Items {


        private Items() {}

        private static Tag<Item> tag(String name) {
            return new ItemTags.Wrapper(new ResourceLocation(BrbMod.MOD_ID, name));
        }

        private static Tag<Item> tag(String namespace, String name) {
            return new ItemTags.Wrapper(new ResourceLocation(namespace, name));
        }
    }
}
