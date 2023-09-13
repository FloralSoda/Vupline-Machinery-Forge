package xyz.hydroxa.vulpine_machinery.item;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class ModCreativeModeTab {
    public  static  final CreativeModeTab MACHINERY_TAB = new CreativeModeTab("vulpine_machinery_tab") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ModItems.BULLET.get());
        }
    };
}
