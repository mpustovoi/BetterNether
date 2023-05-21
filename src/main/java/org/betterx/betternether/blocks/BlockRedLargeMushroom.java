package org.betterx.betternether.blocks;

import org.betterx.bclib.blocks.BlockProperties;
import org.betterx.bclib.blocks.BlockProperties.TripleShape;
import org.betterx.betternether.blocks.materials.Materials;
import org.betterx.betternether.registry.NetherBlocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

public class BlockRedLargeMushroom extends BlockBaseNotFull {
    private static final VoxelShape TOP_SHAPE = box(0, 0.1, 0, 16, 16, 16);
    private static final VoxelShape MIDDLE_SHAPE = box(5, 0, 5, 11, 16, 11);
    public static final EnumProperty<TripleShape> SHAPE = BlockProperties.TRIPLE_SHAPE;

    public BlockRedLargeMushroom() {
        super(Materials.makeNetherWood(MapColor.COLOR_RED).noOcclusion());
        this.setDropItself(false);
        this.setRenderLayer(BNRenderLayer.CUTOUT);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> stateManager) {
        stateManager.add(SHAPE);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter view, BlockPos pos, CollisionContext ePos) {
        return state.getValue(SHAPE) == TripleShape.TOP ? TOP_SHAPE : MIDDLE_SHAPE;
    }

    @Override
    @Environment(EnvType.CLIENT)
    public ItemStack getCloneItemStack(BlockGetter world, BlockPos pos, BlockState state) {
        return state.getValue(SHAPE) == TripleShape.TOP
                ? new ItemStack(Items.RED_MUSHROOM)
                : new ItemStack(NetherBlocks.MAT_NETHER_MUSHROOM.getStem());
    }

    @Override
    public BlockState updateShape(
            BlockState state,
            Direction facing,
            BlockState neighborState,
            LevelAccessor world,
            BlockPos pos,
            BlockPos neighborPos
    ) {
        switch (state.getValue(SHAPE)) {
            case BOTTOM:
                return state;
            case MIDDLE:
            case TOP:
            default:
                return world.getBlockState(pos.below()).getBlock() == this ? state : Blocks.AIR.defaultBlockState();
        }
    }
}
