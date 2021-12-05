package paulevs.betternether.registry;

import net.fabricmc.fabric.api.structure.v1.FabricStructureBuilder;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep.Decoration;
import net.minecraft.world.level.levelgen.feature.ConfiguredStructureFeature;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.StructureFeatureConfiguration;
import paulevs.betternether.BetterNether;
import paulevs.betternether.config.Configs;
import paulevs.betternether.world.structures.CityFeature;
import ru.bclib.api.biomes.BCLBiomeBuilder;
import ru.bclib.api.biomes.BiomeAPI;
import ru.bclib.world.biomes.BCLBiome;
import ru.bclib.world.structures.BCLStructureFeature;

public class NetherStructures {
	public static final int CITY_SPACING = Configs.GENERATOR.getInt("generator.world.cities", "distance", 64);
	// Nether City
	public static final BCLStructureFeature CITY_STRUCTURE = new BCLStructureFeature(
		new ResourceLocation(BetterNether.MOD_ID, "nether_city"),
		new CityFeature(),
		Decoration.RAW_GENERATION,
		CITY_SPACING,
		CITY_SPACING>>1
	);
	
	public static void register() {
		if (Configs.GENERATOR.getBoolean("generator.world.cities", "overworld", false)) {
			BiomeAPI.registerOverworldBiomeModification((biomeID, biome) -> {
				if (!biomeID.getNamespace().equals(BetterNether.MOD_ID)) {
					modifyNonBNBiome(biomeID, biome);
				}
			});
		}
	}
	
	public static void modifyNonBNBiome(ResourceLocation biomeID, Biome biome) {
		BiomeAPI.addBiomeStructure(biome, CITY_STRUCTURE.getFeatureConfigured());
	}
	
	public static void addDefaultFeatures(BCLBiomeBuilder builder) {
		builder.structure(CITY_STRUCTURE);
	}
}
