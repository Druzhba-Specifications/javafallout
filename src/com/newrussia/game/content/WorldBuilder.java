package com.newrussia.game.content;

import com.newrussia.game.model.DialogueLine;
import com.newrussia.game.model.Enemy;
import com.newrussia.game.model.GameState;
import com.newrussia.game.model.Location;
import com.newrussia.game.model.Npc;
import com.newrussia.game.model.Perk;
import com.newrussia.game.model.Player;
import com.newrussia.game.model.Quest;
import com.newrussia.game.model.SpecialStats;

import java.util.List;
import java.util.Map;

public final class WorldBuilder {
    public GameState build() {
        Player player = new Player(
                "Courier Vostok",
                new SpecialStats(6, 7, 6, 6, 7, 8, 5),
                List.of(
                        new Perk("Metro Ghost", "-1 AP for stealth attacks in underground zones."),
                        new Perk("Winter Skin", "+10% resistance against cold and bleed effects."),
                        new Perk("Rad Diplomat", "+15 effective Speech when talking to leaders.")),
                List.of("9mm Pistol", "Bandage", "Tin Soup", "Lockpick Set"));

        Npc irina = new Npc(
                "Engineer Irina",
                "Novaya Metro Mechanist",
                "irina",
                "female_gravel",
                45,
                List.of(
                        new DialogueLine("Irina", "You made it through the ash storm. Good start."),
                        new DialogueLine("Irina", "Makarov's officers are hunting scavengers around Red Square."),
                        new DialogueLine("Irina", "Find the old train relay. It opens hidden bunker doors."),
                        new DialogueLine("Irina", "And don't trust smiling traders. Especially ones in white gloves.")),
                "Metro access codes",
                "She folds her arms and says you need more field credibility."
        );

        Npc kirov = new Npc(
                "Sable Broker Kirov",
                "Information Dealer",
                "kirov",
                "male_smooth",
                60,
                List.of(
                        new DialogueLine("Kirov", "Every secret has a price. Yours just happens to be dangerous."),
                        new DialogueLine("Kirov", "Under the frozen ballet theatre, there is a vault hatch."),
                        new DialogueLine("Kirov", "Bring me a reactor regulator and I will open the city checkpoints."),
                        new DialogueLine("Kirov", "If you hear children singing near the Kremlin, run.")),
                "Checkpoint pass tokens",
                "He smiles politely and sells you fake coordinates."
        );

        Npc colonel = new Npc(
                "Colonel Mikhail Sidorov",
                "Ex-Republic Officer",
                "sidorov",
                "military_harsh",
                68,
                List.of(
                        new DialogueLine("Sidorov", "Order died years ago. We just wear its uniform."),
                        new DialogueLine("Sidorov", "Makarov sealed himself in the Kremlin Depths."),
                        new DialogueLine("Sidorov", "Take this route through drainage tunnels if you want to live."),
                        new DialogueLine("Sidorov", "If you do this, do it clean. No heroes.")),
                "Kremlin drainage route",
                "He dismisses you as another scavenger with delusions."
        );

        Location novayaMetro = new Location(
                "novaya_metro",
                "Novaya Metro",
                "Moscow Undercity",
                "Steel cathedrals, cracked neon saints, and refugee bazaars under collapsed railways.",
                "Cold blue ceramic walls, rusted rails, sparking signs, damp steam clouds",
                "A monorail screams into darkness while old propaganda flickers on broken walls.",
                List.of("red_square_ruins", "volga_shore", "old_university"),
                List.of(irina),
                List.of(
                        new Enemy("Tunnel Ghoul", 38, 2, 6, 10, 28, "rushes low cover"),
                        new Enemy("Metro Rat Swarm", 30, 1, 4, 8, 18, "bleed and retreat")
                ),
                List.of("Signal Chapel - coded map behind icon panel", "Vent 13 - hidden med locker"),
                "Wasteland Ambient"
        );

        Location redSquare = new Location(
                "red_square_ruins",
                "Red Square Ruins",
                "Outer Capital Zone",
                "Ash winds cover shattered plazas. Tank hulls became walls, banners became sniper screens.",
                "Burnt red stones, ash haze, broken statues, fluttering military cloth",
                "A loudspeaker loops victory speeches for a nation that no longer answers.",
                List.of("novaya_metro", "kremlin_depths", "black_market_alleys"),
                List.of(kirov),
                List.of(
                        new Enemy("Makarov Guard", 55, 5, 10, 15, 52, "aimed shots"),
                        new Enemy("War Hound", 42, 3, 8, 13, 40, "flanks quickly")
                ),
                List.of("Lenin Vault hatch - hidden beneath mausoleum steps", "Clocktower cache - prototype rifle parts"),
                "Red Square Ashes"
        );

        Location kremlinDepths = new Location(
                "kremlin_depths",
                "Kremlin Depths",
                "Subterranean Command",
                "Military tunnels with biometric vaults, mold spores, and flickering holographic command maps.",
                "Concrete bunkers, green emergency lights, magnetic doors, heavy mist",
                "Blast doors open while geiger clicks sync with distant choir echoes.",
                List.of("red_square_ruins", "volga_shore", "reactor_catacombs"),
                List.of(colonel),
                List.of(
                        new Enemy("Spore Brute", 80, 6, 12, 18, 90, "slow but devastating"),
                        new Enemy("Autoturret MK-IV", 44, 9, 11, 16, 65, "suppression fire")
                ),
                List.of("Directorate chamber - behind icon wall", "Cryo Archive - companion dossiers and logs"),
                "Kremlin Depth Pulse"
        );

        Location volga = new Location(
                "volga_shore",
                "Volga Shore Fortress",
                "Frozen Trade Axis",
                "Smugglers dock patched hovercrafts beside frozen cranes and market bonfires.",
                "Icy piers, rope bridges, steel floodlights, orange smoke from oil fires",
                "At dusk the Volga cracks like glass while old cosmonaut songs drift over water.",
                List.of("novaya_metro", "kremlin_depths", "white_forest"),
                List.of(),
                List.of(
                        new Enemy("River Raider", 49, 4, 9, 14, 42, "close-quarters rush"),
                        new Enemy("Frost Stalker", 46, 4, 8, 15, 44, "ambush predator")
                ),
                List.of("Submerged customs office - old reserve vault", "Ghost Lighthouse lift - hidden questline"),
                "Volga Ice Wind"
        );

        Location oldUniversity = new Location(
                "old_university",
                "Old University Arcologies",
                "Research Ruins",
                "Collapsed lecture domes surround a reactor-powered library where AI terminals still whisper.",
                "Broken glass roofs, pale terminal glow, toppled bookshelves, drifting dust",
                "A projector boots and displays pre-war students applauding a future that never came.",
                List.of("novaya_metro", "black_market_alleys"),
                List.of(),
                List.of(
                        new Enemy("Archive Drone", 45, 7, 7, 12, 48, "defensive hover"),
                        new Enemy("Professor Revenant", 58, 5, 10, 14, 60, "psi bursts")
                ),
                List.of("Dean's Shelter - encrypted holotape stash", "Sub-basement lab - prototype plasma cell"),
                "Wasteland Ambient"
        );

        Location blackMarket = new Location(
                "black_market_alleys",
                "Black Market Alleys",
                "Shadow District",
                "Narrow alleys lit by salvage neon where contraband surgeons and mercenary brokers work.",
                "Wet cobblestone, hanging lamps, painted faction sigils, crowded stalls",
                "A masked choir sings from rooftops while deals are made under gun barrels.",
                List.of("red_square_ruins", "old_university", "white_forest"),
                List.of(),
                List.of(
                        new Enemy("Knife Runner", 36, 2, 9, 12, 30, "bleed stacks"),
                        new Enemy("Debt Collector", 52, 4, 10, 16, 55, "focuses weak targets")
                ),
                List.of("Sewer ledger room - reveals faction debts", "Silent clinic - cybernetic barter NPC"),
                "Red Square Ashes"
        );

        Location whiteForest = new Location(
                "white_forest",
                "White Forest Exclusion Zone",
                "Radiation Frontier",
                "Snow-covered pines stand over irradiated trenches and abandoned anti-air batteries.",
                "White snow glare, rusting artillery, black pines, radioactive fog",
                "A distant siren rises as footprints appear in snow with no visible owner.",
                List.of("volga_shore", "black_market_alleys", "reactor_catacombs"),
                List.of(),
                List.of(
                        new Enemy("Albino Wolf", 40, 3, 9, 13, 36, "pack coordination"),
                        new Enemy("Rad Bear", 88, 5, 13, 19, 95, "berserk charge")
                ),
                List.of("Bunker 77 hatch - hidden artillery map", "Snow Shrine cave - unique relic and perk"),
                "Volga Ice Wind"
        );

        Location reactorCatacombs = new Location(
                "reactor_catacombs",
                "Reactor Catacombs",
                "Endgame Corridor",
                "A maze of coolant channels, vault turbines, and command shrines beneath the capital.",
                "Pulsing reactor core, red alarm strips, steam jets, deep shadows",
                "As the reactor wakes, every corridor trembles and forgotten alarms return to life.",
                List.of("kremlin_depths", "white_forest"),
                List.of(),
                List.of(
                        new Enemy("Core Warden", 96, 8, 15, 21, 120, "counterattacks aggressively"),
                        new Enemy("Makarov Elite", 75, 7, 14, 20, 105, "uses cover and grenades")
                ),
                List.of("Maintenance prayer room - hidden ending trigger", "Core observatory - true final cutscene"),
                "Kremlin Depth Pulse"
        );


        java.util.List<Quest> quests = java.util.List.of(
                new Quest("relay", "Echoes of the Relay",
                        "Recover old relay calibration records from Novaya Metro hidden sectors.",
                        "Discover any hidden place in Novaya Metro.", 70),
                new Quest("checkpoint", "White Glove Ledger",
                        "Get proof from Kirov and expose the fake checkpoint route operation.",
                        "Pass speech check with Kirov in Red Square.", 90),
                new Quest("core", "Silent Reactor Oath",
                        "Enter Reactor Catacombs and stabilize command core protocols.",
                        "Travel to Reactor Catacombs and survive one combat.", 140)
        );

        Map<String, Location> world = Map.of(
                novayaMetro.id(), novayaMetro,
                redSquare.id(), redSquare,
                kremlinDepths.id(), kremlinDepths,
                volga.id(), volga,
                oldUniversity.id(), oldUniversity,
                blackMarket.id(), blackMarket,
                whiteForest.id(), whiteForest,
                reactorCatacombs.id(), reactorCatacombs
        );

        return new GameState(player, world, novayaMetro.id(), quests);
        return new GameState(player, world, novayaMetro.id());
    }
}
