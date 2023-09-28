package xyz.hydroxa.vulpine_machinery.loot;

import com.google.common.base.Suppliers;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.common.loot.LootModifier;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;
import xyz.hydroxa.vulpine_machinery.item.custom.BlueprintItem;

import java.util.function.Supplier;

public class BlueprintLootModifier extends LootModifier {
    public static final Supplier<Codec<BlueprintLootModifier>> CODEC = Suppliers.memoize( () ->
                    RecordCodecBuilder.create(inst -> codecStart(inst).and(
                            inst.group(
                                ForgeRegistries.ITEMS.getCodec().fieldOf("item").forGetter(m -> m.item),
                                Codec.STRING.fieldOf("recipe").forGetter(m -> m.recipe),
                                Codec.FLOAT.fieldOf("chance").forGetter(m -> m.chance)
                            )
                    ).apply(inst, BlueprintLootModifier::new)));
    private final Item item;
    private final float chance;
    private final String recipe;

    protected BlueprintLootModifier(LootItemCondition[] conditionsIn, Item item, String recipe, float chance) {
        super(conditionsIn);
        this.item = item;
        this.chance = chance;
        this.recipe = recipe;
    }

    @Override
    protected @NotNull ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
        if (context.getRandom().nextFloat() <= chance * (context.getLuck() + 1)) {
            ItemStack toAdd = new ItemStack(item);
            toAdd.getOrCreateTag().putString(BlueprintItem.TAG_PRINT_ID, recipe);
            generatedLoot.add(toAdd);
        }

        return generatedLoot;
    }

    @Override
    public Codec<? extends IGlobalLootModifier> codec() {
        return CODEC.get();
    }
}
