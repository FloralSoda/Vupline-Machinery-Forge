package xyz.hydroxa.vulpine_machinery;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class ModTags {
    public static class ItemTags {
        public static final TagKey<Item> PEPPERBOX_BARRELS = create_component("pepperbox_barrels");
        public static final TagKey<Item> PEPPERBOX_CORES = create_component("pepperbox_cores");
        public static final TagKey<Item> PEPPERBOX_HANDLES = create_component("pepperbox_handles");
        public static final TagKey<Item> PEPPERBOX_BRIDGES = create_component("pepperbox_bridges");


        private static TagKey<Item> create_component(String location) {
            return net.minecraft.tags.ItemTags.create(new ResourceLocation(VulpineMachineryMod.MOD_ID, "components/" + location));
        }
        private static TagKey<Item> create(String location) {
            return net.minecraft.tags.ItemTags.create(new ResourceLocation(VulpineMachineryMod.MOD_ID, location));
        }
    }
}
